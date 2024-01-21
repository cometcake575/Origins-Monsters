package com.starshootercity.originsmonsters;

import com.starshootercity.OriginsAddon;
import com.starshootercity.abilities.Ability;
import com.starshootercity.originsmonsters.abilities.*;
import com.starshootercity.originsmonsters.abilities.metamorphosis.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OriginsMonsters extends OriginsAddon {
    @Override
    public @NotNull List<Ability> getAbilities() {
        return List.of(
                new CreeperAlly(),
                new Explosive(),
                new FearCats(),
                new DrownedTransformIntoZombie(),
                new HuskTransformIntoZombie(),
                new TransformIntoHuskAndDrowned(),
                new TransformIntoStray(),
                new TransformIntoSkeleton(),
                new MetamorphosisTemperature(),
                new Blindness(),
                new SenseMovement(),
                new DoubleHealth(),
                new DoubleDamage(),
                new SonicBoom(),
                new WaterVision(),
                new LandNightVision(),
                new DoubleFireDamage()
        );
    }
}