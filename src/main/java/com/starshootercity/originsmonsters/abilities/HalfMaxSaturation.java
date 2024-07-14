package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HalfMaxSaturation implements VisibleAbility, Listener {
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        AbilityRegister.runForAbility(event.getEntity(), getKey(), () -> event.getEntity().setSaturation(Math.min(event.getEntity().getSaturation(), (float) event.getEntity().getFoodLevel() / 2)));
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can only hold half as much saturation as a human.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Poor Digestion", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:half_max_saturation");
    }
}
