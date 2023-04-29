package fengliu.notheme.block.entity;

import fengliu.notheme.NoThemeMod;
import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.block.heart.BloodPoolBlock;
import fengliu.notheme.item.ModItems;
import fengliu.notheme.item.heart.EmptyHeart;
import fengliu.notheme.screen.handler.BloodPoolBlockScreenHandler;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.Tick;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.block.IPoolBlock;
import fengliu.notheme.util.block.entity.ScreenBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BloodPoolBlockEntity extends ScreenBlockEntity {
    public int level = 0;
    public int heartType = 0;
    public Tick cokyTick = new Tick(100);
    public Tick fillTick = new Tick(200);

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            if (index == 0){
                return level;
            }

            if (index == 1){
                return heartType;
            }

            if (index == 2){
                return cokyTick.getTick();
            }

            if (index == 3){
                return fillTick.getTick();
            }

            return 0;
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int size() {
            return 4;
        }
    };

    public BloodPoolBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.BLOOD_POOL_BLOCK_ENTITY, pos, state);

        this.setMaxItemStack(3);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity be) {
        BloodPoolBlockEntity bloodPoolBlock = (BloodPoolBlockEntity) be;
        IPoolBlock poolBlock = (IPoolBlock) state.getBlock();

        bloodPoolBlock.level = state.get(BloodPoolBlock.LEVEL);
        bloodPoolBlock.heartType = state.get(BloodPoolBlock.HEART_TYPE);

        if (bloodPoolBlock.level < 9){
            ItemStack heartStack = bloodPoolBlock.getStack(0);
            if (heartStack.getItem() instanceof EmptyHeart && !heartStack.isOf(ModItems.EMPTY_HEART)){
                poolBlock.putItemToPool(state, heartStack, world, pos);
                heartStack.decrement(1);
            }
            return;
        }

        ItemStack cokyStack = bloodPoolBlock.getStack(2);
        if (!cokyStack.isEmpty()){
            return;
        }

        ItemStack curingAgentStack = bloodPoolBlock.getStack(1);
        if (curingAgentStack.isEmpty() || curingAgentStack.getCount() < 2){
            return;
        }

        if (!bloodPoolBlock.cokyTick.canAccomplish()){
            return;
        }

        if (!bloodPoolBlock.fillTick.canAccomplish()){
            return;
        }

        bloodPoolBlock.setStack(2, new ItemStack(ModItems.ANIMAL_HEART, 1));
        curingAgentStack.decrement(2);

        if (world.getBlockState(pos.down()).getBlock() instanceof HopperBlock){
            poolBlock.clear(world, pos, state);
        }else {
            poolBlock.addLevel(world, pos, state);
        }

        bloodPoolBlock.cokyTick.clearTick();
        bloodPoolBlock.fillTick.clearTick();

    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        if (side == Direction.UP && stack.getItem() instanceof EmptyHeart && slot == 0){
            return !stack.isOf(ModItems.EMPTY_HEART);
        }

        if (side == Direction.SOUTH || side == Direction.NORTH || side == Direction.WEST || side == Direction.EAST){
            if (slot != 1){
                return false;
            }

            return stack.isOf(ModItems.CUING_AGENT);
        }

        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        if (side == Direction.DOWN && slot == 2){
            return stack.isOf(ModItems.ANIMAL_HEART);
        }
        return false;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(IdUtil.getDisplayName(((IBaseBlock) ModBlocks.BLOOD_POOL_BLOCK).getBlockName()));
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BloodPoolBlockScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }
}
