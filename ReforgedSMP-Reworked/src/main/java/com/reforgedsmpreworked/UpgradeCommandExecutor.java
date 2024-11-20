package com.reforgedsmpreworked;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UpgradeCommandExecutor implements CommandExecutor, Listener {
    private final NamespacedKey blankKey = new NamespacedKey(ReforgedSMPReworkedMain.getInstance(), "upgrade-gui");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command can only be used by a player").color(NamedTextColor.RED));
            return true;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getItemMeta() == null || !item.getItemMeta().getPersistentDataContainer().has(ReforgedSMPReworkedMain.gearKey)) {
            sender.sendMessage(Component.text("You must be holding an upgradeable item to use this").color(NamedTextColor.RED));
            return true;
        }
        if (ReforgedSMPReworkedMain.getGearLevel(item.getItemMeta().getPersistentDataContainer()) >= 5) {
            sender.sendMessage(Component.text("This item is already fully upgraded").color(NamedTextColor.RED));
            return true;
        }
        Inventory inventory = Bukkit.createInventory(null, InventoryType.DISPENSER, Component.text("Upgrade"));
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = filler.getItemMeta();
        meta.displayName(Component.empty());
        meta.getPersistentDataContainer().set(blankKey, PersistentDataType.BOOLEAN, true);
        filler.setItemMeta(meta);
        for (int i = 0; i < 9; i += i == 3 ? 2 : 1) {
            inventory.setItem(i, filler);
        }
        player.openInventory(inventory);
        return true;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
            if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(blankKey)) {
                event.setCancelled(true);
                return;
            }
        }
        Inventory inventory = event.getWhoClicked().getOpenInventory().getTopInventory();
        ItemStack firstItemSlot = inventory.getItem(0);
        if (firstItemSlot != null && firstItemSlot.getItemMeta() != null) {
            if (firstItemSlot.getPersistentDataContainer().has(blankKey)) {

                ItemStack currentItem = event.getCurrentItem();
                if (currentItem != null && currentItem.getItemMeta() != null) {
                    if (!ReforgedSMPReworkedMain.getGearID(currentItem.getItemMeta().getPersistentDataContainer()).isEmpty()) {
                        event.setCancelled(true);
                        return;
                    }
                } else if (event.getClick() == ClickType.NUMBER_KEY) {
                    ItemStack s = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
                    if (s != null && s.getItemMeta() != null && s.getItemMeta().getPersistentDataContainer().has(ReforgedSMPReworkedMain.gearKey)) {
                        event.setCancelled(true);
                        return;
                    }
                }

                Bukkit.getScheduler().scheduleSyncDelayedTask(ReforgedSMPReworkedMain.getInstance(), () -> {
                    ItemStack upgradeItem = event.getWhoClicked().getOpenInventory().getTopInventory().getItem(4);
                    if (upgradeItem == null) return;
                    ItemStack gearItem = event.getWhoClicked().getInventory().getItemInMainHand();
                    if (gearItem.getItemMeta() == null) return;
                    String gearID = ReforgedSMPReworkedMain.getGearID(gearItem.getItemMeta().getPersistentDataContainer());
                    if (gearID.isEmpty()) return;

                    ReforgedSMPReworkedMain.GearEffects effects = ReforgedSMPReworkedMain.effectMap.getOrDefault(gearID, null);
                    if (effects == null) return;
                    int gearLevel = ReforgedSMPReworkedMain.getGearLevel(gearItem.getItemMeta().getPersistentDataContainer());

                    ReforgedSMPReworkedMain.UpgradeItem itemForUpgrade = effects.nextUpgrade(gearLevel);

                    if (itemForUpgrade == null) return;

                    if (itemForUpgrade.component().doesAcceptMaterial(upgradeItem.getType())) {
                        ItemMeta meta = gearItem.getItemMeta();
                        int remaining = ReforgedSMPReworkedMain.addGearAmount(meta.getPersistentDataContainer(), upgradeItem.getAmount());
                        if (remaining == 0) {
                            inventory.setItem(4, null);
                        } else inventory.setItem(4, upgradeItem.subtract(upgradeItem.getAmount() - remaining));
                        gearItem.setItemMeta(meta);
                    }


                    UpdateResult result = updateItem(gearItem);
                    event.getWhoClicked().getInventory().setItemInMainHand(result.item);
                    if (result.complete) event.getWhoClicked().closeInventory();
                }, 4);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killer = event.getPlayer().getKiller();
        if (killer == null) return;
        for (ItemStack item : killer.getInventory()) {
            if (item == null || item.getItemMeta() == null) continue;
            if (item.getItemMeta().getPersistentDataContainer().has(ReforgedSMPReworkedMain.gearKey)) {
                String id = ReforgedSMPReworkedMain.getGearID(item.getItemMeta().getPersistentDataContainer());
                int level = ReforgedSMPReworkedMain.getGearLevel(item.getItemMeta().getPersistentDataContainer());
                ReforgedSMPReworkedMain.UpgradeItem upgradeItem = ReforgedSMPReworkedMain.effectMap.get(id).nextUpgrade(level);
                if (upgradeItem == null) continue;
                Integer killCount = upgradeItem.component().getKillCount();
                if (killCount == null) continue;
                ItemMeta meta = item.getItemMeta();
                ReforgedSMPReworkedMain.addGearAmount(meta.getPersistentDataContainer(), 1);
                item.setItemMeta(meta);
                updateItem(item);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        ItemStack firstItemSlot = event.getInventory().getItem(0);
        if (firstItemSlot != null && firstItemSlot.getItemMeta() != null) {
            if (firstItemSlot.getPersistentDataContainer().has(blankKey)) {
                ItemStack i = event.getInventory().getItem(4);
                if (i != null) {
                    for (ItemStack item : event.getPlayer().getInventory().addItem(i).values()) {
                        event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item);
                    }
                }
            }
        }
    }

    public static UpdateResult updateItem(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        int remaining = ReforgedSMPReworkedMain.getGearAmount(item.getItemMeta().getPersistentDataContainer());
        int level = ReforgedSMPReworkedMain.getGearLevel(item.getItemMeta().getPersistentDataContainer());
        ReforgedSMPReworkedMain.UpgradeItem upgradeItem = ReforgedSMPReworkedMain.effectMap.get(ReforgedSMPReworkedMain.getGearID(item.getItemMeta().getPersistentDataContainer())).nextUpgrade(level);
        String name;
        if (upgradeItem == null) name = null;
        else name = remaining == 1 ? upgradeItem.singularName() : upgradeItem.pluralName();
        meta.lore(List.of(
                Component.text("Level %s".formatted(level)).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA),
                Component.text(name == null ? "This item is fully upgraded" : "You need %s %s to reach level %s".formatted(remaining, name, level + 1)).decoration(TextDecoration.ITALIC, false).color(NamedTextColor.AQUA)
        ));
        item.setItemMeta(meta);

        return new UpdateResult(item, level >= 5);
    }

    public record UpdateResult(ItemStack item, boolean complete) {}
}
