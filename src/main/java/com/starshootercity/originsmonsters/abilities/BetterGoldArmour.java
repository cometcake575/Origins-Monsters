package com.starshootercity.originsmonsters.abilities;

import com.destroystokyo.paper.MaterialTags;
import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.AttributeModifierAbility;
import com.starshootercity.abilities.VisibleAbility;
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

import java.util.List;

public class BetterGoldArmour implements VisibleAbility, AttributeModifierAbility, Listener {
    @Override
    public @NotNull Attribute getAttribute() {
        return Attribute.GENERIC_ARMOR;
    }

    @Override
    public double getAmount() {
        return 0;
    }

    @Override
    public double getChangedAmount(Player player) {
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
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("Your adoration for gold unlocks its hidden power, making golden armor unbreakable and as strong as diamond.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Gold Worshipper", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:better_gold_armour");
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        if (!MaterialTags.ARMOR.isTagged(event.getItem())) return;
        if (!event.getItem().getType().toString().contains("gold")) return;
        AbilityRegister.runForAbility(event.getPlayer(), getKey(), () -> event.setCancelled(true));
    }
}
