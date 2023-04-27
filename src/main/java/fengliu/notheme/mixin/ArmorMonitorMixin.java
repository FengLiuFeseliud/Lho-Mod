package fengliu.notheme.mixin;

import fengliu.notheme.callback.IArmorChangeCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorItem.class)
public class ArmorMonitorMixin {

    @Inject(method = "use", at = @At("RETURN"))
    public void useEnd(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        if (!cir.getReturnValue().getResult().isAccepted()){
            return;
        }

        IArmorChangeCallback.EQUIP_EVENT.invoker().interact(user, cir.getReturnValue().getValue());

        ItemStack stack = user.getStackInHand(hand);
        if (stack.isEmpty()){
            return;
        }
        IArmorChangeCallback.REMOVE_EVENT.invoker().interact(user, stack);
    }
}
