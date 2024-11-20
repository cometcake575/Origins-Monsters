package com.starshooterstudios.ort.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshooterstudios.ort.Main;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LightningHand implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You can strike lightning and teleport when you right click with your empty hand", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Lightning Swing", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("ort:lightning_hand");
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;
        if (event.getHand() == null) return;
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (event.getItem() != null) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> {
            Block block = event.getClickedBlock();
            if (block == null) return;
            if (block.getType().isInteractable()) return;
            block.getWorld().strikeLightning(block.getLocation()).getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);
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
}
