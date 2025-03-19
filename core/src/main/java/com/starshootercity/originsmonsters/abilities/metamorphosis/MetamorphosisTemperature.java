package com.starshootercity.originsmonsters.abilities.metamorphosis;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.CooldownAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.events.PlayerSwapOriginEvent;
import com.starshootercity.originsmonsters.OriginsMonsters;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class MetamorphosisTemperature implements CooldownAbility, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:metamorphosis_temperature");
    }

    private static final NamespacedKey playerTemperatureKey = new NamespacedKey(OriginsMonsters.getInstance(), "player-temperature");

    public static MetamorphosisTemperature INSTANCE = new MetamorphosisTemperature();

    public static int getTemperature(Player player) {
        return player.getPersistentDataContainer().getOrDefault(playerTemperatureKey, PersistentDataType.INTEGER, 50);
    }

    public static void setTemperature(Player player, int amount) {
        player.getPersistentDataContainer().set(playerTemperatureKey, PersistentDataType.INTEGER, Math.max(0, Math.min(amount, 100)));
        INSTANCE.setCooldown(player, getTemperature(player));
    }
    
    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 20 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                double blockTemp = player.getLocation().getBlock().getTemperature();
                if (blockTemp <= 0.15) {
                    setTemperature(player, getTemperature(player) - getConfigOption(OriginsMonsters.getInstance(), speedMultiplier, ConfigManager.SettingType.INTEGER));
                } else if (blockTemp >= 1.75 && !OriginsReborn.getNMSInvoker().isUnderWater(player)) {
                    setTemperature(player, getTemperature(player) + getConfigOption(OriginsMonsters.getInstance(), speedMultiplier, ConfigManager.SettingType.INTEGER));
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

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(100, "metamorphosis_temperature", true, true);
    }

    private final String speedMultiplier = "speed_multiplier";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMonsters.getInstance(), speedMultiplier, Collections.singletonList("The speed at which temperature should change"), ConfigManager.SettingType.INTEGER, 1);
    }
}
