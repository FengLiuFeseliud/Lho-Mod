package fengliu.notheme.util.level;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.RegisterUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.LinkedHashMap;
import java.util.List;
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
    public static Map<Block, ILevelBlock> getBlocks(ILevelBlock[] levels){
        LinkedHashMap<Block, ILevelBlock> blocks = new LinkedHashMap<>();

        for (ILevelBlock blockLevel : levels) {
            blocks.put(blockLevel.getBlock(), blockLevel);
        }

        return blocks;
    }

    /**
     * 实例所有等级方块物品
     * @param blocks 等级方块列表
     * @return 等级方块物品列表
     */
    public static Map<BlockItem, ILevelBlock> getBlockItems(Map<Block, ILevelBlock> blocks){
        LinkedHashMap<BlockItem, ILevelBlock> blockItems = new LinkedHashMap<>();

        for(Map.Entry<Block, ILevelBlock> block: blocks.entrySet()){
            ILevelBlock level = block.getValue();
            blockItems.put(level.getItem(block.getKey()), level);
        }

        return blockItems;
    }

    /**
     * 实例所有等级物品
     * @param levels 等级物品数组
     * @return 等级物品列表
     */
    public static Map<Item, ILevelItem> getItems(ILevelItem[] levels){
        LinkedHashMap<Item, ILevelItem> items = new LinkedHashMap<>();
        for(ILevelItem item: levels){
            items.put(item.getItem(), item);
        }

        return items;
    }

    /**
     * 注册所有等级物品
     * @param items 等级物品列表
     */
    public static void registerAllItem(Map<Item, ILevelItem> items){
        items.forEach((key, value) -> RegisterUtil.registerItem(value.getId(), key));
    }

    /**
     * 注册所有等级方块
     * @param blocks 等级方块列表
     */
    public static void registerAllBlock(Map<Block, ILevelBlock> blocks){
        blocks.forEach((key, value) -> RegisterUtil.registerBlock(value.getId(), key));
    }

    /**
     * 注册所有等级方块物品
     * @param blockItems 等级方块物品列表
     */
    public static void registerAllBlockItem(Map<BlockItem, ILevelBlock> blockItems){
        blockItems.forEach((key, value) -> RegisterUtil.registerItem(value.getId(), key));
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

    public static boolean canUpgrade(Block block, PlayerEntity player, Hand hand, World world){
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isEmpty()){
            return false;
        }

        ILevelItem level = ModItems.EXPAND_PASSENGERS.get(stack.getItem());
        if (level == null){
            return false;
        }

        if (!(level instanceof ICanUpgradeBlock)){
            return false;
        }

        return ((ICanUpgradeBlock) level).canUpgrade(block);
    }

    public static boolean upgrade(Block block, Map<Block, ILevelBlock> blocks, BlockPos pos, World world){
        List<Block> blockList = blocks.keySet().stream().toList();

        int index = blockList.indexOf(block) + 1;
        if (index == blockList.size()){
            return false;
        }

        world.setBlockState(pos, blockList.get(index).getDefaultState());
        return true;
    }

}
