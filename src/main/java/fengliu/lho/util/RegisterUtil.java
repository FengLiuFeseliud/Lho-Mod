package fengliu.lho.util;

import fengliu.lho.util.item.BaseItem;
import fengliu.lho.util.item.armor.BaseArmorItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RegisterUtil {

    public static void registerItem(Identifier id, Item item){
        Registry.register(Registries.ITEM, id, item);
    }

    public static void registerBlock(Identifier id, Block block){
        Registry.register(Registries.BLOCK, id, block);
    }

    public static BlockEntityType<?> registerBlockEntity(Identifier id, Block block, FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> be){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.create(be, block).build(null));
    }

    public static void registerItems(BaseItem[] items){
        for (BaseItem item: items){
            RegisterUtil.registerItem(IdUtil.get(item.name), item);
        }
    }

    public static void registerArmorItems(BaseArmorItem[] items){
        for (BaseArmorItem item: items){
            RegisterUtil.registerItem(IdUtil.get(item.name), item);
        }
    }

    public static void registerItemsToItemGroup(FabricItemGroupEntries content, Item[] items){
        for (Item item: items){
            content.add(new ItemStack(item));
        }
    }

}
