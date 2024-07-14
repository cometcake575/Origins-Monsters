package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_20_R3.entity.AbstractProjectile;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BetterAim implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Your aim is more accurate than humans.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Sniper", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:better_aim");
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> {
            Entity entity = ((CraftEntity) event.getEntity()).getHandle();
            ((AbstractProjectile) event.getProjectile()).getHandle().shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3 * event.getForce(), 0);
        });
    }
}
