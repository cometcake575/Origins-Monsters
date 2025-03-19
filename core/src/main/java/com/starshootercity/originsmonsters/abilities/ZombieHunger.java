package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsmonsters.OriginsMonsters;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExhaustionEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class ZombieHunger implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Your constant hunger for flesh makes you exhaust quicker than a human.";
    }

    @Override
    public String title() {
        return "Zombie Hunger";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:zombie_hunger");
    }

    @EventHandler
    public void onEntityExhaustion(EntityExhaustionEvent event) {
        runForAbility(event.getEntity(), player -> event.setExhaustion(event.getExhaustion() * getConfigOption(OriginsMonsters.getInstance(), exhaustionMultiplier, ConfigManager.SettingType.FLOAT)));
    }

    private final String exhaustionMultiplier = "exhaustion_multiplier";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMonsters.getInstance(), exhaustionMultiplier, Collections.singletonList("The amount to multiply exhaustion by"), ConfigManager.SettingType.FLOAT, 1.5f);
    }
}
