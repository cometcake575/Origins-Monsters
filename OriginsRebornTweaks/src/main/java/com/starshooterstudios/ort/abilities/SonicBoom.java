package com.starshooterstudios.ort.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmonsters.OriginsMonsters;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SonicBoom implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Every 30 seconds you can launch a sonic boom by hitting the air with your hand.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Sonic Boom", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("ort:sonic_boom");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().isLeftClick()) return;
        if (event.getItem() != null) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            Location currentLoc = event.getPlayer().getLocation().clone().add(0, 1.5, 0);
            List<Entity> hitEntities = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                currentLoc.add(currentLoc.getDirection());
                currentLoc.getWorld().spawnParticle(Particle.SONIC_BOOM, currentLoc, 1);
                for (Entity entity : currentLoc.getNearbyEntities(1, 1, 1)) {
                    if (entity == event.getPlayer()) continue;
                    if (!hitEntities.contains(entity)) {
                        if (!(entity instanceof LivingEntity livingEntity)) continue;
                        hitEntities.add(entity);
                        OriginsMonsters.getNMSInvoker().dealSonicBoomDamage(livingEntity, getDamageAmount(), event.getPlayer());
                    }
                }
            }
        });
    }

    public int getDamageAmount() {
        return 100;
    }
}
