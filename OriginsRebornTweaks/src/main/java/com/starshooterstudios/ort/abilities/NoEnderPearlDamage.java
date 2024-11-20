package com.starshooterstudios.ort.abilities;

import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class NoEnderPearlDamage implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("ort:no_ender_pearl_damage");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {
                if (ignorePearlDamage.getOrDefault(event.getEntity(), Bukkit.getCurrentTick()-1) >= Bukkit.getCurrentTick()) {
                    event.setCancelled(true);
                }
            }
        });
    }

    private final Map<Entity, Integer> ignorePearlDamage = new HashMap<>();

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {
                ignorePearlDamage.put(event.getPlayer(), Bukkit.getCurrentTick());
            }
        });
    }
}
