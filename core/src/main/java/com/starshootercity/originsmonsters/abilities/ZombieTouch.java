package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;

public class ZombieTouch implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You zombify villagers instead of killing them.";
    }

    @Override
    public String title() {
        return "Zombie Touch";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:zombie_touch");
    }

    @EventHandler
    public void onEntityDeathEvent(EntityDeathEvent event) {
        if (event.getEntity() instanceof Villager villager) {
            runForAbility(event.getEntity().getKiller(), player -> {
                event.setCancelled(true);
                villager.zombify();
            });
        }
    }
}
