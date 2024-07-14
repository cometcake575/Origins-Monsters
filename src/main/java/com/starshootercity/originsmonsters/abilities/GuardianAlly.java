package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GuardianAlly implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Guardians don't attack you!", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Guardian Ally", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:guardian_ally");
    }

    @EventHandler
    public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event) {
        if (event.getEntityType() == EntityType.GUARDIAN || event.getEntityType() == EntityType.ELDER_GUARDIAN) {
            AbilityRegister.runForAbility(event.getTarget(), getKey(), () -> event.setCancelled(true));
        }
    }
}
