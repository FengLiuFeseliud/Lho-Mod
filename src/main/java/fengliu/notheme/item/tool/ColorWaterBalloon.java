package fengliu.notheme.item.tool;

import fengliu.notheme.entity.ColorWaterBalloonEntity;
import fengliu.notheme.util.color.IColor;
import fengliu.notheme.util.item.BaseItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ColorWaterBalloon extends BaseItem implements IColor {
    public final DyeColor dyeColor;

    public ColorWaterBalloon(Settings settings, String textureName) {
        super(settings, textureName);
        this.dyeColor = null;
    }

    public ColorWaterBalloon(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.dyeColor = dyeColor;
    }

    @Override
    public DyeColor getColor() {
        return this.dyeColor;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            ColorWaterBalloonEntity waterBalloon = new ColorWaterBalloonEntity(user, world);
            waterBalloon.setItem(itemStack);
            waterBalloon.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(waterBalloon);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
