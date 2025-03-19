package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsmonsters.OriginsMonsters;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class BetterGoldWeapons implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Your evil corruption of gold unlocks a dark power, making golden weapons unbreakable and much stronger.";
    }

    @Override
    public String title() {
        return "Gold Desecration";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:better_gold_weapons");
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        if (event.getItem().getType() == Material.GOLDEN_SWORD || event.getItem().getType() == Material.GOLDEN_AXE) {
            runForAbility(event.getPlayer(), player -> event.setCancelled(true));
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.GOLDEN_SWORD || item.getType() == Material.GOLDEN_AXE) {
                runForAbility(player, p -> event.setDamage(event.getDamage() * getConfigOption(OriginsMonsters.getInstance(), damageMultiplier, ConfigManager.SettingType.FLOAT)));
            }
        }
    }

    private final String damageMultiplier = "damage_multiplier";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMonsters.getInstance(), damageMultiplier, Collections.singletonList("Multiplier for damage done with golden tools"), ConfigManager.SettingType.FLOAT, 2.5f);
    }
}
