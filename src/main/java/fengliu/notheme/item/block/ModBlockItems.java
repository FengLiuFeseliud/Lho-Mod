package fengliu.notheme.item.block;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.item.mini.device.BedrockBreaker;
import fengliu.notheme.util.RegisterUtil;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import fengliu.notheme.util.level.ILevelBlock;
import fengliu.notheme.util.level.LevelsUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;

import java.util.Map;

public class ModBlockItems {
    public static final Map<BlockItem, ILevelBlock> CUSTOMERS_BLOCK_ITEMS = LevelsUtil.getBlockItems(ModBlocks.CUSTOMERS_BLOCKS);
    public static final BlockItem BLOOD_POOL_BLOCK_ITEM = new BaseBlockItem((IBaseBlock) ModBlocks.BLOOD_POOL_BLOCK);
    public static final BlockItem ABSORPTION_BLOCK_ITEM = new BaseBlockItem(ModBlocks.ABSORPTION_BLOCK);
    public static final BlockItem WITHER_BLOCK_ITEM = new BaseBlockItem(ModBlocks.WITHER_BLOCK);
    public static final BlockItem ANIMAL_BLOCK_ITEM = new BaseBlockItem(ModBlocks.ANIMAL_BLOCK);
    public static final BlockItem FROST_BLOCK_ITEM = new BaseBlockItem(ModBlocks.FROST_BLOCK);
    public static final BlockItem LIFE_BLOCK_ITEM = new BaseBlockItem(ModBlocks.LIFE_BLOCK);
    public static final BlockItem POISON_BLOCK_ITEM = new BaseBlockItem(ModBlocks.POISON_BLOCK);
    public static final BlockItem LONG_FOR_LIFE_BLOCK_ITEM = new BaseBlockItem(ModBlocks.LONG_FOR_LIFE_BLOCK);

    public static final BlockItem[] BODY_GROUP_ITEMS = new BlockItem[]{
        BLOOD_POOL_BLOCK_ITEM,
        ABSORPTION_BLOCK_ITEM,
        WITHER_BLOCK_ITEM,
        ANIMAL_BLOCK_ITEM,
        FROST_BLOCK_ITEM,
        LIFE_BLOCK_ITEM,
        POISON_BLOCK_ITEM,
        LONG_FOR_LIFE_BLOCK_ITEM
    };

    public static final BlockItem CLOTH_BAG_BLOCK_ITEM = ((ItemStackInventoryBlock) ModBlocks.CLOTH_BAG_BLOCK).getItem();
    public static final BlockItem REINFORCED_BAR_BLOCK_ITEM = ((ItemStackInventoryBlock) ModBlocks.REINFORCED_BAR_BLOCK).getItem();
    public static final Map<BlockItem, ILevelBlock> BENTO_BOX_BLOCK_ITEMS = LevelsUtil.getBlockItems(ModBlocks.BENTO_BOX_BLOCKS);
    public static final Map<BlockItem, ILevelBlock> DRINK_HOLDER_BLOCK_ITEMS = LevelsUtil.getBlockItems(ModBlocks.DRINK_HOLDER_BLOCKS);
    public static final BlockItem BOTTLE_BLOCK_ITEM = ((ItemStackInventoryBlock) ModBlocks.BOTTLE_BLOCK).getItem();
    public static final BlockItem EMPTY_BOTTLE_BLOCK_ITEM = ((ItemStackInventoryBlock) ModBlocks.EMPTY_BOTTLE_BLOCK).getItem();
    public static final BlockItem BOTTLE_BOX_BLOCK_ITEM = ((ItemStackInventoryBlock) ModBlocks.BOTTLE_BOX_BLOCK).getItem();
    public static final BlockItem BOTTLE_12_BOX_BLOCK_ITEM = ((ItemStackInventoryBlock) ModBlocks.BOTTLE_12_BOX_BLOCK).getItem();

