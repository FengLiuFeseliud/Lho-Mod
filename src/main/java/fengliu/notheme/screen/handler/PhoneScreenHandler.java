package fengliu.notheme.screen.handler;

import fengliu.notheme.util.screen.handler.BaseScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class PhoneScreenHandler extends BaseScreenHandler {
    private ItemStack itemStack;

    protected PhoneScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, null, playerInventory);
    }

    public PhoneScreenHandler(int syncId, ItemStack itemStack, PlayerInventory playerInventory) {
        super(ModScreenHandlers.PHONE_SCREEN_HANDLER, syncId);
        this.inventory = playerInventory;

        this.playerSlot(playerInventory);
        this.itemStack = itemStack;
    }

    @Override
    public int[] getSize() {
        return new int[0];
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }
}
