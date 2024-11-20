package com.starshooterstudios.ort.abilities;

import com.starshootercity.abilities.Ability;
import com.starshootercity.abilities.AbilityRegister;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;

public class RidingAbility implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("ort:ride_big_things");
    }

    private final Key smallBodyKey = Key.key("fantasyorigins:small_body");

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player player) {
            AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
                AbilityRegister.runForAbility(player, smallBodyKey, () -> {}, () -> {
                    player.addPassenger(event.getPlayer());
                });
            });
        }
    }
}
