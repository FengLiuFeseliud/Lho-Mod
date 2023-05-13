package fengliu.notheme.block.entity;

import fengliu.notheme.util.block.ItemStackInventoryBlock;
import fengliu.notheme.util.block.entity.CraftBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class DispensingMachineBlockEntity extends CraftBlockEntity {
    public DispensingMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.DISPENSING_MACHINE_BLOCK_ENTITY, pos, state);

        this.setMaxItemStack(((ItemStackInventoryBlock) state.getBlock()).getSize());
    }

    @Override
    public Text getName() {
        return null;
    }

    public enum InventoryAllocation implements Allocation{
        SLOT_1(16),
        SLOT_2(16),
        CLAIM(2);

        private final int size;

        InventoryAllocation(int size){
            this.size = size;
        }

        @Override
        public int getSize() {
            return this.size;
        }

        @Override
        public Allocation[] getAllocations() {
            return InventoryAllocation.values();
        }
    }
}
