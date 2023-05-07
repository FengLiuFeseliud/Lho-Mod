package fengliu.notheme.block.entity;

import fengliu.notheme.block.mini.device.BedrockBreakerBlock;
import fengliu.notheme.util.Tick;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BedrockBreakerBlockEntity extends BlockEntity {
    public final Tick useTick = new Tick(60);
    private int clickCount = 0;
    public PlayerEntity usePlayer = null;
    private int damage = 0;

    public BedrockBreakerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.BEDROCK_BREAKER_BLOCK_ENTITY, pos, state);
    }

    public int getDamage(){
        return this.damage;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public void setUsePlayer(PlayerEntity player){
        this.usePlayer = player;
    }

    public PlayerEntity getUsePlayer(){
        return this.usePlayer;
    }

    public void addClick() {
        this.clickCount++;
    }

    public boolean canRemoveBlock(){
        return this.clickCount >= 30;
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity be) {
        if (!(be instanceof BedrockBreakerBlockEntity bedrockBreakerBlock)){
            return;
        }

        if (bedrockBreakerBlock.getUsePlayer() == null || (bedrockBreakerBlock.damage == 1 && bedrockBreakerBlock.clickCount == 0)){
            return;
        }

        if (!bedrockBreakerBlock.useTick.canAccomplish()){
            if (bedrockBreakerBlock.useTick.getTick() % 5 != 0){
                return;
            }

            if (state.get(BedrockBreakerBlock.CLICK) == 1){
                world.setBlockState(pos, state.with(BedrockBreakerBlock.CLICK, 2));
            } else {
                world.setBlockState(pos, state.with(BedrockBreakerBlock.CLICK, 1));
            }
            return;
        }

        world.createExplosion(bedrockBreakerBlock.usePlayer, pos.getX(), pos.up().getY(), pos.getZ(), 2, false, World.ExplosionSourceType.TNT);
        world.createExplosion(bedrockBreakerBlock.usePlayer, pos.getX(), pos.down().getY(), pos.getZ(), 2, false, World.ExplosionSourceType.TNT);
        world.setBlockState(pos, state.with(BedrockBreakerBlock.USE, true));

        if (bedrockBreakerBlock.canRemoveBlock()){
            world.removeBlock(pos.down(), false);
        }

        bedrockBreakerBlock.useTick.clearTick();
        bedrockBreakerBlock.setUsePlayer(null);
    }
}
