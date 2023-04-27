package fengliu.notheme.mixin;

import fengliu.notheme.networking.packets.server.ModServerMessage;
import fengliu.notheme.util.IPersistentData;
import fengliu.notheme.util.player.IExtendPlayer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 使用 NBT 扩展玩家设置
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerExtendMixin extends LivingEntity implements IPersistentData, IExtendPlayer {
    @Shadow public abstract boolean isPlayer();

    @Shadow public abstract PlayerInventory getInventory();

    private NbtCompound persistentData;

    protected PlayerExtendMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public float getDeleteHealth(){
        if (!this.getPersistentData().contains("deleteHealth", NbtElement.FLOAT_TYPE)){
            this.getPersistentData().putFloat("deleteHealth", 0);
            syncData();

            return 0;
        }

        return this.getPersistentData().getFloat("deleteHealth");
    }

    @Override
    public boolean setDeleteHealth(float health){
        if (this.getMaxHealth() - health <= 0 || health < 0){
            return false;
        }

        this.getPersistentData().putFloat("deleteHealth", health);
        this.syncData();
        return true;
    }

    @Override
    public boolean addDeleteHealth(float health){
        if (this.getMaxHealth() - health <= 0 || health <= 0){
            return false;
        }

        this.getPersistentData().putFloat("deleteHealth", this.getDeleteHealth() + health);
        this.syncData();
        return true;
    }

    @Override
    public boolean subtractDeleteHealth(float health) {
        if (health <= 0){
            return false;
        }

        float deleteHealth = this.getDeleteHealth() - health;
        if (deleteHealth < 0){
            return false;
        }

        this.getPersistentData().putFloat("deleteHealth", deleteHealth);
        this.syncData();
        return true;
    }

    @Override
    public float getAddHealth() {
        if (!this.getPersistentData().contains("addHealth", NbtElement.FLOAT_TYPE)){
            this.getPersistentData().putFloat("addHealth", 0);
            syncData();

            return 0;
        }

        return this.getPersistentData().getFloat("addHealth");
    }

    @Override
    public boolean setAddHealth(float health){
        if (health <= 0){
            return false;
        }

        this.getPersistentData().putFloat("addHealth", health);
        this.syncData();
        return true;
    }

    @Override
    public boolean addAddHealth(float health){
        if (health <= 0){
            return false;
        }

        this.getPersistentData().putFloat("addHealth", this.getAddHealth() + health);
        this.syncData();
        return true;
    }

    @Override
    public boolean subtractAddHealth(float health) {
        if (health <= 0){
            return false;
        }

        float addHealth = this.getAddHealth() - health;
        if (addHealth < 0){
            return false;
        }

        this.getPersistentData().putFloat("addHealth", addHealth);
        this.syncData();
        return true;
    }

    @Override
    public NbtCompound getPersistentData() {
        if(this.persistentData != null){
            return persistentData;
        }
        NbtCompound persistent_data = new NbtCompound();
        persistent_data.putFloat("deleteHealth", 0);
        persistent_data.putFloat("addHealth", 0);
        this.persistentData = persistent_data;
        return this.persistentData;
    }

    @Override
    public void writePersistentData(NbtCompound nbt) {
        if (nbt == null) {
            return;
        }

        if(!nbt.contains("notheme.extend", NbtElement.COMPOUND_TYPE)){
            return;
        }

        persistentData = nbt.getCompound("notheme.extend");

        float maxHealth = this.getMaxHealth();
        if (this.getHealth() > maxHealth){
            this.setHealth(maxHealth);
        }
    }

    @Override
    public void clearPersistentData() {
        this.persistentData = null;
        this.syncData();
    }

    @Override
    public void syncData() {
        if (this.getWorld().isClient() || !this.isPlayer()){
            return;
        }

        if (((ServerPlayerEntity) (Object) this).networkHandler == null){
            return;
        }

        ServerPlayNetworking.send((ServerPlayerEntity) (Object) this, ModServerMessage.SYNC_DATA, PacketByteBufs.create().writeNbt((this.getPersistentData())));
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    public void writeCultivationDataToNbt(NbtCompound nbt, CallbackInfo info) {
        nbt.put("notheme.extend", this.getPersistentData());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    public void readCultivationDataFromNbt(NbtCompound nbt, CallbackInfo info) {
        this.writePersistentData(nbt);
    }
}
