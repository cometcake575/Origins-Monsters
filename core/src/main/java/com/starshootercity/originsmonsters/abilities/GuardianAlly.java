package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.jetbrains.annotations.NotNull;

public class GuardianAlly implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Guardians don't attack you!";
    }

    @Override
    public String title() {
        return "Guardian Ally";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:guardian_ally");
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.getEntityType() == EntityType.GUARDIAN || event.getEntityType() == EntityType.ELDER_GUARDIAN) {
            runForAbility(event.getTarget(), player -> event.setCancelled(true));
        }
    }
}
