package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.jetbrains.annotations.NotNull;

public class HalfMaxSaturation implements VisibleAbility, Listener {
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        runForAbility(event.getEntity(), player -> player.setSaturation(Math.min(player.getSaturation(), (float) player.getFoodLevel() / 2)));
    }

    @Override
    public String description() {
        return "You can only hold half as much saturation as a human.";
    }

    @Override
    public String title() {
        return "Poor Digestion";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:half_max_saturation");
    }
}
