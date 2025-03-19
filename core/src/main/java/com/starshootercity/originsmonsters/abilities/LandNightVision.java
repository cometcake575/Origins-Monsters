package com.starshootercity.originsmonsters.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginsReborn;
import com.starshootercity.SavedPotionEffect;
import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.util.ShortcutUtils;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LandNightVision implements Listener, VisibleAbility {
    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:land_night_vision");
    }

    @Override
    public String description() {
        return "You can see in the dark when on land.";
    }

    @Override
    public String title() {
        return "Dark Sight";
    }


    Map<Player, SavedPotionEffect> storedEffects = new HashMap<>();

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {

        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, (player) -> {
                PotionEffect effect;
                if (!OriginsReborn.getNMSInvoker().isUnderWater(player)) {
                    effect = player.getPotionEffect(PotionEffectType.NIGHT_VISION);
                    boolean ambient = false;
                    boolean showParticles = false;
                    if (effect != null) {
                        ambient = effect.isAmbient();
                        showParticles = effect.hasParticles();
                        if (!ShortcutUtils.isInfinite(effect)) {
                            this.storedEffects.put(player, new SavedPotionEffect(effect, Bukkit.getCurrentTick()));
                            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        }
                    }

                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, ShortcutUtils.infiniteDuration(), -1, ambient, showParticles));
                } else {
                    if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                        effect = player.getPotionEffect(PotionEffectType.NIGHT_VISION);
                        if (effect != null && ShortcutUtils.isInfinite(effect)) {
                            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        }
                    }

                    if (this.storedEffects.containsKey(player)) {
                        SavedPotionEffect effectx = storedEffects.get(player);
                        this.storedEffects.remove(player);
                        PotionEffect potionEffect = effectx.effect();
                        int time = potionEffect.getDuration() - (Bukkit.getCurrentTick() - effectx.currentTime());
                        if (time > 0) {
                            player.addPotionEffect(new PotionEffect(potionEffect.getType(), time, potionEffect.getAmplifier(), potionEffect.isAmbient(), potionEffect.hasParticles()));
                        }
                    }
                }

            });
        }

    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.MILK_BUCKET) {
            this.storedEffects.remove(event.getPlayer());
        }

    }
}
