package com.starshooterstudios.ort.abilities.infpotions;

import net.kyori.adventure.key.Key;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class InfRegX implements InfinitePotionEffectAbility {
    @Override
    public PotionEffectType getEffect() {
        return PotionEffectType.REGENERATION;
    }

    @Override
    public int getLevel() {
        return 9;
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("ort:inf_regx");
    }
}
