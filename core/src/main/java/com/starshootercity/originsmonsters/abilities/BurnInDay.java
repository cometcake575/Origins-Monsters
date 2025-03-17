package com.starshootercity.originsmonsters.abilities;

import com.destroystokyo.paper.MaterialTags;
import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.util.config.ConfigManager;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BurnInDay implements VisibleAbility, Listener {
    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent ignored) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            runForAbility(p, player -> {
                        Block block = player.getWorld().getHighestBlockAt(player.getLocation());
                        while ((MaterialTags.GLASS.isTagged(block) || (MaterialTags.GLASS_PANES.isTagged(block)) && block.getY() >= player.getLocation().getY())) {
                            block = block.getRelative(BlockFace.DOWN);
                        }
                        boolean height = block.getY() < player.getLocation().getY();
                        boolean isInOverworld = player.getWorld().getEnvironment().equals(World.Environment.NORMAL);
                        boolean day = player.getWorld().isDayTime();

                        if (!getConfigOption(OriginsReborn.getInstance(), burnWithHelmet, ConfigManager.SettingType.BOOLEAN)) {
                            ItemStack helm = player.getInventory().getHelmet();
                            if (helm != null) {
                                if (!helm.getType().isAir()) return;
                            }
                        }

                        if (height && isInOverworld && day && !player.isInWaterOrRainOrBubbleColumn()) {
                            player.setFireTicks(Math.max(player.getFireTicks(), 60));
                        }
                    });
        }
    }

    private final String burnWithHelmet = "burn_with_helmet";

    @Override
    public void initialize() {
        registerConfigOption(OriginsReborn.getInstance(), burnWithHelmet, List.of("Whether the player should burn even when wearing a helmet"), ConfigManager.SettingType.BOOLEAN, true);
    }

    @Override
    public String description() {
        return "You burn in daylight.";
    }

    @Override
    public String title() {
        return "Photoallergic";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:burn_in_day");
    }
}
