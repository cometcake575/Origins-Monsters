package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

public class Slowness implements VisibleAbility, AttributeModifierAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getMovementSpeedAttribute();
    }

    @Override
    public double getAmount() {
        return -0.15;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    }

    @Override
    public String description() {
        return "Your undead body moves at a slower pace than humans.";
    }

    @Override
    public String title() {
        return "Zombie Slowness";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:slowness");
    }
}
