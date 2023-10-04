package fengliu.notheme.util;

import fengliu.notheme.util.block.BaseBlock;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.item.BaseItem;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterUtil {
    public enum Model{
        GENERATED;
    }

    public static final Map<Item, Model> ITEM_MODEL = new HashMap<>();
    public static final List<List<BaseItem>> COLORS_ITEM_LIST = new ArrayList<>();

    public static <I extends Item> I registerItem(Identifier id, I item, ItemGroup group, RegisterUtil.Model model){
        ITEM_MODEL.put(item, model);
        ItemGroupEvents.modifyEntriesEvent(group).register(content -> content.add(item.getDefaultStack()));
        return Registry.register(Registries.ITEM, id, item);
    }

    public static <I extends Item> I registerItem(String id, I item, ItemGroup group, RegisterUtil.Model model){
        return RegisterUtil.registerItem(IdUtil.get(id), item, group, model);
    }

    public interface colorItem{
        BaseItem get(DyeColor dyeColor);
    }

    public static List<BaseItem> registerColorItems(DyeColor[] dyeColors, colorItem colorItem, @Nullable ItemGroup group){
        List<BaseItem> items = new ArrayList<>();
        for (DyeColor dyeColor: dyeColors){
            BaseItem item = colorItem.get(dyeColor);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> dyeColor.getMapColor().color, item);
            if (group == null){
                continue;
            }
            items.add(RegisterUtil.registerItem(item.name, item, group, RegisterUtil.Model.GENERATED));
        }

        COLORS_ITEM_LIST.add(items);
        return items;
    }

    public static Block registerBlock(Identifier id, Block block){
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static Block registerBlock(IBaseBlock block){
        return RegisterUtil.registerBlock(block.getId(), (Block) block);
    }

    public static BlockEntityType<?> registerBlockEntity(Identifier id, Block block, FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> be){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.create(be, block).build(null));
    }

    public static BlockEntityType<?> registerBlockEntity(IBaseBlock block, FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> be){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, block.getId(), FabricBlockEntityTypeBuilder.create(be, (Block) block).build(null));
    }

    public static void registerBlocks(BaseBlock[] blocks){
        for (BaseBlock block: blocks){
            RegisterUtil.registerBlock(IdUtil.get(block.getBlockName()), block);
        }
    }

}
