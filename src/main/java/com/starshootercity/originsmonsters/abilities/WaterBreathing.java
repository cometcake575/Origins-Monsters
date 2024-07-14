package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.jetbrains.annotations.NotNull;

public class WaterBreathing implements Ability, Listener {
    @EventHandler
    public void onEntityAirChange(EntityAirChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            AbilityRegister.runForAbility(player, getKey(), () -> event.setAmount(player.getMaximumAir()));
        }
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:water_breathing");
    }
}
