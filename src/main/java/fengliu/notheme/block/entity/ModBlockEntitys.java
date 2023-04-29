package fengliu.notheme.block.entity;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.RegisterUtil;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.level.ILevelBlock;
import fengliu.notheme.util.level.LevelsUtil;
import net.minecraft.block.entity.BlockEntityType;

import java.util.Map;

public class ModBlockEntitys {
    public static Map<ILevelBlock, BlockEntityType<?>> CustomersBlockEntityTypes;
    public static BlockEntityType<?> BLOOD_POOL_BLOCK_ENTITY;

    public static void registerAllBlockEntity(){
        CustomersBlockEntityTypes = LevelsUtil.registerAllBlockEntity(ModBlocks.CUSTOMERS_BLOCKS);

        BLOOD_POOL_BLOCK_ENTITY = RegisterUtil.registerBlockEntity((IBaseBlock) ModBlocks.BLOOD_POOL_BLOCK, BloodPoolBlockEntity::new);
    }
}
