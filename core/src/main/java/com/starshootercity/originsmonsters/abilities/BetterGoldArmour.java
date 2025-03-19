package com.starshootercity.originsmonsters.abilities;

import com.destroystokyo.paper.MaterialTags;
import com.starshootercity.OriginsReborn;
import com.starshootercity.abilities.types.AttributeModifierAbility;
import com.starshootercity.abilities.types.VisibleAbility;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BetterGoldArmour implements VisibleAbility, AttributeModifierAbility, Listener {
    @Override
    public @NotNull Attribute getAttribute() {
        return OriginsReborn.getNMSInvoker().getArmorAttribute();
    }

    @Override
    public double getAmount(Player player) {
        int amount = 0;
        for (ItemStack item : player.getEquipment().getArmorContents()) {
            if (item == null) continue;
            if (item.getType() == Material.GOLDEN_HELMET) {
                amount += 1;
            } else if (item.getType() == Material.GOLDEN_CHESTPLATE) {
                amount += 3;
            } else if (item.getType() == Material.GOLDEN_LEGGINGS) {
                amount += 3;
            } else if (item.getType() == Material.GOLDEN_BOOTS) {
                amount += 2;
            }
        }
        return amount;
    }

    @Override
    public AttributeModifier.@NotNull Operation getOperation() {
        return AttributeModifier.Operation.ADD_NUMBER;
    }

    @Override
    public String description() {
        return "Your adoration for gold unlocks its hidden power, making golden armor unbreakable and as strong as diamond.";
    }

    @Override
    public String title() {
        return "Gold Worshipper";
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:better_gold_armour");
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        if (!MaterialTags.ARMOR.isTagged(event.getItem())) return;
        if (!event.getItem().getType().toString().toLowerCase().contains("gold")) return;
        runForAbility(event.getPlayer(), player -> event.setCancelled(true));
    }
}
