package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.cooldowns.CooldownAbility;
import com.starshootercity.cooldowns.Cooldowns;
import com.starshootercity.events.PlayerLeftClickEvent;
import com.starshootercity.originsmonsters.OriginsMonsters;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import net.kyori.adventure.key.Key;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TridentExpert implements VisibleAbility, Listener, AttributeModifierAbility, CooldownAbility {
    @Override
    public String description() {
        return "You're a master of the trident, dealing +2 damage when you throw it, and +2 melee damage with it. You can also use channeling without thunder, and use riptide without rain/water at the price of extra durability.";
    }

    @Override
    public String title() {
        return "Trident Expert";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:trident_expert");
    }

    private final NamespacedKey riptideKey = new NamespacedKey(OriginsMonsters.getInstance(), "riptide-trident");

    public ItemStack fixTrident(ItemStack item) {
        if (item == null) return null;
        if (item.getItemMeta() == null) return item;
        if (item.getItemMeta().getPersistentDataContainer().has(riptideKey)) {
            ItemMeta meta = item.getItemMeta();
            int level = item.getItemMeta().getPersistentDataContainer().getOrDefault(riptideKey, PersistentDataType.INTEGER, 1);
            meta.getPersistentDataContainer().remove(riptideKey);
            meta.addEnchant(Enchantment.RIPTIDE, level, false);
            meta.removeEnchant(Enchantment.FROST_WALKER);
            item.setItemMeta(meta);
        }
        return item;
    }

    @EventHandler
    public void onPlayerStopUsingItem(PlayerStopUsingItemEvent event) {
        if (event.getItem().getType() != Material.TRIDENT) return;
        if (event.getTicksHeldFor() >= 10 && event.getItem().getItemMeta().getPersistentDataContainer().has(riptideKey)) {
            releaseUsing(fixTrident(event.getItem()), event.getPlayer().getWorld(), event.getPlayer());
            OriginsMonsters.getNMSInvoker().damageItem(event.getItem(), 10, event.getPlayer());
        } else fixTrident(event.getItem());
    }

    @EventHandler
    public void onPlayerLeave(PlayerJoinEvent event) {
        fixTrident(event.getPlayer().getInventory().getItemInMainHand());
        fixTrident(event.getPlayer().getInventory().getItemInOffHand());
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItem(event.getPreviousSlot());
        if (item != null && item.getType() == Material.TRIDENT) fixTrident(item);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer().isInWaterOrRainOrBubbleColumn()) return;
        if (Bukkit.getCurrentTick() - lastTridentEnabledTime.getOrDefault(event.getPlayer(), Bukkit.getCurrentTick() - 400) >= 400) return;
        if (event.getItem() == null || event.getItem().getType() != Material.TRIDENT) return;
        if (!event.getItem().getItemMeta().hasEnchant(Enchantment.RIPTIDE)) return;
        ItemMeta meta = event.getItem().getItemMeta();
        event.getItem().setItemMeta(meta);
        meta.getPersistentDataContainer().set(riptideKey, PersistentDataType.INTEGER, meta.getEnchantLevel(Enchantment.RIPTIDE));
        meta.removeEnchant(Enchantment.RIPTIDE);
        if (meta.getEnchants().isEmpty()) {
            meta.addEnchant(Enchantment.FROST_WALKER, 1, true);
        }
        event.getItem().setItemMeta(meta);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        event.getItemDrop().setItemStack(fixTrident(event.getItemDrop().getItemStack()));
    }

    private final Map<Player, Integer> lastTridentEnabledTime = new HashMap<>();

    @EventHandler
    public void onPlayerLeftClick(PlayerLeftClickEvent event) {
        if (List.of(Material.AIR, Material.TRIDENT).contains(event.getPlayer().getInventory().getItemInMainHand().getType())) {
            runForAbility(event.getPlayer(), player -> {
                if (hasCooldown(player)) return;
                setCooldown(player);
                lastTridentEnabledTime.put(player, Bukkit.getCurrentTick());
            });
        }
    }

    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getAttackDamageAttribute();
    }

    @Override
    public double getAmount() {
        return 0;
    }

    @Override
    public double getChangedAmount(Player player) {
        return player.getInventory().getItemInMainHand().getType() == Material.TRIDENT && Bukkit.getCurrentTick() - lastTridentEnabledTime.getOrDefault(player, Bukkit.getCurrentTick() - 400) < 400 ? 2 : 0;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player player) {
            if (event.getEntity() instanceof Trident trident) {
                runForAbility(player, p -> {
                    if (Bukkit.getCurrentTick() - lastTridentEnabledTime.getOrDefault(p, Bukkit.getCurrentTick() - 400) < 400) {
                        trident.setDamage(trident.getDamage() + 2);
                    }
                });
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getHitEntity() == null) return;
        if (event.getEntity().getShooter() instanceof Player player && event.getEntity() instanceof Trident trident) {
            runForAbility(player, p -> {
                if (Bukkit.getCurrentTick() - lastTridentEnabledTime.getOrDefault(p, Bukkit.getCurrentTick() - 400) < 400) {
                    if (trident.getItemStack().getItemMeta().hasEnchant(Enchantment.CHANNELING)) {
                        event.getHitEntity().getWorld().strikeLightning(event.getHitEntity().getLocation());
                    }
                }
            });
        }
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @SuppressWarnings("deprecation")
    public void releaseUsing(ItemStack stack, World world, Player player) {
        int k = stack.getEnchantmentLevel(Enchantment.RIPTIDE);

        if (k > 0) {
            PlayerRiptideEvent event = new PlayerRiptideEvent(player, stack);
            event.getPlayer().getServer().getPluginManager().callEvent(event);
            float f = player.getLocation().getYaw();
            float f1 = player.getLocation().getPitch();
            double f2 = -Math.sin(f * 0.017453292F) * Math.cos(f1 * 0.017453292F);
            double f3 = -Math.sin(f1 * 0.017453292F);
            double f4 = Math.cos(f * 0.017453292F) * Math.cos(f1 * 0.017453292F);
            double f5 = Math.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
            float f6 = 3.0F * ((1.0F + (float) k) / 4.0F);

            f2 *= f6 / f5;
            f3 *= f6 / f5;
            f4 *= f6 / f5;
            if (player.isOnGround()) {
                OriginsMonsters.getNMSInvoker().tridentMove(player);
            }
            player.setVelocity(player.getVelocity().add(new Vector(f2, f3, f4)));
            OriginsMonsters.getNMSInvoker().startAutoSpinAttack(player, 20, 8.0F, stack);

            Sound soundeffect;

            if (k >= 3) {
                soundeffect = Sound.ITEM_TRIDENT_RIPTIDE_3;
            } else if (k == 2) {
                soundeffect = Sound.ITEM_TRIDENT_RIPTIDE_2;
            } else {
                soundeffect = Sound.ITEM_TRIDENT_RIPTIDE_1;
            }

            world.playSound(player.getLocation(), soundeffect, 1, 1);

        }
    }

    @Override
    public Cooldowns.CooldownInfo getCooldownInfo() {
        return new Cooldowns.CooldownInfo(400, "trident_expert", true);
    }
}
