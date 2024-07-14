package com.starshootercity.originsmonsters;

import com.destroystokyo.paper.entity.ai.Goal;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public abstract class MonstersNMSInvoker {

    public abstract void dealExplosionDamage(Player player, int amount);

    public abstract void dealSonicBoomDamage(LivingEntity entity, int amount, Player source);

    public abstract @NotNull Enchantment getSmiteEnchantment();

    public abstract Goal<Villager> getVillagerAfraidGoal(LivingEntity villager, Predicate<Player> hasAbility);

    public abstract @Nullable Player getNearestVisiblePlayer(Piglin piglin);

    public abstract void throwItem(Piglin piglin, ItemStack itemStack, Location pos);

    public abstract void launchArrow(Entity projectile, Entity entity, float roll, float force, float divergence);

    public abstract void damageItem(ItemStack item, int amount, Player player);

    public abstract void startAutoSpinAttack(Player player, int duration, float riptideAttackDamage, ItemStack item);

    public abstract void tridentMove(Player player);
}