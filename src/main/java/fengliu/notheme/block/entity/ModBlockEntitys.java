package fengliu.notheme.block.entity;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.util.level.ILevelBlock;
import fengliu.notheme.util.level.LevelsUtil;
import net.minecraft.block.entity.BlockEntityType;

import java.util.Map;

public class ModBlockEntitys {
    public static Map<ILevelBlock, BlockEntityType<?>> CustomersBlockEntityTypes;

    public static void registerAllBlockEntity(){
        CustomersBlockEntityTypes = LevelsUtil.registerAllBlockEntity(ModBlocks.CUSTOMERS_BLOCKS);
    }
}
