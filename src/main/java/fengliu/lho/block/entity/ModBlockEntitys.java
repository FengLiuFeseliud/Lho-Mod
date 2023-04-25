package fengliu.lho.block.entity;

import fengliu.lho.block.ModBlocks;
import fengliu.lho.util.level.ILevelBlock;
import fengliu.lho.util.level.LevelsUtil;
import net.minecraft.block.entity.BlockEntityType;

import java.util.Map;

public class ModBlockEntitys {
    public static Map<ILevelBlock, BlockEntityType<?>> CustomersBlockEntityTypes;

    public static void registerAllBlockEntity(){
        CustomersBlockEntityTypes = LevelsUtil.registerAllBlockEntity(ModBlocks.CUSTOMERS_BLOCKS);
    }
}
