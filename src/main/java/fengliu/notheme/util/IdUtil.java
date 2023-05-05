package fengliu.notheme.util;

import fengliu.notheme.NoThemeMod;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class IdUtil {

    public static Identifier get(String name){
        return new Identifier(NoThemeMod.MOD_ID, name);
    }

    public static Identifier getTooltip(String name){
        return new Identifier(NoThemeMod.MOD_ID, name);
    }

    public static String getItemInfo(String name){
        return NoThemeMod.MOD_ID + ".item." + name + ".info";
    }

    public static String getItemInfo(String name, int index){
        return IdUtil.getItemInfo(name) + "." + index;
    }

    public static String getItemTooltip(String name){
        return "item." + NoThemeMod.MOD_ID + "." + name + ".tooltip";
    }

    public static String getItemTooltip(String name, int index){
        return "item." + NoThemeMod.MOD_ID + "." + name + ".tooltip." + index;
    }

    public static String getBlockItemTooltip(String name){
        return "block.item." + NoThemeMod.MOD_ID + "." + name + ".tooltip";
    }

    public static String getBlockItemTooltip(String name, int index){
        return "block.item." + NoThemeMod.MOD_ID + "." + name + ".tooltip." + index;
    }

    public static Text getItemName(String name){
        return Text.translatable("item." + NoThemeMod.MOD_ID + "." + name);
    }

    public static String getDisplayName(String name){
        return "block." + NoThemeMod.MOD_ID + "." + name + ".display.name";
    }

    public static String getDeathMessage(String name){
        return NoThemeMod.MOD_ID + ".death." + name;
    }
}
