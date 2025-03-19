package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.types.CooldownAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.events.PlayerLeftClickEvent;
import com.starshootercity.originsmonsters.OriginsMonsters;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SonicBoom implements VisibleAbility, Listener, CooldownAbility {
    @Override
    public String description() {
        return "Every 30 seconds you can launch a sonic boom by hitting the air with your hand.";
    }

    @Override
    public String title() {
        return "Sonic Boom";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:sonic_boom");
    }

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
        runForAbility(event.getPlayer(), player -> {
            if (hasCooldown(player)) return;
            setCooldown(player);
            Location currentLoc = player.getLocation().clone().add(0, 1.5, 0);
            List<Entity> hitEntities = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                currentLoc.add(currentLoc.getDirection());
                currentLoc.getWorld().spawnParticle(Particle.SONIC_BOOM, currentLoc, 1);
                for (Entity entity : currentLoc.getNearbyEntities(1, 1, 1)) {
                    if (entity == player) continue;
                    if (!hitEntities.contains(entity)) {
                        if (!(entity instanceof LivingEntity livingEntity)) continue;
                        hitEntities.add(entity);
                        OriginsMonsters.getNMSInvoker().dealSonicBoomDamage(livingEntity, getDamageAmount(), player);
                    }
                }
            }
        });
    }

    public int getDamageAmount() {
        return getConfigOption(OriginsMonsters.getInstance(), damageDealt, ConfigManager.SettingType.INTEGER);
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(600, "sonic_boom");
    }

    private final String damageDealt = "damage_dealt";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMonsters.getInstance(), damageDealt, Collections.singletonList("The amount of damage the Sonic Boom should deal"), ConfigManager.SettingType.INTEGER, 15);
    }
}
