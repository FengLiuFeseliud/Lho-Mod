package fengliu.notheme.item;

import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.util.IdUtil;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
    public static final ItemGroup INVENTORY_GROUP = FabricItemGroup.builder(IdUtil.get("inventory_group"))
        .icon(() -> new ItemStack(ModBlockItems.VENDING_MACHINE_BLOCK_ITEM))
        .build();

    public static final ItemGroup FOOD_GROUP = FabricItemGroup.builder(IdUtil.get("food_group"))
        .icon(() -> new ItemStack((Item) ModItems.CHOCOLATE_CRUST_ICE_CREAM_BARS.keySet().toArray()[0]))
        .build();

    public static final ItemGroup MINI_DEVICE_GROUP = FabricItemGroup.builder(IdUtil.get("mini_device_group"))
        .icon(() -> new ItemStack(ModBlockItems.BEDROCK_BREAKER_BLOCK_ITEM))
        .build();

    public static final ItemGroup HEART_GROUP = FabricItemGroup.builder(IdUtil.get("heart_group"))
        .icon(() -> new ItemStack(ModItems.HEART))
        .build();
}
