package fengliu.notheme.block.inventory;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.item.ModFoodComponents;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.item.inventory.block.BentoBox;
import fengliu.notheme.item.inventory.block.DrinkHolder;
import fengliu.notheme.util.block.IBaseBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class DrinkHolderBlock extends ClothBagBlock {
    public static final VoxelShape DRINK_HOLDER_BLOCK_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 0.45, 1);

    public DrinkHolderBlock(Settings settings, int size) {
        super(settings, size);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (!(stack.getItem() instanceof PotionItem) && !stack.isEmpty()){
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return DRINK_HOLDER_BLOCK_SHAPE;
    }

    @Override
    public BlockItem getItem() {
        return new DrinkHolder((IBaseBlock) ModBlocks.DRINK_HOLDER_BLOCK, new FabricItemSettings().maxCount(1).maxDamageIfAbsent(this.getSize()).food(ModFoodComponents.BENTO_BOX));
    }

    @Override
    public ItemStack getItemStack() {
        return ModBlockItems.DRINK_HOLDER_BLOCK_ITEM.getDefaultStack();
    }

    @Override
    public IntProperty getInventoryProperty() {
        return IntProperty.of("inventory", 0, 5);
    }

    @Override
    public String getBlockName() {
        return "drink_holder";
    }
}
