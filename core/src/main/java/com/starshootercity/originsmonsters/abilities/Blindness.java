package com.starshootercity.originsmonsters.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class Blindness implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You can't see anything further than a few blocks away, though you can see further with night vision.";
    }

    @Override
    public String title() {
        return "Blindness";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:blindness");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 5 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    player.removePotionEffect(PotionEffectType.BLINDNESS);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 240, 0, false, false));
                } else {
                    player.removePotionEffect(PotionEffectType.DARKNESS);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 240, 0, false, false));
                }
            });
        }
    }
}
