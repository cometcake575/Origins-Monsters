package com.starshootercity.originsmonsters.abilities;

import com.starshootercity.abilities.types.VisibleAbility;
import com.starshootercity.originsmonsters.OriginsMonsters;
import com.starshootercity.util.config.ConfigManager;
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
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class SuperBartering implements VisibleAbility, Listener {
    @Override
    public String description() {
        return "You're brilliant at bartering after a lifetime of experience, every time you barter you get between 2 and 5 times as many valuables.";
    }

    @Override
    public String title() {
        return "Bartering Master";
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
        Player p = OriginsMonsters.getNMSInvoker().getNearestVisiblePlayer(event.getEntity());
        if (p == null) return;
        runForAbility(p, player -> {
            int num = random.nextInt(
                    getConfigOption(OriginsMonsters.getInstance(), minimumAmount, ConfigManager.SettingType.INTEGER),
                    getConfigOption(OriginsMonsters.getInstance(), maximumAmount, ConfigManager.SettingType.INTEGER) + 1);
            for (int i = 0; i < num; i++) {
                Collection<ItemStack> items = getBarterResponseItems(event.getEntity());
                throwItemsTowardPlayer(event.getEntity(), player, items);
            }
        });
    }

    private final String minimumAmount = "minimum_amount";
    private final String maximumAmount = "maximum_amount";

    @Override
    public void initialize(JavaPlugin plugin) {
        registerConfigOption(OriginsMonsters.getInstance(), minimumAmount, Collections.singletonList("The minimum number of extra barters"), ConfigManager.SettingType.INTEGER, 1);
        registerConfigOption(OriginsMonsters.getInstance(), maximumAmount, Collections.singletonList("The maximum number of extra barters"), ConfigManager.SettingType.INTEGER, 3);
    }
}
