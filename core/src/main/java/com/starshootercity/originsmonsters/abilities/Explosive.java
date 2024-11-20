package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.cooldowns.CooldownAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.originsmonsters.OriginsMonsters;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Explosive implements VisibleAbility, Listener, CooldownAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can sacrifice some of your health to create an explosion every 15 seconds.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Explosive", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:explosive");
    }
    private final Map<Player, Integer> lastToggledSneak = new HashMap<>();

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            if (hasCooldown(event.getPlayer())) return;
            if (!event.isSneaking()) return;
            if (Bukkit.getCurrentTick() - lastToggledSneak.getOrDefault(event.getPlayer(), Bukkit.getCurrentTick() - 11) <= 10) {
                setCooldown(event.getPlayer());
                event.getPlayer().getLocation().createExplosion(event.getPlayer(), 3, false, OriginsMonsters.getInstance().getConfig().getBoolean("creeper-explosion-breaks-blocks", true));
                OriginsMonsters.getNMSInvoker().dealExplosionDamage(event.getPlayer(), 8);
            } else {
                lastToggledSneak.put(event.getPlayer(), Bukkit.getCurrentTick());
            }
        });
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(300, "explosive");
    }
}
