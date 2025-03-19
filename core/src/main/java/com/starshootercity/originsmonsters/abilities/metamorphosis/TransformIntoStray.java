package com.starshootercity.originsmonsters.abilities.metamorphosis;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.AddonLoader;
import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.types.VisibleAbility;
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
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TransformIntoStray implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You transform into a Stray if you're in the cold for too long.";
    }

    @Override
    public String title() {
        return "Metamorphosis";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:transform_into_stray");
    }

    private final Map<Player, Integer> lastHadLowFreezeTime = new HashMap<>();

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 20 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                if (player.getFreezeTicks() >= player.getMaxFreezeTicks()) {
                    lastHadLowFreezeTime.put(player, Bukkit.getCurrentTick());
                }
                if (Bukkit.getCurrentTick() - lastHadLowFreezeTime.getOrDefault(player, Bukkit.getCurrentTick()) >= 300) {
                    MetamorphosisTemperature.setTemperature(player, 25);
                }
                if (MetamorphosisTemperature.getTemperature(player) <= 25) {
                    switchToStray(player);
                }
            });
        }
    }

    private void switchToStray(Player player) {
        player.getLocation().getWorld().playSound(player, Sound.ENTITY_SKELETON_CONVERTED_TO_STRAY, SoundCategory.PLAYERS, 1, 1);
        OriginSwapper.setOrigin(player, AddonLoader.getOrigin("stray"), PlayerSwapOriginEvent.SwapReason.PLUGIN, false, "origin");
        player.sendMessage(Component.text("You have transformed into a stray!")
                .color(NamedTextColor.YELLOW));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FREEZE) {
            runForAbility(event.getEntity(), player -> event.setCancelled(true));
        }
    }
}
