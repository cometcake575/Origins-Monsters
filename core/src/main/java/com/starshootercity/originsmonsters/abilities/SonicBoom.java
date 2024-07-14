package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.events.PlayerLeftClickEvent;
import com.starshootercity.originsmonsters.OriginsMonsters;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return Key.key("monsterorigins:sonic_boom");
    }
    private final Map<Player, Integer> lastUsedMagicTime = new HashMap<>();

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.AIR) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (Bukkit.getCurrentTick() - lastUsedMagicTime.getOrDefault(event.getPlayer(), Bukkit.getCurrentTick() - 600) < 600) return;
            lastUsedMagicTime.put(event.getPlayer(), Bukkit.getCurrentTick());
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
        return OriginsMonsters.getInstance().getConfig().getInt("sonic-boom-damage", 15);
    }
}
