package fengliu.notheme.block.inventory.bottle;

import fengliu.notheme.block.entity.ClothBagBlockEntity;
import fengliu.notheme.block.inventory.ClothBagBlock;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.item.inventory.block.bottle.Bottle;
import fengliu.notheme.util.block.entity.InventoryBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.item.BlockItem.BLOCK_ENTITY_TAG_KEY;

public class BottleBlock extends ClothBagBlock {
    public static final VoxelShape BOTTLE_BLOCK_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 0.625, 1);

    public BottleBlock(Settings settings, int size) {
        super(settings, size);
    }

    @Override
    public boolean canSave(ItemStack stack, BlockState state) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(BLOCK_ENTITY_TAG_KEY)){
            return true;
        }

        return !nbt.getCompound(BLOCK_ENTITY_TAG_KEY).contains("Items");
    }

    @Override
    public boolean canTakeBlock(PlayerEntity player, ItemStack stack) {
        return false;
    }

    public boolean canPlaced(ItemStack stack){
        return stack.isOf(ModBlockItems.BOTTLE_BLOCK_ITEM) || stack.isEmpty();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof InventoryBlockEntity inventoryBlockEntity)){
            return;
        }

        inventoryBlockEntity.setStack(0, itemStack);
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof ClothBagBlockEntity clothBagBlock) || world.isClient()){
            return;
        }

        clothBagBlock.dropAllItemStack(pos, world);
    }

    @Override
    public BlockItem getItem() {
        return new Bottle(this, new FabricItemSettings().maxCount(1).maxDamageIfAbsent(this.getSize()));
    }

    @Override
    public ItemStack getItemStack() {
        return ModBlockItems.BOTTLE_BLOCK_ITEM.getDefaultStack();
    }

    @Override
    public IntProperty getInventoryProperty() {
        return IntProperty.of("inventory", 0, 16);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return BOTTLE_BLOCK_SHAPE;
    }

    @Override
    public String getBlockName() {
        return "bottle";
    }
}
