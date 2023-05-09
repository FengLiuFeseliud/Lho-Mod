package fengliu.notheme.item.inventory.block.bottle;

import fengliu.notheme.item.block.BaseBlockItem;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import fengliu.notheme.util.item.IItemStackInventoryBlockItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Bottle extends BaseBlockItem implements IItemStackInventoryBlockItem {
    public Bottle(IBaseBlock block, Settings settings) {
        super(block, settings);
    }

    public static ItemStack getPotion(ItemStack stack){
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains("potion", NbtElement.COMPOUND_TYPE)){
            return ItemStack.EMPTY;
        }

        return ItemStack.fromNbt(nbt.getCompound("potion"));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!(user instanceof PlayerEntity player) ||  world.isClient()){
            return super.finishUsing(stack, world, user);
        }

        ItemStack potion = Bottle.getPotion(stack);
        if (!potion.isEmpty()){
            PotionUtil.getPotionEffects(potion).forEach(statusEffectInstance -> {
                if (statusEffectInstance.getEffectType().isInstant()) {
                    statusEffectInstance.getEffectType().applyInstantEffect(player, player, user, statusEffectInstance.getAmplifier(), 1.0);
                    return;
                }
                user.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
            });
        }

        if (!player.isCreative()){
            return ModBlockItems.EMPTY_BOTTLE_BLOCK_ITEM.getDefaultStack();
        }

        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public Text getName(ItemStack stack) {
        ItemStack potion = Bottle.getPotion(stack);
        if (potion.isEmpty()){
            return super.getName(stack);
        }

        return Text.translatable(IdUtil.getItemInfo("bottle")).append(Text.translatable(potion.getTranslationKey()));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        PotionUtil.buildTooltip(Bottle.getPotion(stack), tooltip, 1.0f);
    }

    @Override
    public ItemStackInventoryBlock getInventoryBlock() {
        return (ItemStackInventoryBlock) this.getBlock();
    }
}
