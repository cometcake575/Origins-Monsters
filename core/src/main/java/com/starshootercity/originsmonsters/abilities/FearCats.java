package com.starshootercity.originsmonsters.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmonsters.OriginsMonsters;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class FearCats implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You get nausea and weakness when around cats.";
    }

    @Override
    public String title() {
        return "Afraid of Cats";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:fear_cats");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        if (event.getTickNumber() % 5 != 0) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                List<Entity> entities = player.getNearbyEntities(8, 8, 8);
                entities.removeIf(entity -> entity.getType() != EntityType.CAT);
                if (!entities.isEmpty()) {
                    player.addPotionEffect(new PotionEffect(OriginsReborn.getNMSInvoker().getNauseaEffect(), 200, 0, false, true));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, getConfigOption(OriginsMonsters.getInstance(), weaknessStrength, SettingType.INTEGER), false, true));
                }
            });
        }
    }

    private final String weaknessStrength = "weakness_strength";

    @Override
    public void initialize() {
        registerConfigOption(OriginsMonsters.getInstance(), weaknessStrength, Collections.singletonList("How strong the weakness effect should be"), SettingType.INTEGER, 0);
    }
}
