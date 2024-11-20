package com.reforgedsmpreworked;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ForceUpgradeCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            ReforgedSMPReworkedMain.addGearAmount(meta.getPersistentDataContainer(), 1000);
            item.setItemMeta(meta);
            player.getInventory().setItemInMainHand(UpgradeCommandExecutor.updateItem(item).item());
            return true;
        }
        sender.sendMessage(Component.text("This command can only be used by a player").color(NamedTextColor.RED));
        return true;
    }
}
