package fengliu.notheme.mixin;

import fengliu.notheme.item.tool.EmptyColorPicker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ItemFrameEntity.class)
public abstract class ItemFrameEntityMixin extends AbstractDecorationEntity {
    @Final
    @Shadow
    private static TrackedData<ItemStack> ITEM_STACK;

    protected ItemFrameEntityMixin(EntityType<? extends AbstractDecorationEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void pickItemFrameColor(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        if (!(player.getStackInHand(hand).getItem() instanceof EmptyColorPicker)){
            return;
        }

        String path = Registries.ITEM.getId((this.getDataTracker().get(ITEM_STACK)).getItem()).getPath();
        EmptyColorPicker.pickerColorInHand(path, player, hand);

        cir.cancel();
        cir.setReturnValue(ActionResult.SUCCESS);
    }
}
