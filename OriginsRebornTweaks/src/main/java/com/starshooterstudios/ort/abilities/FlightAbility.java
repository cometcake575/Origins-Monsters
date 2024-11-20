package com.starshooterstudios.ort.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.FlightAllowingAbility;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FlightAbility implements FlightAllowingAbility, VisibleAbility {
    @Override
    public boolean canFly(Player player) {
        return true;
    }

    @Override
    public float getFlightSpeed(Player player) {
        return 0.1f;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("ort:flight");
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can double tap jump to fly", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Flight", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }
}
