package com.starshootercity.originsmonsters.abilities.metamorphosis;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.AddonLoader;
import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.events.PlayerSwapOriginEvent;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class TransformIntoSkeleton implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You transform into a Skeleton if you're in a warm area for too long.";
    }

    @Override
    public String title() {
        return "Metamorphosis";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:transform_into_skeleton");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 20 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                if (MetamorphosisTemperature.getTemperature(player) >= 30) {
                    switchToSkeleton(player);
                }
            });
        }
    }

    private void switchToSkeleton(Player player) {
        player.getLocation().getWorld().playSound(player, Sound.ENTITY_SKELETON_CONVERTED_TO_STRAY, SoundCategory.PLAYERS, 1, 1);
        OriginSwapper.setOrigin(player, AddonLoader.getOrigin("skeleton"), PlayerSwapOriginEvent.SwapReason.PLUGIN, false, "origin");
        player.sendMessage(Component.text("You have transformed into a skeleton!")
                .color(NamedTextColor.YELLOW));
    }
}
