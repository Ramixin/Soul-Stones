package net.ramixin.soulstones;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public class ModBlocks {

    public static final Block SOUL_STONE = register("soul_stone", Block::new, AbstractBlock.Settings.copy(Blocks.DEEPSLATE));

    private static <T extends Block> T register(String id, Function<AbstractBlock.Settings, T> blockFunction, AbstractBlock.Settings settings) {
        RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, SoulStones.id(id));
        T block = blockFunction.apply(settings.registryKey(key));
        return Registry.register(Registries.BLOCK, key, block);
    }

    public static void onInitialize() {}

}
