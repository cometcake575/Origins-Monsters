package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmonsters.OriginsMonsters;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class SlownessArrows implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "All arrows you shoot have the slowness effect.";
    }

    @Override
    public String title() {
        return "Frozen Arrows";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:slowness_arrows");
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (event.getProjectile() instanceof Arrow arrow) {
            runForAbility(event.getEntity(), player -> arrow.addCustomEffect(new PotionEffect(OriginsReborn.getNMSInvoker().getSlownessEffect(),
                    getConfigOption(OriginsMonsters.getInstance(), duration, ConfigManager.SettingType.INTEGER),
                    getConfigOption(OriginsMonsters.getInstance(), amplifier, ConfigManager.SettingType.INTEGER), false, true), false));
        }
    }

    private final String duration = "weakness_duration";
    private final String amplifier = "weakness_strength";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMonsters.getInstance(), duration, Collections.singletonList("The duration of the weakness effect in ticks"), ConfigManager.SettingType.INTEGER, 600);
        registerConfigOption(OriginsMonsters.getInstance(), amplifier, Collections.singletonList("The strength of the weakness effect"), ConfigManager.SettingType.INTEGER, 0);
    }
}
