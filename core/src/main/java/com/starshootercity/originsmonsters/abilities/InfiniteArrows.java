package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.jetbrains.annotations.NotNull;

public class InfiniteArrows implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "Arrows you shoot are not used up.";
    }

    @Override
    public String title() {
        return "Infinite Arrows";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:infinite_arrows");
    }

    @EventHandler
    public void onPlayerLaunchProjectile(EntityShootBowEvent event) {
        if (event.getConsumable() == null) return;
        if (event.getConsumable().getType() != Material.ARROW) return;
        if (event.getEntity() instanceof Player player) {
            runForAbility(player, p -> p.getInventory().addItem(event.getConsumable()));
        }
    }
}
