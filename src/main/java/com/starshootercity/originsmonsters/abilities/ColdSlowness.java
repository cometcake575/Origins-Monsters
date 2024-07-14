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

public class ColdSlowness implements VisibleAbility, AttributeModifierAbility {
    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.GENERIC_MOVEMENT_SPEED;
    }

    @Override
    public double getAmount() {
        return 0;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.MULTIPLY_SCALAR_1;
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Your warm body conflicts with colder biomes, slowing you down.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Warm Body", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:cold_slowness");
    }

    @Override
    public double getChangedAmount(Player player) {
        double temp = player.getLocation().getBlock().getTemperature();
        if (temp <= 0) return -0.2;
        else if (temp <= 0.5) return -0.15;
        else if (temp <= 1) return -0.1;
        else if (temp <= 1.5) return -0.05;
        else return 0;
    }
}
