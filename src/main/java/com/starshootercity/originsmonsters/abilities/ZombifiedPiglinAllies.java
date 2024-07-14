package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ZombifiedPiglinAllies implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Nearby Zombified Piglins will attack anything that that attacks you or that you attack.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Terrifying Armies", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:zombified_piglin_allies");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            Player player;
            boolean go = true;
            if (event.getDamager() instanceof Player p) player = p;
            else if (event.getDamager() instanceof Projectile p && p.getShooter() instanceof Player pl) player = pl;
            else {
                player = null;
                go = false;
            }
            if (go) {
                AbilityRegister.runForAbility(player, getKey(), () -> {
                    List<Entity> entities = player.getNearbyEntities(32, 32, 32);
                    entities.removeIf(entity1 -> entity1.getType() != EntityType.ZOMBIFIED_PIGLIN);
                    for (Entity entity1 : entities) {
                        if (entity1 instanceof PigZombie pigZombie) {
                            pigZombie.setAngry(true);
                            pigZombie.setTarget(entity);
                        }
                    }
                });
            }
        }


        LivingEntity entity;
        if (event.getDamager() instanceof LivingEntity e) entity = e;
        else if (event.getDamager() instanceof Projectile p && p.getShooter() instanceof LivingEntity e) entity = e;
        else return;
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> {
            List<Entity> entities = event.getEntity().getNearbyEntities(32, 32, 32);
            entities.removeIf(entity1 -> entity1.getType() != EntityType.ZOMBIFIED_PIGLIN);
            for (Entity entity1 : entities) {
                if (entity1 instanceof PigZombie pigZombie) {
                    pigZombie.setAngry(true);
                    pigZombie.setTarget(entity);
                }
            }
        });
    }
}
