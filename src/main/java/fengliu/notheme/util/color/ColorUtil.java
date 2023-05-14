package fengliu.notheme.util.color;

import fengliu.notheme.NoThemeMod;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.RegisterUtil;
import fengliu.notheme.util.item.BaseItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.data.client.Models.GENERATED;

public class ColorUtil {

    public interface colorItem{
        IColor get(DyeColor dyeColor);
    }

    public static List<IColor> getColorItems(DyeColor[] dyeColors, colorItem colorItem){
        List<IColor> items = new ArrayList<>();
        for (DyeColor dyeColor: dyeColors){
            items.add(colorItem.get(dyeColor));
        }
        return items;
    }

    public static void registerAllItem(List<IColor> colorItems){
        colorItems.forEach((color) -> {
            RegisterUtil.registerItem(IdUtil.get(((BaseItem) color).name), (Item) color);
            RegisterUtil.registerItemColor(color.getColor().getMapColor().color, (Item) color);
        });
    }

    public static void registerAllItemGroupAll(List<IColor> colorItems, FabricItemGroupEntries content){
        colorItems.forEach(colorItem -> content.add(new ItemStack((Item) colorItem)));
    }

    public static void registerAllModel(List<IColor> colorItems, ItemModelGenerator itemModelGenerator){
        colorItems.forEach(item -> {
            GENERATED.upload(ModelIds.getItemModelId((Item) item), TextureMap.layer0(new Identifier(NoThemeMod.MOD_ID, item.getTextureName()).withPrefixedPath("item/")), itemModelGenerator.writer);
        });
    }
}
