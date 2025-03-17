package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DoubleFireDamage implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You take double damage from all sources of fire.";
    }

    @Override
    public String title() {
        return "Frozen Skin";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:double_fire_damage");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (List.of(
                EntityDamageEvent.DamageCause.FIRE,
                EntityDamageEvent.DamageCause.HOT_FLOOR,
                EntityDamageEvent.DamageCause.FIRE_TICK,
                EntityDamageEvent.DamageCause.LAVA
        ).contains(event.getCause())) {
            runForAbility(event.getEntity(), player -> event.setDamage(event.getDamage() * 2));
        }
    }
}
