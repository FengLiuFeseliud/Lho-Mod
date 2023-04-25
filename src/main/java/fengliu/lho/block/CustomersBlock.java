package fengliu.lho.block;

import fengliu.lho.block.entity.CustomersBlockEntity;
import fengliu.lho.block.entity.ModBlockEntitys;
import fengliu.lho.util.level.LevelsUtil;
import fengliu.lho.util.level.ILevelBlock;
import fengliu.lho.util.block.ScreenBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomersBlock extends ScreenBlock {

    public CustomersBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient){
           return super.onUse(state, world, pos, player, hand, hit);
        }

        if (!LevelsUtil.canUpgrade(this, player, hand, world)){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (!LevelsUtil.upgrade(this, ModBlocks.CUSTOMERS_BLOCKS, pos, world)){
            player.sendMessage(Text.translatable("lho.block.upgrade.info"));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void openHandledScreen(BlockEntity entity, PlayerEntity player) {
        if (entity instanceof CustomersBlockEntity){
            player.openHandledScreen((CustomersBlockEntity) entity);
        }
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return ModBlockEntitys.CustomersBlockEntityTypes.get(ModBlocks.CUSTOMERS_BLOCKS.get(this));
    }

    @Override
    public BlockEntityTicker<? super BlockEntity> uesTick() {
        return CustomersBlockEntity::tick;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CustomersBlockEntity(pos, state);
    }

    public enum CustomersBlockLevels implements ILevelBlock {
        Lv1(1, 10),
        Lv2(2, 20),
        Lv3(3, 30),
        Lv4(4, 40),
        Lv5(5, 50),
        Lv6(6, 60),
        Lv7(7, 70),
        Lv8(8, 80),
        Lv9(9, 90),
        Lv10(10, 100);

        private final int level;
        private final int gain;

        CustomersBlockLevels(int level, int gain){
            this.level = level;
            this.gain = gain;
        }

        @Override
        public String getIdName() {
            return "customers_block";
        }

        @Override
        public Block getBlock() {
            return new CustomersBlock(AbstractBlock.Settings.of(Material.PISTON));
        }

        @Override
        public FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> getBlockEntityNew() {
            return CustomersBlockEntity::new;
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getMaxLevel() {
            return CustomersBlockLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }
    }
}
