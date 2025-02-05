package net.ramixin.soulstones;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.ramixin.soulstones.blocks.ModBlocks;
import net.ramixin.soulstones.entities.ModEntityTypes;
import net.ramixin.soulstones.items.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SoulStones implements ModInitializer {

    public static final String MOD_ID = "soulstones";

    public static final Logger LOGGER = LoggerFactory.getLogger("SoulStones");

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing SoulStones (1/1)");
        ModBlocks.onInitialize();
        ModEntityTypes.onInitialize();
        ModItems.onInitialize();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
