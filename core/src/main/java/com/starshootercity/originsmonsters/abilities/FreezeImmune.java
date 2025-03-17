package com.starshootercity.originsmonsters.abilities;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.abilities.Ability;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class FreezeImmune implements Ability, Listener {
    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:freeze_immune");
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            runForAbility(player, p -> p.setFreezeTicks(0));
        }
    }
}
