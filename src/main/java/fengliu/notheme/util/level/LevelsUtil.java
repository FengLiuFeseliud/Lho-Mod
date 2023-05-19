package fengliu.notheme.util.level;

import fengliu.notheme.util.RegisterUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 等级物品枚举工具
 * <p>
 * 批量 实例/注册/查找 等级物品
 */
public class LevelsUtil {

    /**
     * 实例所有等级方块
     * @param levels 等级方块数组
     * @return 等级方块列表
     */
    public static Map<Block, ILevelBlock> registerBlocks(ILevelBlock[] levels){
        LinkedHashMap<Block, ILevelBlock> blocks = new LinkedHashMap<>();

        for (ILevelBlock blockLevel : levels) {
            blocks.put(RegisterUtil.registerBlock(blockLevel.getId(), blockLevel.getBlock()), blockLevel);
        }

        return blocks;
    }

    /**
     * 实例所有等级方块物品
     * @param blocks 等级方块列表
     * @return 等级方块物品列表
     */
    public static Map<BlockItem, ILevelBlock> registerBlockItems(Map<Block, ILevelBlock> blocks, ItemGroup group){
        LinkedHashMap<BlockItem, ILevelBlock> blockItems = new LinkedHashMap<>();

        for(Map.Entry<Block, ILevelBlock> block: blocks.entrySet()){
            ILevelBlock level = block.getValue();
            blockItems.put(RegisterUtil.registerItem(level.getId(), level.getItem(block.getKey()), group, RegisterUtil.Model.GENERATED), level);
        }

        return blockItems;
    }

    /**
     * 实例所有等级物品
     * @param levels 等级物品数组
     * @return 等级物品列表
     */
    public static Map<Item, ILevelItem> registerItems(ILevelItem[] levels, ItemGroup group){
        LinkedHashMap<Item, ILevelItem> items = new LinkedHashMap<>();
        for(ILevelItem item: levels){
            items.put(RegisterUtil.registerItem(item.getPath(), item.getItem(), group, RegisterUtil.Model.GENERATED), item);
        }

        return items;
    }

    /**
     * 注册所有等级方块
     * @param blocks 等级方块列表
     */
    public static void registerAllBlock(Map<Block, ILevelBlock> blocks){
        blocks.forEach((key, value) -> RegisterUtil.registerBlock(value.getId(), key));
    }

    /**
     * 注册所有等级物品至物品组
     * @param items 等级物品列表
     * @param content 物品组注册上下文
     */
    public static void registerAllItemGroupAll(Map<Item, ILevelItem> items, FabricItemGroupEntries content){
        items.keySet().forEach(content::add);
    }

    /**
     * 注册所有等级方块物品至物品组
     * @param blockItems 等级方块物品列表
     * @param content 物品组注册上下文
     */
    public static void registerAllBlockItemGroupAll(Map<BlockItem, ILevelBlock> blockItems, FabricItemGroupEntries content){
        blockItems.keySet().forEach(content::add);
    }

    /**
     * 注册所有等级方块实体
     * @param blocks 等级方块列表
     * @return 等级方块实体类型列表
     */
    public static Map<ILevelBlock, BlockEntityType<?>> registerAllBlockEntity(Map<Block, ILevelBlock> blocks) {
        LinkedHashMap<ILevelBlock, BlockEntityType<?>> blockEntityTypes = new LinkedHashMap<>();
        blocks.forEach((key, value) -> blockEntityTypes.put(value, RegisterUtil.registerBlockEntity(value.getId(), key, value.getBlockEntityNew())));
        return blockEntityTypes;
    }

    public static BlockItem getBlockItem(Map<BlockItem, ILevelBlock> blockItems, Block block){
        for(BlockItem item: blockItems.keySet()){
            if (item.getBlock().equals(block)){
                return item;
            }
        }
        return null;
    }

    public static ItemStack playerInventoryContainsItemStacks(PlayerEntity player, Map<Item, ILevelItem> items){
        AtomicReference<ItemStack> containStack = new AtomicReference<>();
        for(Item item: items.keySet()){
            player.getInventory().main.forEach(stack -> {
                if (!stack.isOf(item)){
                    return;
                }
                containStack.set(stack);
            });
        }
        return containStack.get();
    }

}
