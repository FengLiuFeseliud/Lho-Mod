package fengliu.notheme.callback;

import fengliu.notheme.NoThemeMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public class ArmorChangeCallback {

    private static ActionResult equipEvent(PlayerEntity player, ItemStack stack) {
//        Item item = stack.getItem();
//        if (item instanceof BaseArmorItem){
//            ((BaseArmorItem) item).material.append((IExtendPlayer) player, ((BaseArmorItem) item).getType());
//        }
        NoThemeMod.LOGGER.info("穿上了 "+stack);
        return ActionResult.SUCCESS;
    }

    private static ActionResult removeEvent(PlayerEntity player, ItemStack stack) {
//        Item item = stack.getItem();
//        if (item instanceof BaseArmorItem){
//            ((BaseArmorItem) item).material.remove((IExtendPlayer) player, ((BaseArmorItem) item).getType());
//        }
        NoThemeMod.LOGGER.info("脱下了 "+stack);
        return ActionResult.SUCCESS;
    }

    public static void registerAllCallback(){
        IArmorChangeCallback.EQUIP_EVENT.register(ArmorChangeCallback::equipEvent);
        IArmorChangeCallback.REMOVE_EVENT.register(ArmorChangeCallback::removeEvent);
    }
}
