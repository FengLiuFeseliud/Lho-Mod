package fengliu.lho.item;

import fengliu.lho.item.block.ModBlockItems;
import fengliu.lho.util.IdUtil;
import fengliu.lho.util.RegisterUtil;
import fengliu.lho.util.item.BaseItem;
import fengliu.lho.util.level.LevelsUtil;
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
            RegisterUtil.registerItemsToItemGroup(content, ModItems.BODY_GROUP_ARMOR_ITEMS);
        });
    }
}
