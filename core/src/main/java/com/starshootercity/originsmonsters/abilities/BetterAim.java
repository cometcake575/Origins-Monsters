package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmonsters.OriginsMonsters;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class BetterAim implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Your aim is more accurate than humans.";
    }

    @Override
    public String title() {
        return "Sniper";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:better_aim");
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        runForAbility(event.getEntity(), player -> {
            OriginsMonsters.getNMSInvoker().launchArrow(event.getProjectile(), player, 0, 3 * event.getForce(), getConfigOption(OriginsMonsters.getInstance(), divergence, SettingType.FLOAT));
        });
    }

    private final String divergence = "divergence";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMonsters.getInstance(), divergence, Collections.singletonList("Divergence of arrows fired"), SettingType.FLOAT, 0f);
    }
}
