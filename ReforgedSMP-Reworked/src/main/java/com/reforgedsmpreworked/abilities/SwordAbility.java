package com.reforgedsmpreworked.abilities;

import com.reforgedsmpreworked.ReforgedSMPReworkedMain;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class SwordAbility implements Ability {
    @Override
    public void runAbility(Player player) {
        for (Entity entity : player.getNearbyEntities(20, 20, 20)) {
            if (entity.equals(player)) continue;
            if (entity instanceof Player p) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 400, 255, false, false, false));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 400, 10, false, false, false));
            }
        }
    }

    @Override
    public int getCooldown() {
        return 9600;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return new NamespacedKey(ReforgedSMPReworkedMain.getInstance(), "swordability");
    }

    @Override
    public NamedTextColor color() {
        return NamedTextColor.BLACK;
    }
}
