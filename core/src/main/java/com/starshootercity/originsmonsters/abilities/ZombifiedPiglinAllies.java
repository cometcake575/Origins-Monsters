package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmonsters.OriginsMonsters;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ZombifiedPiglinAllies implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Nearby Zombified Piglins will attack anything that that attacks you or that you attack.";
    }

    @Override
    public String title() {
        return "Terrifying Armies";
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
                runForAbility(player, pl -> {
                    int ran = getConfigOption(OriginsMonsters.getInstance(), range, ConfigManager.SettingType.INTEGER);
                    List<Entity> entities = pl.getNearbyEntities(ran, ran, ran);
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
        runForAbility(event.getEntity(), player -> {
            int ran = getConfigOption(OriginsMonsters.getInstance(), range, ConfigManager.SettingType.INTEGER);
            List<Entity> entities = player.getNearbyEntities(ran, ran, ran);
            entities.removeIf(entity1 -> entity1.getType() != EntityType.ZOMBIFIED_PIGLIN);
            for (Entity entity1 : entities) {
                if (entity1 instanceof PigZombie pigZombie) {
                    pigZombie.setAngry(true);
                    pigZombie.setTarget(entity);
                }
            }
        });
    }

    private final String range = "range";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMonsters.getInstance(), range, Collections.singletonList("Range to check for Zombified Piglins to attack a target"), ConfigManager.SettingType.INTEGER, 32);
    }
}
