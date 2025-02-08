package net.ramixin.soulstones.client.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.ramixin.soulstones.client.ClientSoulStoneManager;
import net.ramixin.soulstones.screenhandlers.SoulStoneScreenHandler;
import net.ramixin.soulstones.units.DispatcherResponseEntry;

import java.util.Optional;

public class SoulStoneScreen extends HandledScreen<SoulStoneScreenHandler> {

    private DispatcherResponseEntry[] entries = null;
    private int updatesTillNotice = 5;

    private static int center(int pos, int size) {
        return pos - (size / 2);
    }

    private void drawCenteredText(String text, int x, int y, int color, DrawContext context) {
        int width = this.textRenderer.getWidth(text);
        context.drawText(textRenderer, text, center(x, width), center(y, 10), color, false);
    }

    public SoulStoneScreen(SoulStoneScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        consumeEntries();
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        if(entries == null) consumeEntries();
    }

    private void consumeEntries() {
        Optional<DispatcherResponseEntry[]> optional = ClientSoulStoneManager.consumeDispatcherResponseEntries();
        if(optional.isEmpty()) {
            if(updatesTillNotice > 0) updatesTillNotice--;
            return;
        }
        entries = optional.get();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int centerX = width / 2;
        int centerY = height / 2;
        if(entries == null) {
            if(updatesTillNotice == 0) drawCenteredText("Waiting for server...", centerX, centerY, 0xFFFFFF, context);
            return;
        }
        int offset = 20;
        for(DispatcherResponseEntry entry : entries) {
            drawCenteredText(entry.name(), centerX, offset, 0xFFFFFF, context);
            offset += 20;
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        // Stop "Inventory" from rendering
    }
}
