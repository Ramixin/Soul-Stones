package net.ramixin.soulstones.client;

import net.minecraft.util.math.BlockPos;

import java.util.Objects;
import java.util.UUID;

public final class ActiveTransferEntry {

    private final UUID figureUUID;
    private final BlockPos pos;
    private int transferTicks;

    public ActiveTransferEntry(UUID figureUUID, BlockPos pos, int transferTicks) {
        this.figureUUID = figureUUID;
        this.pos = pos;
        this.transferTicks = transferTicks;
    }

    public UUID figureUUID() {
        return figureUUID;
    }

    public BlockPos pos() {
        return pos;
    }

    public int transferTicks() {
        return transferTicks;
    }

    public void tick() {
        if(transferTicks < 100) transferTicks++;
    }

    @Override
    public int hashCode() {
        return Objects.hash(figureUUID, pos, transferTicks);
    }

    @Override
    public String toString() {
        return "ActiveTransferEntry[" +
                "figureUUID=" + figureUUID + ", " +
                "pos=" + pos + ", " +
                "transferTicks=" + transferTicks + ']';
    }

}
