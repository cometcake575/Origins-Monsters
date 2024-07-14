package com.starshootercity.originsmonsters.abilities.metamorphosis;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.events.PlayerSwapOriginEvent;
import com.starshootercity.originsmonsters.OriginsMonsters;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MetamorphosisTemperature implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:metamorphosis_temperature");
    }

    private static final NamespacedKey playerTemperatureKey = new NamespacedKey(OriginsMonsters.getInstance(), "player-temperature");

    public static int getTemperature(Player player) {
        return player.getPersistentDataContainer().getOrDefault(playerTemperatureKey, PersistentDataType.INTEGER, 50);
    }

    public static void setTemperature(Player player, int amount) {
        player.getPersistentDataContainer().set(playerTemperatureKey, PersistentDataType.INTEGER, Math.max(0, Math.min(amount, 100)));
    }
    
    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 20 != 0) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            AbilityRegister.runForAbility(player, getKey(), () -> {
                double blockTemp = player.getLocation().getBlock().getTemperature();
                if (blockTemp <= 0.15) {
                    setTemperature(player, getTemperature(player) - 1);
                } else if (blockTemp >= 2 && !OriginsReborn.getNMSInvoker().isUnderWater(player)) {
                    setTemperature(player, getTemperature(player) + 1);
                }
            });
        }
    }

    @EventHandler
    public void onPlayerSwapOrigin(PlayerSwapOriginEvent event) {
        if (List.of(
                PlayerSwapOriginEvent.SwapReason.ORB_OF_ORIGIN,
                PlayerSwapOriginEvent.SwapReason.DIED,
                PlayerSwapOriginEvent.SwapReason.COMMAND,
                PlayerSwapOriginEvent.SwapReason.INITIAL
        ).contains(event.getReason())) setTemperature(event.getPlayer(), 50);
    }
}
