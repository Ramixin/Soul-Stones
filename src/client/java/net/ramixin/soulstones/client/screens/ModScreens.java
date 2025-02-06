package net.ramixin.soulstones.client.screens;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.ramixin.soulstones.screenhandlers.ModScreenHandlers;

public class ModScreens {

    public static void onInitialize() {
        HandledScreens.register(ModScreenHandlers.SOUL_STONE_HANDLER_TYPE, SoulStoneScreen::new);
    }

}
