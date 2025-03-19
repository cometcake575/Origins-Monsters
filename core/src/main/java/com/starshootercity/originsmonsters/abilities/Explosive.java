package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.types.CooldownAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.originsmonsters.OriginsMonsters;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Explosive implements VisibleAbility, Listener, CooldownAbility {
    @Override
    public String description() {
        return "You can sacrifice some of your health to create an explosion every 15 seconds.";
    }

    @Override
    public String title() {
        return "Explosive";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:explosive");
    }
    private final Map<Player, Integer> lastToggledSneak = new HashMap<>();

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        runForAbility(event.getPlayer(), player -> {
            if (hasCooldown(player)) return;
            if (!event.isSneaking()) return;
            if (Bukkit.getCurrentTick() - lastToggledSneak.getOrDefault(player, Bukkit.getCurrentTick() - 11) <= 10) {
                setCooldown(player);
                player.getLocation().createExplosion(player, 3, getConfigOption(OriginsMonsters.getInstance(), causeFire, ConfigManager.SettingType.BOOLEAN), getConfigOption(OriginsMonsters.getInstance(), breakBlocks, ConfigManager.SettingType.BOOLEAN));
                OriginsMonsters.getNMSInvoker().dealExplosionDamage(player, getConfigOption(OriginsMonsters.getInstance(), healthLost, ConfigManager.SettingType.INTEGER));
            } else {
                lastToggledSneak.put(player, Bukkit.getCurrentTick());
            }
        });
    }

    private final String breakBlocks = "break_blocks";
    private final String causeFire = "cause_fire";
    private final String healthLost = "health_lost";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMonsters.getInstance(), breakBlocks, Collections.singletonList("Whether the explosion should break blocks"), ConfigManager.SettingType.BOOLEAN, true);
        registerConfigOption(OriginsMonsters.getInstance(), causeFire, Collections.singletonList("Whether the explosion should cause fire"), ConfigManager.SettingType.BOOLEAN, false);
        registerConfigOption(OriginsMonsters.getInstance(), healthLost, Collections.singletonList("How much damage the player should take when creating an explosion"), ConfigManager.SettingType.INTEGER, 8);
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(300, "explosive");
    }
}
