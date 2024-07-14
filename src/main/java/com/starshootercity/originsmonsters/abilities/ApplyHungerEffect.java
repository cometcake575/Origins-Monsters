package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ApplyHungerEffect implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Anything you hit gets the Hunger effect.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Hunger", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:apply_hunger_effect");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            AbilityRegister.runForAbility(event.getDamager(), getKey(), () -> entity.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 200, 0, false, true)));
        }
    }
}
