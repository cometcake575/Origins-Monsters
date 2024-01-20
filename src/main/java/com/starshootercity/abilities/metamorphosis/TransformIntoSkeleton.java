package com.starshootercity.abilities.metamorphosis;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TransformIntoSkeleton implements VisibleAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You transform into a Skeleton if you're in a warm area for too long.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Metamorphosis", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:transform_into_stray");
    }
}
