package com.starshooterstudios.ort.abilities.infpotions;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public interface InfinitePotionEffectAbility extends Ability, Listener {

    PotionEffectType getEffect();

    int getLevel();

    @EventHandler
    default void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 20 != 0) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!enabled(player)) continue;
            AbilityRegister.runForAbility(player, getKey(), () -> player.addPotionEffect(new PotionEffect(getEffect(), 30, 1)));
        }
    }

    default boolean enabled(Player player) {
        return true;
    }
}
