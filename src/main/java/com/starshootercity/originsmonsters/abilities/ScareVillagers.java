package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ScareVillagers implements VisibleAbility, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:scare_villagers");
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Villagers are scared of you and refuse to trade with you.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Terrifying Monster", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Villager villager) {
            fixVillager(villager);
        }
    }

    @EventHandler
    public void onEntitiesLoad(EntitiesLoadEvent event) {
        for (Entity entity : event.getEntities()) {
            if (entity instanceof Villager villager) {
                fixVillager(villager);
            }
        }
    }

    public void fixVillager(Villager villager) {
        Bukkit.getMobGoals().addGoal(villager, 0, new AvoidEntityGoal<>(
                (PathfinderMob) ((CraftEntity) villager).getHandle(),
                Player.class,
                6,
                0.5,
                0.8,
                livingEntity -> {
                    if (livingEntity.getBukkitEntity() instanceof org.bukkit.entity.Player player) {
                        return AbilityRegister.hasAbility(player, getKey());
                    }
                    return false;
                }
        ).asPaperVanillaGoal());
    }

    private final NamespacedKey hitByPlayerKey = new NamespacedKey(OriginsReborn.getInstance(), "hit-by-player");

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() == EntityType.CREEPER) {
            org.bukkit.entity.Player player;
            if (event.getDamager() instanceof Projectile projectile) {
                if (projectile.getShooter() instanceof org.bukkit.entity.Player shooter) player = shooter;
                else return;
            } else if (event.getDamager() instanceof org.bukkit.entity.Player damager) player = damager;
            else return;
            AbilityRegister.runForAbility(player, getKey(), () -> event.getEntity().getPersistentDataContainer().set(hitByPlayerKey, PersistentDataType.STRING, player.getName()));
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager villager) {
            AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
                event.setCancelled(true);
                villager.shakeHead();
            });
        }
    }
}
