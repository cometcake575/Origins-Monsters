package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmonsters.OriginsMonsters;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class ApplyHungerEffect implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Anything you hit gets the Hunger effect.";
    }

    @Override
    public String title() {
        return "Hunger";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:apply_hunger_effect");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            runForAbility(event.getDamager(), player -> entity.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, getConfigOption(OriginsMonsters.getInstance(), effectDuration, SettingType.INTEGER), getConfigOption(OriginsMonsters.getInstance(), effectStrength, SettingType.INTEGER), false, true)));
        }
    }

    private final String effectStrength = "effect_strength";
    private final String effectDuration = "effect_duration";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMonsters.getInstance(), effectStrength, Collections.singletonList("Strength of the hunger effect"), SettingType.INTEGER, 200);
        registerConfigOption(OriginsMonsters.getInstance(), effectDuration, Collections.singletonList("Duration in ticks of the hunger effect"), SettingType.INTEGER, 0);
    }
}
