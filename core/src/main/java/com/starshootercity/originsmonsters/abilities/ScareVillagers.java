package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsmonsters.OriginsMonsters;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ScareVillagers implements VisibleAbility, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:scare_villagers");
    }

    @Override
    public String description() {
        return "Villagers are scared of you and refuse to trade with you.";
    }

    @Override
    public String title() {
        return "Terrifying Monster";
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
        Bukkit.getMobGoals().addGoal(villager, 0, OriginsMonsters.getNMSInvoker().getVillagerAfraidGoal(villager, this::hasAbility));
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
            runForAbility(player, p -> event.getEntity().getPersistentDataContainer().set(hitByPlayerKey, PersistentDataType.STRING, p.getName()));
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Villager villager) {
            runForAbility(event.getPlayer(), player -> {
                event.setCancelled(true);
                villager.shakeHead();
            });
        }
    }
}
