package fengliu.notheme.block.entity;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.util.RegisterUtil;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.level.ILevelBlock;
import fengliu.notheme.util.level.LevelsUtil;
import net.minecraft.block.entity.BlockEntityType;

import java.util.Map;

public class ModBlockEntitys {
    public static Map<ILevelBlock, BlockEntityType<?>> CustomersBlockEntityTypes;
    public static BlockEntityType<?> BLOOD_POOL_BLOCK_ENTITY;
    public static BlockEntityType<?> CLOTH_BAG_BLOCK_ENTITY;
    public static BlockEntityType<?> REINFORCED_BAR_BLOCK_ENTITY;
    public static Map<ILevelBlock, BlockEntityType<?>> BENTO_BOX_BLOCK_ENTITYS;
    public static Map<ILevelBlock, BlockEntityType<?>> DRINK_HOLDER_BLOCK_ENTITYS;
    public static BlockEntityType<?> BEDROCK_BREAKER_BLOCK_ENTITY;

    public static void registerAllBlockEntity(){
        CustomersBlockEntityTypes = LevelsUtil.registerAllBlockEntity(ModBlocks.CUSTOMERS_BLOCKS);

        BLOOD_POOL_BLOCK_ENTITY = RegisterUtil.registerBlockEntity((IBaseBlock) ModBlocks.BLOOD_POOL_BLOCK, BloodPoolBlockEntity::new);
        CLOTH_BAG_BLOCK_ENTITY = RegisterUtil.registerBlockEntity((IBaseBlock) ModBlocks.CLOTH_BAG_BLOCK, ClothBagBlockEntity::new);
        REINFORCED_BAR_BLOCK_ENTITY = RegisterUtil.registerBlockEntity((IBaseBlock) ModBlocks.REINFORCED_BAR_BLOCK, ClothBagBlockEntity::new);
        BENTO_BOX_BLOCK_ENTITYS = LevelsUtil.registerAllBlockEntity(ModBlocks.BENTO_BOX_BLOCKS);
        DRINK_HOLDER_BLOCK_ENTITYS = LevelsUtil.registerAllBlockEntity(ModBlocks.DRINK_HOLDER_BLOCKS);
        BEDROCK_BREAKER_BLOCK_ENTITY = RegisterUtil.registerBlockEntity((IBaseBlock) ModBlocks.BEDROCK_BREAKER_BLOCK, BedrockBreakerBlockEntity::new);
    }
}
