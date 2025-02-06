package net.ramixin.soulstones.client.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.ramixin.soulstones.SoulStones;
import net.ramixin.soulstones.client.SoulStonesClient;
import net.ramixin.soulstones.screenhandlers.SoulStoneScreenHandler;
import net.ramixin.soulstones.units.DispatcherResponseEntry;

import java.util.Arrays;
import java.util.Optional;

public class SoulStoneScreen extends HandledScreen<SoulStoneScreenHandler> {

    private DispatcherResponseEntry[] entries = null;

    public SoulStoneScreen(SoulStoneScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        SoulStones.LOGGER.info("SoulStoneScreen initialized");
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        if(entries == null) {
            Optional<DispatcherResponseEntry[]> optional = SoulStonesClient.consumeDispatcherResponseEntries();
            if(optional.isEmpty()) return;
            entries = optional.get();
            SoulStones.LOGGER.info("SoulStoneScreen consumed entries: {}", Arrays.toString(entries));
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    }
}
