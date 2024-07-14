package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BetterGoldWeapons implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Your evil corruption of gold unlocks a dark power, making golden weapons unbreakable and much stronger.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Gold Desecration", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:better_gold_weapons");
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        if (event.getItem().getType() == Material.GOLDEN_SWORD || event.getItem().getType() == Material.GOLDEN_AXE) {
            AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> event.setCancelled(true));
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.GOLDEN_SWORD || item.getType() == Material.GOLDEN_AXE) {
                AbilityRegister.runForAbility(player, getKey(), () -> event.setDamage(event.getDamage() * 2));
            }
        }
    }
}
