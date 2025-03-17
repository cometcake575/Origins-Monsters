package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.Ability;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class WitherImmunity implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:wither_immunity");
    }

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        runForAbility(event.getEntity(), player -> {
            if (event.getNewEffect() != null) {
                if (event.getNewEffect().getType().equals(PotionEffectType.WITHER)) {
                    event.setCancelled(true);
                }
            }
        });
    }
}
