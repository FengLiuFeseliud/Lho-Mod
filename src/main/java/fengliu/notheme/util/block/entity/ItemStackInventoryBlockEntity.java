package fengliu.notheme.util.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

/**
 * 移动库存方块实体
 */
public abstract class ItemStackInventoryBlockEntity extends InventoryBlockEntity {

    public ItemStackInventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /**
     * 将库存转 Nbt 存入物品格
     * @param stack 存入的物品格
     * @return 物品格
     */
    public ItemStack writeInventoryToItemStack(ItemStack stack){
        this.setStackNbt(stack);
        if (this.hasCustomName()){
            stack.setCustomName(this.getCustomName());
        }

        stack.setDamage(this.getUseSize());
        return stack;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
    }
}
