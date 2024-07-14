package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.OriginSwapper;
import com.starshootercity.abilities.AbilityRegister;
import com.starshootercity.abilities.VisibleAbility;
import com.starshootercity.originsmonsters.OriginsMonsters;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Player;
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

    private static void throwItemsTowardPos(Piglin piglin, Collection<ItemStack> items, Location pos) {
        for (ItemStack itemStack : items) {
            OriginsMonsters.getNMSInvoker().throwItem(piglin, itemStack, pos.add(0.0D, 1.0D, 0.0D));
        }
    }

    private static void throwItemsTowardPlayer(Piglin piglin, Player player, Collection<ItemStack> items) {
        throwItemsTowardPos(piglin, items, player.getLocation());
    }

    private static Collection<ItemStack> getBarterResponseItems(Piglin piglin) {
        return LootTables.PIGLIN_BARTERING.getLootTable().populateLoot(random, new LootContext.Builder(piglin.getLocation()).lootedEntity(piglin).build());
    }

    @EventHandler
    public void onPiglinBarter(PiglinBarterEvent event) {
        Player player = OriginsMonsters.getNMSInvoker().getNearestVisiblePlayer(event.getEntity());
        //Optional<Player> optional = piglin.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
        if (player == null) return;
        AbilityRegister.runForAbility(player, getKey(), () -> {
            int num = random.nextInt(1, 4);
            for (int i = 0; i < num; i++) {
                Collection<ItemStack> items = getBarterResponseItems(event.getEntity());
                throwItemsTowardPlayer(event.getEntity(), player, items);
            }
        });
    }
}
