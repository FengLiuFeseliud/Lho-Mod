package fengliu.notheme.data.generation;

import fengliu.notheme.NoThemeMod;
import fengliu.notheme.item.ModItems;
import fengliu.notheme.item.food.ice.cream.IIceCreamLevel;
import fengliu.notheme.util.color.ColorUtil;
import fengliu.notheme.util.level.ILevelItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Map;

import static net.minecraft.data.client.Models.GENERATED;

public class ItemDataGeneration extends FabricModelProvider {
    public ItemDataGeneration(FabricDataOutput output) {
        super(output);
    }

    public static void registerAllIceCreamBarItemModel(Map<Item, ILevelItem> items, ItemModelGenerator itemModelGenerator){
        items.forEach((item, level) -> {
            GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(new Identifier(NoThemeMod.MOD_ID, ((IIceCreamLevel) level).getThawName()).withPrefixedPath("item/" + level.getIdName() + "/")), itemModelGenerator.writer);
        });
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        ColorUtil.registerAllModel(ModItems.ICE_CREAM_BAR_PACKS, itemModelGenerator);
        ColorUtil.registerAllModel(ModItems.PACK_ICE_CREAM_BARS, itemModelGenerator);

        ItemDataGeneration.registerAllIceCreamBarItemModel(ModItems.ICE_CREAM_BARS, itemModelGenerator);
        ItemDataGeneration.registerAllIceCreamBarItemModel(ModItems.CHOCOLATE_CRUST_ICE_CREAM_BARS, itemModelGenerator);
    }
}
