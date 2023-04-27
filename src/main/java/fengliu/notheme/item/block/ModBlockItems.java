package fengliu.notheme.item.block;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.util.level.ILevelBlock;
import fengliu.notheme.util.level.LevelsUtil;
import net.minecraft.item.BlockItem;

import java.util.Map;

public class ModBlockItems {
    public static final Map<BlockItem, ILevelBlock> CUSTOMERS_BLOCK_ITEMS = LevelsUtil.getBlockItems(ModBlocks.CUSTOMERS_BLOCKS);

    public static void registerAllBlockItem(){
        LevelsUtil.registerAllBlockItem(CUSTOMERS_BLOCK_ITEMS);
    }
}
