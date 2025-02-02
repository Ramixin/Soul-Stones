package net.ramixin.soulstones.items;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.ramixin.soulstones.SoulStones;

import java.util.function.Function;

public class ModItems {

    public static Item SOUL_STATUE = register("soul_statue", SoulStatueItem::new, new Item.Settings());

    private static <T extends Item> T register(String id, Function<Item.Settings, T> itemFunction, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, SoulStones.id(id));
        T item = itemFunction.apply(settings.registryKey(key));
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void onInitialize() {

    }
}
