package fengliu.notheme.entity;

import fengliu.notheme.util.IdUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ColorProjectileEntity extends ProjectileEntity {
    private static final TrackedData<Integer> COLOR;

    static {
        COLOR = DataTracker.registerData(ColorProjectileEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    public ColorProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(COLOR, DyeColor.WHITE.getId());
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.dataTracker.get(COLOR));
    }

    public Identifier getTexture(){
        return IdUtil.get("texture/");
    }

    public void setColor(DyeColor color) {
        if (color == null){
            this.dataTracker.set(COLOR, -1);
            return;
        }
        this.dataTracker.set(COLOR, color.getId());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("color", this.getColor().getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        int colorId = nbt.getInt("color");
        if (colorId == -1){
            this.setColor(null);
            return;
        }
        this.setColor(DyeColor.byId(colorId));
    }
}
