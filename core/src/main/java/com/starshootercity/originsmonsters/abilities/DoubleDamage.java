package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DoubleDamage implements VisibleAbility, AttributeModifierAbility {
    @Override
    public String description() {
        return "You deal twice as much damage as a normal player.";
    }

    @Override
    public String title() {
        return "Powerful Swings";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:double_damage");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getAttackDamageAttribute();
    }

    @Override
    public double getAmount(Player player) {
        return 1;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    }
}
