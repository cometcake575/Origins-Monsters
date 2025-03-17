package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.Ability;
import com.starshootercity.originsmonsters.OriginsMonsters;
import net.kyori.adventure.key.Key;
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
        runForAbility(event.getEntity(), player -> {
            if (event.getDamager() instanceof LivingEntity entity) {
                int level = entity.getActiveItem().getEnchantmentLevel(OriginsMonsters.getNMSInvoker().getSmiteEnchantment());
                event.setDamage(event.getDamage() + (2.5 * level));
            }
        });
    }
}
