package fengliu.notheme.item;

import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.RegisterUtil;
import fengliu.notheme.util.level.LevelsUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
    private static final ItemGroup INVENTORY_GROUP = FabricItemGroup.builder(IdUtil.get("inventory_group"))
        .icon(() -> new ItemStack(ModBlockItems.CLOTH_BAG_BLOCK_ITEM))
        .build();

    private static final ItemGroup MINI_DEVICE_GROUP = FabricItemGroup.builder(IdUtil.get("mini_device_group"))
        .icon(() -> new ItemStack(ModBlockItems.BEDROCK_BREAKER_BLOCK_ITEM))
        .build();

    private static final ItemGroup HEART_GROUP = FabricItemGroup.builder(IdUtil.get("heart_group"))
        .icon(() -> new ItemStack(ModItems.HEART))
        .build();

    public static void registerItemGroup(){
        ItemGroupEvents.modifyEntriesEvent(INVENTORY_GROUP).register(content -> {
//            LevelsUtil.registerAllBlockItemGroupAll(ModBlockItems.CUSTOMERS_BLOCK_ITEMS, content);
//            LevelsUtil.registerAllItemGroupAll(ModItems.EXPAND_PASSENGERS, content);
            RegisterUtil.registerItemsToItemGroup(content, ModBlockItems.INVEBTORY_GROUP_ITEMS);
            LevelsUtil.registerAllBlockItemGroupAll(ModBlockItems.BENTO_BOX_BLOCK_ITEMS, content);
            LevelsUtil.registerAllBlockItemGroupAll(ModBlockItems.DRINK_HOLDER_BLOCK_ITEMS, content);
        });

        ItemGroupEvents.modifyEntriesEvent(MINI_DEVICE_GROUP).register(content -> {
            RegisterUtil.registerItemsToItemGroup(content, ModBlockItems.MINI_DEVICE_GROUP_ITEMS);
        });

        ItemGroupEvents.modifyEntriesEvent(HEART_GROUP).register(content -> {
            RegisterUtil.registerItemsToItemGroup(content, ModItems.BODY_GROUP_ITEMS);
            LevelsUtil.registerAllItemGroupAll(ModItems.HEART_DROP_DEVICE, content);
            LevelsUtil.registerAllItemGroupAll(ModItems.HEART_ABSORPTION_DEVICE, content);
            RegisterUtil.registerItemsToItemGroup(content, ModBlockItems.BODY_GROUP_ITEMS);

            RegisterUtil.registerItemsToItemGroup(content, ModItems.BODY_GROUP_ARMOR_ITEMS);
        });
    }
}
