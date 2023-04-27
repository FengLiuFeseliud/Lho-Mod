package fengliu.notheme.item.heart;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.SpawnUtil;
import fengliu.notheme.util.player.IExtendPlayer;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import static net.minecraft.world.RaycastContext.FluidHandling;

public class EmptyBloodNeedle extends BloodNeedle {
    public EmptyBloodNeedle(Settings settings, String id) {
        super(settings, id);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient() || user.isCreative() || user.isSneaking()){
            return super.use(world, user, hand);
        }

        if (raycast(world, user, FluidHandling.WATER).getType() == HitResult.Type.BLOCK){
            return super.use(world, user, hand);
        }

        float absorption = user.getAbsorptionAmount();
        if (absorption >= 2){
            user.setAbsorptionAmount(absorption - 2);
            user.getStackInHand(hand).setCount(0);
            SpawnUtil.spawnItemToPlayer(new ItemStack(ModItems.GOLD_BLOOD_NEEDLE, 1), user, world);
            return super.use(world, user, hand);
        } else if (absorption != 0) {
            user.setAbsorptionAmount(0);
        }

        float maxHealth = user.getMaxHealth();
        if (!((IExtendPlayer)user).addDeleteHealth(2) && maxHealth == 2){
            ((IExtendPlayer)user).addDeleteHealth(1.9f);
            user.damage(user.getDamageSources().cactus(), 1.9f);
        } else{
            user.damage(user.getDamageSources().cactus(), 2);
        }
        
        if (user.hasStatusEffect(StatusEffects.POISON)){
            user.setStackInHand(hand, new ItemStack(ModItems.POISON_BLOOD_NEEDLE, 1));
        } else if (user.hasStatusEffect(StatusEffects.WITHER)) {
            user.setStackInHand(hand, new ItemStack(ModItems.WITHER_BLOOD_NEEDLE, 1));
        } else if (user.isFrozen()) {
            user.setStackInHand(hand, new ItemStack(ModItems.FROST_BLOOD_NEEDLE, 1));
        } else {
            user.setStackInHand(hand, new ItemStack(ModItems.BLOOD_NEEDLE, 1));
        }
        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient()){
            return super.useOnBlock(context);
        }

        PlayerEntity player = context.getPlayer();
        if (player == null){
            return super.useOnBlock(context);
        }

        if (!world.getBlockState(raycast(world, player, FluidHandling.WATER).getBlockPos()).isOf(Blocks.WATER)){
            return super.useOnBlock(context);
        }
        
        player.setStackInHand(context.getHand(), new ItemStack(ModItems.WATER_BLOOD_NEEDLE, 1));
        return super.useOnBlock(context);
    }
}
