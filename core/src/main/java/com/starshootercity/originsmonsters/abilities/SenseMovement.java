package com.starshootercity.originsmonsters.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class SenseMovement implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You can see the outlines of nearby mobs, even through blocks.";
    }

    @Override
    public String title() {
        return "Heightened Senses";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:sense_movement");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 20 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                for (Entity entity : player.getNearbyEntities(24, 24, 24)) {
                    if (entity == player) continue;
                    if (!(entity instanceof LivingEntity livingEntity)) continue;

                    byte data = 0;
                    if (entity.isGlowing() || entity.getLocation().distance(player.getLocation()) <= 16) {
                        data += 0x40;
                    }
                    if (entity.getFireTicks() > 0) {
                        data += 0x01;
                    }
                    if (livingEntity.isInvisible()) {
                        data += 0x20;
                    }
                    if (entity instanceof Player checkedPlayer) {
                        if (checkedPlayer.isSneaking()) {
                            data += 0x02;
                        }
                        if (checkedPlayer.isSprinting()) {
                            data += 0x08;
                        }
                        if (checkedPlayer.isSwimming()) {
                            data += 0x10;
                        }
                        if (checkedPlayer.isGliding()) {
                            data += (byte) 0x80;
                        }
                    }

                    OriginsReborn.getNMSInvoker().sendEntityData(player, entity, data);
                }
            });
        }
    }
}
