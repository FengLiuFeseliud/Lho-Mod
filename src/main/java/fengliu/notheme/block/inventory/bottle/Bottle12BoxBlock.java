package fengliu.notheme.block.inventory.bottle;

import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.item.inventory.block.bottle.BottleBox;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.IntProperty;

public class Bottle12BoxBlock extends BottleBoxBlock {
    public Bottle12BoxBlock(Settings settings, int size) {
        super(settings, size);
    }

    @Override
    public BlockItem getItem() {
        return new BottleBox(this, new FabricItemSettings().maxCount(1).maxDamageIfAbsent(12));
    }

    @Override
    public ItemStack getItemStack() {
        return ModBlockItems.BOTTLE_12_BOX_BLOCK_ITEM.getDefaultStack();
    }

    @Override
    public IntProperty getInventoryProperty() {
        return IntProperty.of("inventory", 0, 12);
    }

    @Override
    public String getBlockName() {
        return "bottle_12_box";
    }
}
