package fengliu.notheme.item.block;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.item.mini.device.BedrockBreaker;
import fengliu.notheme.util.RegisterUtil;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import fengliu.notheme.util.level.ILevelBlock;
import fengliu.notheme.util.level.LevelsUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;

import java.util.Map;

import static fengliu.notheme.item.ModItemGroups.*;

public class ModBlockItems {
    public static final BlockItem BLOOD_POOL_BLOCK_ITEM = register(ModBlocks.BLOOD_POOL_BLOCK, HEART_GROUP);
    public static final BlockItem ABSORPTION_BLOCK_ITEM = register(ModBlocks.ABSORPTION_BLOCK, HEART_GROUP);
    public static final BlockItem WITHER_BLOCK_ITEM = register(ModBlocks.WITHER_BLOCK, HEART_GROUP);
    public static final BlockItem ANIMAL_BLOCK_ITEM = register(ModBlocks.ANIMAL_BLOCK, HEART_GROUP);
    public static final BlockItem FROST_BLOCK_ITEM = register(ModBlocks.FROST_BLOCK, HEART_GROUP);
    public static final BlockItem LIFE_BLOCK_ITEM = register(ModBlocks.LIFE_BLOCK, HEART_GROUP);
    public static final BlockItem POISON_BLOCK_ITEM = register(ModBlocks.POISON_BLOCK, HEART_GROUP);
    public static final BlockItem LONG_FOR_LIFE_BLOCK_ITEM = register(ModBlocks.LONG_FOR_LIFE_BLOCK, HEART_GROUP);

    public static final BlockItem CLOTH_BAG_BLOCK_ITEM = register(ModBlocks.CLOTH_BAG_BLOCK, RegisterUtil.Model.GENERATED, INVENTORY_GROUP);
    public static final BlockItem REINFORCED_BAR_BLOCK_ITEM = register(ModBlocks.REINFORCED_BAR_BLOCK, INVENTORY_GROUP);
    public static final Map<BlockItem, ILevelBlock> BENTO_BOX_BLOCK_ITEMS = LevelsUtil.registerBlockItems(ModBlocks.BENTO_BOX_BLOCKS, INVENTORY_GROUP);
    public static final Map<BlockItem, ILevelBlock> DRINK_HOLDER_BLOCK_ITEMS = LevelsUtil.registerBlockItems(ModBlocks.DRINK_HOLDER_BLOCKS, RegisterUtil.Model.GENERATED, INVENTORY_GROUP);
    public static final BlockItem BOTTLE_BLOCK_ITEM = register(ModBlocks.BOTTLE_BLOCK, INVENTORY_GROUP);
    public static final BlockItem EMPTY_BOTTLE_BLOCK_ITEM = register(ModBlocks.EMPTY_BOTTLE_BLOCK, INVENTORY_GROUP);
    public static final BlockItem BOTTLE_BOX_BLOCK_ITEM = register(ModBlocks.BOTTLE_BOX_BLOCK, INVENTORY_GROUP);
    public static final BlockItem BOTTLE_12_BOX_BLOCK_ITEM = register(ModBlocks.BOTTLE_12_BOX_BLOCK, INVENTORY_GROUP);
    public static final BlockItem DISPENSING_MACHINE_BLOCK_ITEM = register(ModBlocks.DISPENSING_MACHINE_BLOCK, INVENTORY_GROUP);
    public static final BlockItem VENDING_MACHINE_BLOCK_ITEM = register(ModBlocks.VENDING_MACHINE_BLOCK, INVENTORY_GROUP);

    public static final BlockItem BEDROCK_BREAKER_BLOCK_ITEM = register(new BedrockBreaker((IBaseBlock) ModBlocks.BEDROCK_BREAKER_BLOCK, new FabricItemSettings().maxCount(1).maxDamage(1)), ModBlocks.BEDROCK_BREAKER_BLOCK, MINI_DEVICE_GROUP);
    public static final BlockItem UPDATE_SKIPPING_BLOCK_ITEM = register(new BaseBlockItem((IBaseBlock) ModBlocks.UPDATE_SKIPPING_BLOCK, new FabricItemSettings().maxCount(1)), ModBlocks.UPDATE_SKIPPING_BLOCK, MINI_DEVICE_GROUP);
    public static final BlockItem CRAFTING_TABLE_SLAB_BLOCK_ITEM = register(ModBlocks.CRAFTING_TABLE_SLAB_BLOCK, MINI_DEVICE_GROUP);

    public static <BI extends BlockItem, B extends Block> BI register(BI item, B block, ItemGroup group){
        return RegisterUtil.registerItem(((IBaseBlock) block).getId(), item, group, null);
    }

    public static <BI extends BlockItem, B extends Block> BI register(BI item, B block, RegisterUtil.Model model, ItemGroup group){
        return RegisterUtil.registerItem(((IBaseBlock) block).getId(), item, group, model);
    }

    public static <B extends Block> BlockItem register(B block, ItemGroup group){
        if (block instanceof ItemStackInventoryBlock itemStackInventoryBlock){
            return ModBlockItems.register(itemStackInventoryBlock.getItem(), block, group);
        }
        return ModBlockItems.register(new BaseBlockItem((IBaseBlock) block), block, group);
    }

    public static <B extends Block> BlockItem register(B block, RegisterUtil.Model model, ItemGroup group){
        if (block instanceof ItemStackInventoryBlock itemStackInventoryBlock){
            return ModBlockItems.register(itemStackInventoryBlock.getItem(), block, model, group);
        }
        return ModBlockItems.register(new BaseBlockItem((IBaseBlock) block), block, model, group);
    }

    public static void registerAllBlockItem(){

    }
}
