package fengliu.lho.item.block;

import fengliu.lho.block.ModBlocks;
import fengliu.lho.util.level.ILevelBlock;
import fengliu.lho.util.level.LevelsUtil;
import net.minecraft.item.BlockItem;

import java.util.Map;

public class ModBlockItems {
    public static final Map<BlockItem, ILevelBlock> CUSTOMERS_BLOCK_ITEMS = LevelsUtil.getBlockItems(ModBlocks.CUSTOMERS_BLOCKS);

    public static void registerAllBlockItem(){
        LevelsUtil.registerAllBlockItem(CUSTOMERS_BLOCK_ITEMS);
    }
}
