package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.jetbrains.annotations.NotNull;

public class CreeperAlly implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Creepers don't attack you!";
    }

    @Override
    public String title() {
        return "Creeper Ally";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:creeper_ally");
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.getEntityType() != EntityType.CREEPER) return;
        runForAbility(event.getTarget(), player -> event.setCancelled(true));
    }
}
