package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.CatVision;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class LandNightVision extends CatVision {
    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:land_night_vision");
    }

    @Override
    public String description() {
        return "You can see in the dark when on land.";
    }

    @Override
    public String title() {
        return "Dark Sight";
    }
}
