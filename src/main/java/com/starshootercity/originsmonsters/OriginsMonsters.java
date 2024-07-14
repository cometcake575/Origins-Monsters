package com.starshootercity.originsmonsters;

import com.starshootercity.OriginsAddon;
import com.starshootercity.abilities.Ability;
import com.starshootercity.originsmonsters.abilities.*;
import com.starshootercity.originsmonsters.abilities.ZombieTouch;
import com.starshootercity.originsmonsters.abilities.metamorphosis.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
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
                new DoubleFireDamage(),
                new BurnInDay(),
                new Undead(),
                new TridentExpert(),
                new ZombieHunger(),
                new WitherImmunity(),
                new HalfMaxSaturation(),
                new GuardianAlly(),
                new WaterCombatant(),
                new UndeadAlly(),
                new ApplyWitherEffect(),
                new InfiniteArrows(),
                new SlownessArrows(),
                new ApplyHungerEffect(),
                new SkeletonBody(),
                new Slowness(),
                new LandSlowness(),
                new WaterBreathing(),
                new SwimSpeed(),
                new FreezeImmune(),
                new HeatSlowness(),
                new BetterAim(),
                new ColdSlowness(),
                new ZombieTouch(),
                new ScareVillagers(),
                new TransformIntoZombifiedPiglin(),
                new TransformIntoPiglin(),
                new BetterGoldArmour(),
                new BetterGoldWeapons(),
                new ZombifiedPiglinAllies(),
                new SuperBartering(),
                new PiglinAlly()
        );
    }
}