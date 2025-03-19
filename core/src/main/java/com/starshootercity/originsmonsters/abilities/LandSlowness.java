package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LandSlowness implements VisibleAbility, AttributeModifierAbility {
    @Override
    public String description() {
        return "You're used to the water, so move much slower on land.";
    }

    @Override
    public String title() {
        return "Water Based";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:land_slowness");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getMovementSpeedAttribute();
    }

    @Override
    public double getAmount(Player player) {
        return player.isInWater() ? 0 : -0.2;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    }
}
