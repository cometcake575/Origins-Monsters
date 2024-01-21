package com.starshootercity.originsmonsters.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SenseMovement implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can see the outlines of nearby mobs, even through blocks.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Heightened Senses", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:sense_movement");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 20 != 0) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            AbilityRegister.runForAbility(player, getKey(), () -> {
                for (Entity entity : player.getNearbyEntities(24, 24, 24)) {
                    if (entity == player) continue;
                    if (!(entity instanceof LivingEntity)) continue;

                    byte data = 0;
                    if (entity.isGlowing() || entity.getLocation().distance(player.getLocation()) <= 16) {
                        data += 0x40;
                    }
                    if (entity.getFireTicks() > 0) {
                        data += 0x01;
                    }
                    if (entity.isSneaking()) {
                        data += 0x02;
                    }
                    if (entity instanceof Player checkedPlayer) {
                        if (checkedPlayer.isSprinting()) {
                            data += 0x08;
                        }
                        if (checkedPlayer.isSwimming()) {
                            data += 0x10;
                        }
                        if (checkedPlayer.isInvisible()) {
                            data += 0x20;
                        }
                        if (checkedPlayer.isGliding()) {
                            data += 0x80;
                        }
                    }

                    List<SynchedEntityData.DataValue<?>> eData = new ArrayList<>();
                    eData.add(SynchedEntityData.DataValue.create(new EntityDataAccessor<>(0, EntityDataSerializers.BYTE), data));
                    ClientboundSetEntityDataPacket metadata = new ClientboundSetEntityDataPacket(entity.getEntityId(), eData);
                    ((CraftPlayer) player).getHandle().connection.send(metadata);
                }
            });
        }
    }
}
