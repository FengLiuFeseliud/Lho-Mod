package fengliu.notheme.block;

import fengliu.notheme.util.level.ILevelBlock;
import fengliu.notheme.util.level.LevelsUtil;
import net.minecraft.block.Block;

import java.util.Map;

public class ModBlocks {
    public static final Map<Block, ILevelBlock> CUSTOMERS_BLOCKS = LevelsUtil.getBlocks(CustomersBlock.CustomersBlockLevels.values());

    public static void registerAllBlock(){
        LevelsUtil.registerAllBlock(CUSTOMERS_BLOCKS);
    }
}
