package fengliu.notheme.block.inventory.bottle;

import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.item.inventory.block.bottle.EmptyBottle;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class EmptyBottleBlock extends BottleBlock {
    public EmptyBottleBlock(Settings settings, int size) {
        super(settings, size);
    }

    @Override
    public boolean canPlaced(ItemStack stack) {
        return stack.isOf(ModBlockItems.EMPTY_BOTTLE_BLOCK_ITEM) || stack.isEmpty();
    }

    @Override
    public BlockItem getItem() {
        return new EmptyBottle(this, new FabricItemSettings().maxCount(1).maxDamageIfAbsent(this.getSize()));
    }

    @Override
    public ItemStack getItemStack() {
        return ModBlockItems.EMPTY_BOTTLE_BLOCK_ITEM.getDefaultStack();
    }

    @Override
    public String getBlockName() {
        return "empty_bottle";
    }
}
