package fengliu.notheme.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface IArmorChangeCallback {

    Event<IArmorChangeCallback> EQUIP_EVENT = EventFactory.createArrayBacked(IArmorChangeCallback.class,
        (listeners) -> (player, stack) -> {
            if (player.getWorld().isClient()){
                return ActionResult.PASS;
            }

            for (IArmorChangeCallback listener : listeners) {
                ActionResult result = listener.interact(player, stack);

                if(result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });


    Event<IArmorChangeCallback> REMOVE_EVENT = EventFactory.createArrayBacked(IArmorChangeCallback.class,
        (listeners) -> (player, stack) -> {
            if (player.getWorld().isClient()){
                return ActionResult.PASS;
            }

            for (IArmorChangeCallback listener : listeners) {
                ActionResult result = listener.interact(player, stack);

                if(result != ActionResult.PASS) {
                    return result;
                }
            }

            return ActionResult.PASS;
        });

    ActionResult interact(PlayerEntity player, ItemStack stack);
}

