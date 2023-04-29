package fengliu.notheme.screen.handler;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.screen.handler.BaseScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class BloodPoolBlockScreenHandler extends BaseScreenHandler {
    protected BloodPoolBlockScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(4));
    }

    public BloodPoolBlockScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.BLOOP_POOL_BLOCK_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;

        this.init(playerInventory);

        this.addSlot(new Slot(this.inventory, 1, 118, 12){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(ModItems.CUING_AGENT);
            }
        });

        this.addSlot(new Slot(this.inventory, 2, 80, 61){
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
    }

    @Override
    public int[] getSize() {
        return new int[]{3, 4};
    }

    public int getLevel(){
        return this.propertyDelegate.get(0);
    }

    public int getHeartType(){
        return this.propertyDelegate.get(1);
    }

    public int getCokyTick(){
        return this.propertyDelegate.get(2);
    }

    public int getFillTick(){
        return this.propertyDelegate.get(3);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }
}
