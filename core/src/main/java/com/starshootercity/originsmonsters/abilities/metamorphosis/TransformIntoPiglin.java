package com.starshootercity.originsmonsters.abilities.metamorphosis;

import com.starshootercity.AddonLoader;
import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.events.PlayerSwapOriginEvent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class TransformIntoPiglin implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You transform into a Piglin if you eat a golden apple when under the effect of a weakness potion.";
    }

    @Override
    public String title() {
        return "Metamorphosis";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:transform_into_piglin");
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.GOLDEN_APPLE) return;
        if (!event.getPlayer().hasPotionEffect(PotionEffectType.WEAKNESS)) return;
        runForAbility(event.getPlayer(), this::switchToPiglin);
    }

    private void switchToPiglin(Player player) {
        player.getLocation().getWorld().playSound(player, Sound.ENTITY_PIGLIN_CONVERTED_TO_ZOMBIFIED, SoundCategory.PLAYERS, 1, 1);
        OriginSwapper.setOrigin(player, AddonLoader.getOrigin("piglin"), PlayerSwapOriginEvent.SwapReason.PLUGIN, false, "origin");
        player.sendMessage(Component.text("You have transformed into a Piglin!")
                .color(NamedTextColor.YELLOW));
    }
}
