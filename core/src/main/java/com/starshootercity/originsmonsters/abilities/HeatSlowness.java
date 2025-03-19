package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HeatSlowness implements VisibleAbility, AttributeModifierAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getMovementSpeedAttribute();
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    }

    @Override
    public String description() {
        return "Your cold body conflicts with warmer biomes, slowing you down.";
    }

    @Override
    public String title() {
        return "Cold Body";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:heat_slowness");
    }

    @Override
    public double getAmount(Player player) {
        double temp = player.getLocation().getBlock().getTemperature();
        if (temp >= 2) return -0.2;
        else if (temp >= 1.5) return -0.15;
        else if (temp >= 1) return -0.1;
        else if (temp >= 0.5) return -0.05;
        else return 0;
    }
}
