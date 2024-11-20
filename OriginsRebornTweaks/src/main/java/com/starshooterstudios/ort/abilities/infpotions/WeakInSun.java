package com.starshooterstudios.ort.abilities.infpotions;

import com.destroystokyo.paper.MaterialTags;
import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.MultiAbility;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WeakInSun implements MultiAbility, VisibleAbility {

    @Override
    public @NotNull Key getKey() {
        return Key.key("ort:weak_in_sun");
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You get Weakness I and Slowness II in the sun", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Solar Weakness", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public List<Ability> getAbilities() {
        return List.of(WeaknessEffect.EFFECT, SlownessEffect.EFFECT);
    }

    public static boolean isInLight(Player player) {
        Block block = player.getWorld().getHighestBlockAt(player.getLocation());
        while ((MaterialTags.GLASS.isTagged(block) || (MaterialTags.GLASS_PANES.isTagged(block)) && block.getY() >= player.getLocation().getY())) {
            block = block.getRelative(BlockFace.DOWN);
        }
        boolean height = block.getY() < player.getLocation().getY();
        boolean isInOverworld = player.getWorld().getEnvironment() == World.Environment.NORMAL;
        boolean day = player.getWorld().isDayTime();
        return height && isInOverworld && day && !player.isInWaterOrRainOrBubbleColumn();
    }

    public static class WeaknessEffect implements InfinitePotionEffectAbility {
        public static WeaknessEffect EFFECT = new WeaknessEffect();

        @Override
        public PotionEffectType getEffect() {
            return PotionEffectType.WEAKNESS;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        @Override
        public @NotNull Key getKey() {
            return Key.key("ort:solar_weak_effect");
        }

        @Override
        public boolean enabled(Player player) {
            return isInLight(player);
        }
    }

    public static class SlownessEffect implements InfinitePotionEffectAbility {
        public static SlownessEffect EFFECT = new SlownessEffect();

        @Override
        public PotionEffectType getEffect() {
            return PotionEffectType.SLOWNESS;
        }

        @Override
        public int getLevel() {
            return 1;
        }

        @Override
        public @NotNull Key getKey() {
            return Key.key("ort:solar_slow_effect");
        }

        @Override
        public boolean enabled(Player player) {
            return isInLight(player);
        }
    }
}
