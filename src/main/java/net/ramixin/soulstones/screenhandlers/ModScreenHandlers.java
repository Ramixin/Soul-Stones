package net.ramixin.soulstones.screenhandlers;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.ramixin.soulstones.SoulStones;

public class ModScreenHandlers {

    public static final ScreenHandlerType<SoulStoneScreenHandler> SOUL_STONE_HANDLER_TYPE = new ScreenHandlerType<>(SoulStoneScreenHandler::new, FeatureFlags.VANILLA_FEATURES);

    public static void onInitialize() {
        Registry.register(Registries.SCREEN_HANDLER, SoulStones.id("soul_stone"), SOUL_STONE_HANDLER_TYPE);
    }
}
