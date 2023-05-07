package fengliu.notheme.item.inventory.block;

import fengliu.notheme.util.block.IBaseBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PotionItem;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicReference;

public class DrinkHolder extends BentoBox {
    public DrinkHolder(IBaseBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public TypedActionResult<ItemStack> edibleItem(PlayerEntity user, Hand hand){
        ItemStack bagStack = user.getStackInHand(hand);
        ItemStack useStack = this.getUseItemStack(this.getStacks(bagStack.getOrCreateNbt()));
        if (!this.canEatItemStack(useStack, bagStack)){
            return null;
        }

        return super.edibleItem(user, hand);
    }

    @Override
    public boolean useItem(PlayerEntity user, Hand hand, Ues ues) {
        ItemStack bagStack = user.getStackInHand(hand);
        ItemStack useStack = this.getUseItemStack(this.getStacks(bagStack.getOrCreateNbt()));
        if (!this.canEatItemStack(useStack, bagStack)){
            ues = stack -> {
                user.setStackInHand(hand, stack);
                stack.use(user.getWorld(), user, hand);
                user.setStackInHand(hand, bagStack);
            };
        }

        return super.useItem(user, hand, ues);
    }

    @Override
    public boolean canResetBagStack(ItemStack useStack, ItemStack bagStack) {
        return useStack.getItem() instanceof ThrowablePotionItem || useStack.isEmpty();
    }

    @Override
    public boolean canEatItemStack(ItemStack useStack, ItemStack bagStack) {
        return useStack.getItem() instanceof PotionItem && !(useStack.getItem() instanceof ThrowablePotionItem);
    }

}
