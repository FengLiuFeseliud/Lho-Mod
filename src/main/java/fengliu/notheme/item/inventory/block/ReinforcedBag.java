package fengliu.notheme.item.inventory.block;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.item.block.BaseBlockItem;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import fengliu.notheme.util.block.entity.IInventory;
import fengliu.notheme.util.item.BaseItem;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.item.ItemStack.DAMAGE_KEY;

public class ReinforcedBag extends BaseBlockItem {

    public ReinforcedBag(IBaseBlock block, Settings settings) {
        super(block, settings);
    }

    protected interface Ues{
        void in(ItemStack stack);
    }

    public DefaultedList<ItemStack> getStacks(NbtCompound bagStackNbt){
        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(((ItemStackInventoryBlock) this.getBlock()).getSize(), ItemStack.EMPTY);
        Inventories.readNbt(bagStackNbt.getCompound(BLOCK_ENTITY_TAG_KEY), stacks);
        return stacks;
    }

    public ItemStack getUseItemStack(DefaultedList<ItemStack> stacks){
        for(ItemStack stack: stacks){
            if (stack.isEmpty() || stack.getItem() instanceof ToolItem){
                continue;
            }
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public void resetBagStack(ItemStack oldBagStack, ItemStack useStack, DefaultedList<ItemStack> stacks, PlayerEntity user, Hand hand){
        NbtCompound bagStackNbt = oldBagStack.getOrCreateNbt();

        useStack.decrement(1);
        if (useStack.isEmpty()){
            bagStackNbt.putInt(DAMAGE_KEY, oldBagStack.getDamage()-1);
        }

        bagStackNbt.put(BLOCK_ENTITY_TAG_KEY, Inventories.writeNbt(bagStackNbt.getCompound(BLOCK_ENTITY_TAG_KEY), stacks));
        oldBagStack.setNbt(bagStackNbt);
        user.setStackInHand(hand, oldBagStack);
    }

    public boolean canResetBagStack(ItemStack useStack, ItemStack bagStack){
        return true;
    }

    public boolean uesItem(PlayerEntity user, Hand hand, Ues ues){
        if (user.isSneaking()){
            return false;
        }

        ItemStack bagStack = user.getStackInHand(hand);
        NbtCompound nbt = bagStack.getOrCreateNbt();

        if (!nbt.contains(BLOCK_ENTITY_TAG_KEY, NbtElement.COMPOUND_TYPE)){
            return false;
        }

        DefaultedList<ItemStack> stacks = this.getStacks(nbt);
        ItemStack useStack = this.getUseItemStack(stacks);
        if (useStack.isEmpty()){
            return false;
        }

        ItemStack oldBagStack = bagStack.copy();
        ues.in(useStack);

        if (this.canResetBagStack(useStack, bagStack)){
            this.resetBagStack(oldBagStack, useStack, stacks, user, hand);
        }
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        this.uesItem(user, hand, uesStack -> {
            uesStack.use(world, user, hand);
        });
        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (this.uesItem(user, hand, useStack -> {
            useStack.useOnEntity(user, entity, hand);
        })){
            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (this.uesItem(context.getPlayer(), context.getHand(), useStack -> {
            useStack.useOnBlock(context);
        })){
            return ActionResult.SUCCESS;
        }
        return super.useOnBlock(context);
    }

    @Override
    public Text getName(ItemStack stack) {
        ItemStack useStack = this.getUseItemStack(this.getStacks(stack.getOrCreateNbt()));
        if (useStack.isEmpty()){
            return super.getName(stack);
        }
        return ((MutableText) super.getName(stack)).append(Text.translatable(IdUtil.getItemInfo("reinforced_bag"), useStack.getName()));
    }
}
