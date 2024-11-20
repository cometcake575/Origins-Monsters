package com.starshooterstudios.ort.abilities.infpotions;

import net.kyori.adventure.key.Key;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class InfRes implements InfinitePotionEffectAbility {

    @Override
    public @NotNull Key getKey() {
        return Key.key("ort:inf_res");
    }

    @Override
    public PotionEffectType getEffect() {
        return PotionEffectType.RESISTANCE;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
