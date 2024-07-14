package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LandSlowness implements VisibleAbility, AttributeModifierAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You're used to the water, so move much slower on land.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Water Based", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:land_slowness");
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.GENERIC_MOVEMENT_SPEED;
    }

    @Override
    public double getAmount() {
        return 0;
    }

    @Override
    public double getChangedAmount(Player player) {
        return player.isInWater() ? 0 : -0.2;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    }
}
