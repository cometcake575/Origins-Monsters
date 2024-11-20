package com.starshooterstudios.ort.abilities;

import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public class LightningImmunity implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("ort:lightning_immune");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType().equals(EntityType.LIGHTNING_BOLT)) {
            AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> event.setCancelled(true));
        }
    }
}
