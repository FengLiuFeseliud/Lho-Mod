package fengliu.lho.util;

import fengliu.lho.LhoMod;
import net.minecraft.util.Identifier;

public class IdUtil {

    public static Identifier get(String name){
        return new Identifier(LhoMod.MOD_ID, name);
    }

    public static Identifier getTooltip(String name){
        return new Identifier(LhoMod.MOD_ID, name);
    }

    public static String getItemTooltip(String name){
        return "item." + LhoMod.MOD_ID + "." + name + ".tooltip";
    }

    public static String getItemTooltip(String name, int index){
        return "item." + LhoMod.MOD_ID + "." + name + ".tooltip." + index;
    }

    public static String getBlockItemTooltip(String name){
        return "block.item." + LhoMod.MOD_ID + "." + name + ".tooltip";
    }

    public static String getBlockItemTooltip(String name, int index){
        return "block.item." + LhoMod.MOD_ID + "." + name + ".tooltip." + index;
    }

    public static String getDisplayName(String name){
        return "block." + LhoMod.MOD_ID + "." + name + ".display.name";
    }

    public static String getDeathMessage(String name){
        return LhoMod.MOD_ID + ".death." + name;
    }
}
