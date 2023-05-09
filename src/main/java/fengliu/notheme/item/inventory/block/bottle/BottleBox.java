package fengliu.notheme.item.inventory.block.bottle;

import fengliu.notheme.item.block.BaseBlockItem;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import fengliu.notheme.util.item.IItemStackInventoryBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class BottleBox extends BaseBlockItem implements IItemStackInventoryBlockItem {
    public BottleBox(IBaseBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();

        Text cappingText;
        if (nbt.contains("capping")){
            if (nbt.getBoolean("capping")){
                cappingText = Text.translatable(IdUtil.getItemInfo("capping", 1));
            } else {
                cappingText = Text.translatable(IdUtil.getItemInfo("capping", 2));
            }
        } else {
            cappingText = Text.translatable(IdUtil.getItemInfo("capping", 2));
        }

        ItemStack cappingStack =  this.getUseItemStack(nbt);
        if (cappingStack.isEmpty()){
            return ((MutableText) super.getName(stack)).append(cappingText);
        }

        return ((MutableText) super.getName(stack)).append(cappingText).append(Text.translatable(IdUtil.getItemInfo("reinforced_bag", 1), cappingStack.getName()));
    }

    @Override
    public ItemStackInventoryBlock getInventoryBlock() {
        return (ItemStackInventoryBlock) this.getBlock();
    }
}
