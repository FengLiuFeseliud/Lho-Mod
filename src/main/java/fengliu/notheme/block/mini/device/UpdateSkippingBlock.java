package fengliu.notheme.block.mini.device;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.util.SpawnUtil;
import fengliu.notheme.util.block.BaseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class UpdateSkippingBlock extends BaseBlock {
    public static final IntProperty PLACED = IntProperty.of("placed", 0, 16);
    public static final IntProperty BREAK = IntProperty.of("break", 0, 16);
    public static final IntProperty USE = IntProperty.of("use", 0, 4);
    public static final BooleanProperty IGNITE = BooleanProperty.of("ignite");
    public static final BooleanProperty HALF = BooleanProperty.of("half");

    public UpdateSkippingBlock(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        ItemStack stack = player.getStackInHand(hand);
        if (state.get(HALF)){
            if (stack.isOf(ModBlockItems.UPDATE_SKIPPING_BLOCK_ITEM)){
                stack.decrement(1);
                world.setBlockState(pos, state.with(HALF, false));
            }

            return super.onUse(state, world, pos, player, hand, hit);
        }

        int placed = state.get(PLACED);
        if (stack.isOf(Items.OBSIDIAN) && placed < 16){
            stack.decrement(1);
            world.setBlockState(pos, state.with(PLACED, placed + 1));
            world.playSound(null, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS);
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (placed != 16){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        boolean ignite = state.get(IGNITE);
        if (stack.isOf(Items.FLINT_AND_STEEL) && !ignite){
            stack.damage(1, Random.create(), (ServerPlayerEntity) player);
            world.setBlockState(pos, state.with(IGNITE, true));
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (!ignite){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        int use = state.get(USE);
        if (stack.isOf(Items.REDSTONE) && (use == 0 || use == 2)){
            stack.decrement(1);
            world.setBlockState(pos, state.with(USE, ++use));
            world.playSound(null, pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS);
            return super.onUse(state, world, pos, player, hand, hit);
        }

        int break_count = state.get(BREAK);
        if (use == 1 && break_count == 0 || use == 3 && break_count == 1){
            world.setBlockState(pos, state.with(USE, ++use));
            world.playSound(null, pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundCategory.BLOCKS);
            return super.onUse(state, world, pos, player, hand, hit);
        }

        if (use == 4 && break_count == 16){
            world.setBlockState(pos.up(2), Blocks.NETHER_PORTAL.getDefaultState());
            world.setBlockState(pos, ModBlocks.UPDATE_SKIPPING_BLOCK.getDefaultState().with(IGNITE, false).with(HALF, false));
            world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS);
            SpawnUtil.spawnItemToPos(new ItemStack(Items.REDSTONE, 2), pos, world);
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (world.isClient()){
            super.onPlaced(world, pos, state, placer, itemStack);
            return;
        }

        world.setBlockState(pos, state.with(IGNITE, false));
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (world.isClient()){
            super.onBroken(world, pos, state);
            return;
        }
        super.onBroken(world, pos, state);

        int use = state.get(USE);
        int break_count = state.get(BREAK);
        int placed = state.get(PLACED);

        if (use < 2 || use == 3 || !state.get(IGNITE) || placed != 16 || (use == 2 && break_count >= 1)){
            SpawnUtil.spawnItemToPos(new ItemStack(Items.OBSIDIAN, placed), pos, (World) world);
            if (state.get(IGNITE)){
                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS);
            }

            SpawnUtil.spawnItemToPos(new ItemStack(ModBlockItems.UPDATE_SKIPPING_BLOCK_ITEM, 1), pos, (World) world);
            if (!state.get(HALF)){
                SpawnUtil.spawnItemToPos(new ItemStack(ModBlockItems.UPDATE_SKIPPING_BLOCK_ITEM, 1), pos, (World) world);
            }

            return;
        }

        if (break_count < 16){
            BlockState resetState = state.with(BREAK, ++break_count);
            ((World) world).setBlockState(pos, resetState);
            if (use == 2 || (use == 4 && break_count == 2) || break_count == 6 || break_count == 13){
                if (use == 2 || (use == 4 && break_count == 2)){
                    world.playSound(null, pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS);
                }
                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS);
            }
            SpawnUtil.spawnItemToPos(new ItemStack(Items.OBSIDIAN, 1), pos, (World) world);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PLACED);
        builder.add(BREAK);
        builder.add(USE);
        builder.add(IGNITE);
        builder.add(HALF);
        super.appendProperties(builder);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }
}
