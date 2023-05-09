package fengliu.notheme.item.inventory.block.bottle;

import fengliu.notheme.item.block.BaseBlockItem;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import fengliu.notheme.util.item.IItemStackInventoryBlockItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

public class EmptyBottle extends BaseBlockItem implements IItemStackInventoryBlockItem {

    public EmptyBottle(IBaseBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ItemStack slotStack = slot.getStack();
        if (slotStack.isEmpty()){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        Item item = slotStack.getItem();
        if (item instanceof ThrowablePotionItem){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        if (!(item instanceof PotionItem)){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        ItemStack bottleStack = ModBlockItems.BOTTLE_BLOCK_ITEM.getDefaultStack();
        bottleStack.getOrCreateNbt().put("potion", slotStack.writeNbt(new NbtCompound()));

        stack.decrement(1);
        slotStack.decrement(1);

        slot.setStack(bottleStack);
        return super.onStackClicked(stack, slot, clickType, player);
    }

    @Override
    public ItemStackInventoryBlock getInventoryBlock() {
        return (ItemStackInventoryBlock) this.getBlock();
    }
}
