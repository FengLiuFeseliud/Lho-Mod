package fengliu.notheme.item.tool;

import fengliu.notheme.entity.ColorWaterBalloonEntity;
import fengliu.notheme.util.color.IColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;

public class ColorWaterBalloon extends WaterBalloon implements IColor {
    public final DyeColor dyeColor;

    public ColorWaterBalloon(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.dyeColor = dyeColor;
    }

    @Override
    public DyeColor getColor() {
        return this.dyeColor;
    }

    @Override
    public ThrownItemEntity getEntity(World world, PlayerEntity user) {
        return new ColorWaterBalloonEntity(user, world);
    }
}
