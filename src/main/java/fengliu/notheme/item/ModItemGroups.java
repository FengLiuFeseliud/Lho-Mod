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
    private static final ItemGroup BLOCK_GROUP = FabricItemGroup.builder(IdUtil.get("block_group"))
        .icon(() -> new ItemStack((Item) ModBlockItems.CUSTOMERS_BLOCK_ITEMS.keySet().toArray()[0]))
        .build();

    private static final ItemGroup BODY_GROUP = FabricItemGroup.builder(IdUtil.get("body_group"))
        .icon(() -> new ItemStack(ModItems.BLOOD_NEEDLE))
        .build();

    public static void registerItemGroup(){
        ItemGroupEvents.modifyEntriesEvent(BLOCK_GROUP).register(content -> {
            LevelsUtil.registerAllBlockItemGroupAll(ModBlockItems.CUSTOMERS_BLOCK_ITEMS, content);
            LevelsUtil.registerAllItemGroupAll(ModItems.EXPAND_PASSENGERS, content);
        });

        ItemGroupEvents.modifyEntriesEvent(BODY_GROUP).register(content -> {
            RegisterUtil.registerItemsToItemGroup(content, ModItems.BODY_GROUP_ITEMS);
            LevelsUtil.registerAllItemGroupAll(ModItems.HEART_DROP_DEVICE, content);
            LevelsUtil.registerAllItemGroupAll(ModItems.HEART_ABSORPTION_DEVICE, content);

            RegisterUtil.registerItemsToItemGroup(content, ModItems.BODY_GROUP_ARMOR_ITEMS);
        });
    }
}
