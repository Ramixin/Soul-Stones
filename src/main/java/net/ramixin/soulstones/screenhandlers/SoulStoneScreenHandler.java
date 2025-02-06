package net.ramixin.soulstones.screenhandlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.ramixin.soulstones.SoulStoneManager;

public class SoulStoneScreenHandler extends ScreenHandler {

    public SoulStoneScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.SOUL_STONE_HANDLER_TYPE, syncId);
        if(playerInventory.player instanceof ServerPlayerEntity serverPlayer)
            SoulStoneManager.gatherAndSendDispatchLocations(serverPlayer, playerInventory.player.getWorld());
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
