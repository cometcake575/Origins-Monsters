package com.starshooterstudios.ort.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.cooldowns.CooldownAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshooterstudios.ort.Main;
import net.kyori.adventure.key.Key;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LightningSword implements VisibleAbility, Listener, CooldownAbility {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can strike lightning and teleport when you right click with a sword", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Lightning Sword", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("ort:lightning_sword");
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (hasCooldown(event.getPlayer())) return;
        if (!event.getAction().isRightClick()) return;
        if (event.getItem() == null) return;
        if (!Tag.ITEMS_SWORDS.isTagged(event.getItem().getType())) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            Block block = event.getPlayer().getTargetBlockExact(500, FluidCollisionMode.NEVER);
            if (block == null) return;
            if (block.getType().isInteractable()) return;
            block.getWorld().strikeLightning(block.getLocation()).getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
            setCooldown(event.getPlayer());
            Location loc = block.getLocation().add(0, 1, 0);
            loc.setPitch(event.getPlayer().getPitch());
            loc.setYaw(event.getPlayer().getYaw());
            event.getPlayer().teleport(loc);
        });
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager().getPersistentDataContainer().has(key)) event.setDamage(1);
    }

    private final NamespacedKey key = new NamespacedKey(Main.getInstance(), "lightning-sword");

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(40, "lightning");
    }
}
