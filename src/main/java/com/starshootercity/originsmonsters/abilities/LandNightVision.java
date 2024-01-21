package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.CatVision;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LandNightVision extends CatVision {
    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:land_night_vision");
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can see in the dark when on land.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Dark Vision", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }
}
