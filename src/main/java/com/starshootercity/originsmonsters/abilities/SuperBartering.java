package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import net.kyori.adventure.key.Key;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPiglin;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PiglinBarterEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SuperBartering implements VisibleAbility, Listener {
    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getDescription() {
        return OriginSwapper.LineData.makeLineFor("You're brilliant at bartering after a lifetime of experience, every time you barter you get between 2 and 5 times as many valuables.", OriginSwapper.LineData.LineComponent.LineType.DESCRIPTION);
    }

    @Override
    public @NotNull List<OriginSwapper.LineData.LineComponent> getTitle() {
        return OriginSwapper.LineData.makeLineFor("Bartering Master", OriginSwapper.LineData.LineComponent.LineType.TITLE);
    }

    @Override
    public @NotNull Key getKey() {
        return Key.key("monsterorigins:super_bartering");
    }

    private static final Random random = new Random();

    private static void throwItemsTowardPos(Piglin piglin, Collection<ItemStack> items, Vec3 pos) {
        for (ItemStack itemStack : items) {
            BehaviorUtils.throwItem(piglin, CraftItemStack.asNMSCopy(itemStack), pos.add(0.0D, 1.0D, 0.0D));
        }
    }

    private static void throwItemsTowardPlayer(Piglin piglin, Player player, Collection<ItemStack> items) {
        throwItemsTowardPos(piglin, items, player.position());
    }

    private static Collection<ItemStack> getBarterResponseItems(Piglin piglin) {
        return LootTables.PIGLIN_BARTERING.getLootTable().populateLoot(random, new LootContext.Builder(piglin.getBukkitEntity().getLocation()).lootedEntity(piglin.getBukkitEntity()).build());
    }

    @EventHandler
    public void onPiglinBarter(PiglinBarterEvent event) {
        Piglin piglin = ((CraftPiglin) event.getEntity()).getHandle();
        Optional<Player> optional = piglin.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        if (optional.isEmpty()) return;
        AbilityRegister.runForAbility(optional.get().getBukkitEntity(), getKey(), () -> {
            int num = random.nextInt(1, 4);
            for (int i = 0; i < num; i++) {
                Collection<ItemStack> items = getBarterResponseItems(piglin);
                throwItemsTowardPlayer(piglin, optional.get(), items);
            }
        });
    }
}
