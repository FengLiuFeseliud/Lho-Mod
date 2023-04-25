package fengliu.lho.mixin;

import fengliu.lho.LhoMod;
import fengliu.lho.callback.IArmorChangeCallback;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 监听装备变化
 */
@Mixin(ServerPlayNetworkHandler.class)
public abstract class PlayerInventoryArmorMonitorMixin implements ServerPlayPacketListener  {

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onClickSlot", at = @At("RETURN"))
    public void onClickSlot(ClickSlotC2SPacket packet, CallbackInfo ci) {
        if (!(this.player.currentScreenHandler instanceof PlayerScreenHandler)){
            return;
        }

        Int2ObjectMap<ItemStack> oldSlotMap = packet.getModifiedStacks();
        SlotActionType type = packet.getActionType();

        if (oldSlotMap.size() == 2 && type == SlotActionType.QUICK_MOVE){
            oldSlotMap.forEach((slot, stack) -> {
                if (stack.isEmpty()) {
                    return;
                }

                if (slot > 4 && slot < 9){
                    IArmorChangeCallback.EQUIP_EVENT.invoker().interact(this.player, stack);
                    return;
                }

                if (slot >= 9){
                    IArmorChangeCallback.REMOVE_EVENT.invoker().interact(this.player, stack);
                }

            });
            return;
        }

        int slot = packet.getSlot();
        if (!(slot > 4 && slot < 9)){
            return;
        }

        if (oldSlotMap.isEmpty()){
            return;
        }

        ItemStack stack = packet.getStack();
        if (stack.isEmpty()){
            IArmorChangeCallback.EQUIP_EVENT.invoker().interact(this.player, (ItemStack) oldSlotMap.values().toArray()[0]);
        } else{
            IArmorChangeCallback.REMOVE_EVENT.invoker().interact(this.player, stack);

            stack = (ItemStack) oldSlotMap.values().toArray()[0];
            if (stack.isEmpty()) {
                return;
            }
            IArmorChangeCallback.EQUIP_EVENT.invoker().interact(this.player, (ItemStack) oldSlotMap.values().toArray()[0]);
        }
    }
}
