package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.Ability;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class WaterBreathing implements Ability, Listener {
    @EventHandler
    public void onEntityAirChange(EntityAirChangeEvent event) {
        runForAbility(event.getEntity(), player -> event.setAmount(player.getMaximumAir()));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause().equals(EntityDamageEvent.DamageCause.DROWNING)) {
            runForAbility(event.getEntity(), player -> event.setCancelled(true));
        }
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:water_breathing");
    }
}
