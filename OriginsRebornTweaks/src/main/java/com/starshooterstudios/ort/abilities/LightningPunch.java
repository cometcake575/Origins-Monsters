package com.starshooterstudios.ort.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.events.PlayerLeftClickEvent;
import com.starshooterstudios.ort.Main;
import net.kyori.adventure.key.Key;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LightningPunch implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can strike lightning when you swing with an empty hand", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Lightning Punch", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("ort:lightning_punch");
    }

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getItem() != null && !event.getItem().getType().equals(Material.AIR)) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            Block block = event.getPlayer().getTargetBlockExact(500, FluidCollisionMode.NEVER);
            if (block == null) return;
            block.getWorld().strikeLightning(block.getLocation()).getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
        });
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager().getPersistentDataContainer().has(key)) event.setDamage(1000);
    }

    private final NamespacedKey key = new NamespacedKey(Main.getInstance(), "lightning-power");
}
