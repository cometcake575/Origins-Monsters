package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import net.kyori.adventure.key.Key;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public class Undead implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:undead");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> {
            if (event.getDamager() instanceof LivingEntity entity) {
                int level = entity.getActiveItem().getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
                event.setDamage(event.getDamage() + (2.5 * level));
            }
        });
    }
}