    public static final BlockItem[] INVEBTORY_GROUP_ITEMS = new BlockItem[]{
        CLOTH_BAG_BLOCK_ITEM,
        REINFORCED_BAR_BLOCK_ITEM,
        BOTTLE_BLOCK_ITEM,
        EMPTY_BOTTLE_BLOCK_ITEM,
        BOTTLE_BOX_BLOCK_ITEM,
        BOTTLE_12_BOX_BLOCK_ITEM
    };

    public static final BlockItem BEDROCK_BREAKER_BLOCK_ITEM = new BedrockBreaker((IBaseBlock) ModBlocks.BEDROCK_BREAKER_BLOCK, new FabricItemSettings().maxCount(1).maxDamage(1));
    public static final BlockItem UPDATE_SKIPPING_BLOCK_ITEM = new BaseBlockItem((IBaseBlock) ModBlocks.UPDATE_SKIPPING_BLOCK, new FabricItemSettings().maxCount(1));

    public static final BlockItem[] MINI_DEVICE_GROUP_ITEMS = new BlockItem[]{
        BEDROCK_BREAKER_BLOCK_ITEM,
        UPDATE_SKIPPING_BLOCK_ITEM
    };

    public static void registerAllBlockItem(){
        LevelsUtil.registerAllBlockItem(CUSTOMERS_BLOCK_ITEMS);

        RegisterUtil.registerItem((IBaseBlock) ModBlocks.BLOOD_POOL_BLOCK, BLOOD_POOL_BLOCK_ITEM);
        RegisterUtil.registerItem((IBaseBlock) ModBlocks.CLOTH_BAG_BLOCK, CLOTH_BAG_BLOCK_ITEM);
        RegisterUtil.registerItem((IBaseBlock) ModBlocks.REINFORCED_BAR_BLOCK, REINFORCED_BAR_BLOCK_ITEM);
        RegisterUtil.registerItem((IBaseBlock) ModBlocks.BOTTLE_BOX_BLOCK, BOTTLE_BOX_BLOCK_ITEM);
        RegisterUtil.registerItem((IBaseBlock) ModBlocks.EMPTY_BOTTLE_BLOCK, EMPTY_BOTTLE_BLOCK_ITEM);
        RegisterUtil.registerItem((IBaseBlock) ModBlocks.BOTTLE_12_BOX_BLOCK, BOTTLE_12_BOX_BLOCK_ITEM);
        RegisterUtil.registerItem((IBaseBlock) ModBlocks.BOTTLE_BLOCK, BOTTLE_BLOCK_ITEM);
        LevelsUtil.registerAllBlockItem(BENTO_BOX_BLOCK_ITEMS);
        LevelsUtil.registerAllBlockItem(DRINK_HOLDER_BLOCK_ITEMS);

        RegisterUtil.registerItem(ModBlocks.ABSORPTION_BLOCK, ABSORPTION_BLOCK_ITEM);
        RegisterUtil.registerItem(ModBlocks.WITHER_BLOCK, WITHER_BLOCK_ITEM);
        RegisterUtil.registerItem(ModBlocks.LIFE_BLOCK, LIFE_BLOCK_ITEM);
        RegisterUtil.registerItem(ModBlocks.FROST_BLOCK, FROST_BLOCK_ITEM);
        RegisterUtil.registerItem(ModBlocks.ANIMAL_BLOCK, ANIMAL_BLOCK_ITEM);
        RegisterUtil.registerItem(ModBlocks.POISON_BLOCK, POISON_BLOCK_ITEM);
        RegisterUtil.registerItem(ModBlocks.LONG_FOR_LIFE_BLOCK, LONG_FOR_LIFE_BLOCK_ITEM);

        RegisterUtil.registerItem((IBaseBlock) ModBlocks.BEDROCK_BREAKER_BLOCK, BEDROCK_BREAKER_BLOCK_ITEM);
        RegisterUtil.registerItem((IBaseBlock) ModBlocks.UPDATE_SKIPPING_BLOCK, UPDATE_SKIPPING_BLOCK_ITEM);
    }
}
