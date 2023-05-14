package fengliu.notheme.util;

import fengliu.notheme.util.block.BaseBlock;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.item.BaseItem;
import fengliu.notheme.util.item.armor.BaseArmorItem;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
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

    public static void registerItem(IBaseBlock block, Item item){
        Registry.register(Registries.ITEM, block.getId(), item);
    }

    public static void registerItemColor(int color, Item item){
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> color, item);
    }

    public static void registerBlock(Identifier id, Block block){
        Registry.register(Registries.BLOCK, id, (Block) block);
    }

    public static void registerBlock(IBaseBlock block){
        Registry.register(Registries.BLOCK, block.getId(), (Block) block);
    }

    public static BlockEntityType<?> registerBlockEntity(Identifier id, Block block, FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> be){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.create(be, block).build(null));
    }

    public static BlockEntityType<?> registerBlockEntity(IBaseBlock block, FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> be){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, block.getId(), FabricBlockEntityTypeBuilder.create(be, (Block) block).build(null));
    }

    public static void registerItems(BaseItem[] items){
        for (BaseItem item: items){
            RegisterUtil.registerItem(IdUtil.get(item.name), item);
        }
    }

    public static void registerBlocks(BaseBlock[] blocks){
        for (BaseBlock block: blocks){
            RegisterUtil.registerBlock(IdUtil.get(block.getBlockName()), block);
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
