package fengliu.notheme.item.inventory.block;

import fengliu.notheme.NoThemeMod;
import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicReference;

public class BentoBox extends ReinforcedBag {
    public BentoBox(IBaseBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean canResetBagStack(ItemStack useStack, ItemStack bagStack) {
        return !useStack.isFood();
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        DefaultedList<ItemStack> stacks = this.getStacks(stack.getOrCreateNbt());
        ItemStack useItemStack = this.getUseItemStack(stacks);
        if (useItemStack.isEmpty()){
            return user.eatFood(world, stack);
        }

        if (useItemStack.isFood()) {
            ItemStack oldUseItemStack = useItemStack.copy();

            useItemStack.finishUsing(world, user);
            this.resetBagStack(stack, oldUseItemStack, stacks, (PlayerEntity) user, user.getActiveHand());
        }

        return stack;
    }

    public TypedActionResult<ItemStack> edibleItem(PlayerEntity user, Hand hand){
        AtomicReference<TypedActionResult<ItemStack>> result = new AtomicReference<>();
        if (this.uesItem(user, hand, stack -> {
            result.set(stack.use(user.getWorld(), user, hand));
        })){
            return result.get();
        }

        return null;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        TypedActionResult<ItemStack> result = this.edibleItem(user, hand);
        if (result == null){
            return super.use(world, user, hand);
        }
        return result;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        TypedActionResult<ItemStack> result = this.edibleItem(context.getPlayer(), context.getHand());
        if (result == null){
            return super.useOnBlock(context);
        }
        return result.getResult();
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        TypedActionResult<ItemStack> result = this.edibleItem(user, hand);
        if (result == null){
            return super.useOnEntity(stack, user, entity, hand);
        }
        return result.getResult();
    }
}
