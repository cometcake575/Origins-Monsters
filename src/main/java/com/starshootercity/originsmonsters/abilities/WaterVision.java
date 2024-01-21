package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WaterVision extends com.starshootercity.abilities.WaterVision {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Water Vision", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }
}
