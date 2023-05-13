package fengliu.notheme.block.inventory;

import fengliu.notheme.block.entity.DispensingMachineBlockEntity;
import fengliu.notheme.item.block.BaseBlockItem;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static fengliu.notheme.block.entity.DispensingMachineBlockEntity.InventoryAllocation.SLOT_1;
import static fengliu.notheme.block.entity.DispensingMachineBlockEntity.InventoryAllocation.SLOT_2;

public class DispensingMachineBlock extends ItemStackInventoryBlock implements IBaseBlock {
    /**
     * 移动库存方块
     *
     * @param settings 方块属性
     * @param size     库存大小
     */
    public DispensingMachineBlock(Settings settings, int size) {
        super(settings, size);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof DispensingMachineBlockEntity dispensingMachine)){
            return super.onUse(state, world, pos, player, hand, hit);
        }

        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.POTION)){
            if (dispensingMachine.getItemStack(0, SLOT_1).isEmpty()){
                dispensingMachine.setItemStack(0, stack.copy(), SLOT_1);
            } else if (dispensingMachine.getItemStack(0, SLOT_2).isEmpty()){
                dispensingMachine.setItemStack(0, stack.copy(), SLOT_2);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public BlockItem getItem() {
        return new BaseBlockItem(this, new FabricItemSettings().maxCount(1));
    }

    @Override
    public ItemStack getItemStack() {
        return ModBlockItems.DISPENSING_MACHINE_BLOCK_ITEM.getDefaultStack();
    }

    @Override
    public IntProperty getInventoryProperty() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DispensingMachineBlockEntity(pos, state);
    }

    @Override
    public String getBlockName() {
        return "dispensing_machine";
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
