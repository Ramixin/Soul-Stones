package net.ramixin.soulstones.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.ramixin.soulstones.SoulStones;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ModBlocks {

    public static final Block SOUL_STONE = register("soul_stone", SoulStoneBlock::new, AbstractBlock.Settings.copy(Blocks.DEEPSLATE));

    private static <T extends Block> T register(String id, Function<AbstractBlock.Settings, T> blockFunction, AbstractBlock.Settings settings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, SoulStones.id(id));
        T block = blockFunction.apply(settings.registryKey(key));
        registerItem(block, id, BlockItem::new, new Item.Settings());
        return Registry.register(Registries.BLOCK, key, block);
    }

    private static void registerItem(Block block, String id, BiFunction<Block, Item.Settings, BlockItem> itemFunction, Item.Settings settings) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, SoulStones.id(id));
        BlockItem item = itemFunction.apply(block, settings.registryKey(key).useBlockPrefixedTranslationKey());
        Registry.register(Registries.ITEM, key, item);
    }

    public static void onInitialize() {}

}
