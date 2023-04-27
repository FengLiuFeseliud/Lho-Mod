package fengliu.notheme.screen.handler;

import fengliu.notheme.util.screen.handler.BaseScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.slot.Slot;

public class CustomersBlockScreenHandler extends BaseScreenHandler {
    protected CustomersBlockScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1), new ArrayPropertyDelegate(3));
    }

    public CustomersBlockScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.CUSTOMERS_BLOCK_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;

        this.init(playerInventory);
        this.addSlot(new Slot(inventory, 0, 69, 49){
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof BlockItem;
            }
        });
    }

    @Override
    public int[] getSize() {
        return new int[]{1, 3};
    }

    public int getWidth(){
        return this.propertyDelegate.get(0);
    }

    public int getHeight(){
        return this.propertyDelegate.get(1);
    }

    public boolean isShowPos(){
        return this.propertyDelegate.get(2) == 0;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }
}
