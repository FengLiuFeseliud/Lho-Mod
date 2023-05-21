package fengliu.notheme.block.entity;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.util.SpawnUtil;
import fengliu.notheme.util.block.ItemStackInventoryBlock;
import fengliu.notheme.util.block.entity.CraftBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class CraftingTableSlabBlockEntity extends CraftBlockEntity {
    private final CraftingSlabInventory inventory = new CraftingSlabInventory();
    private int choose;

    public CraftingTableSlabBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.CRAFTING_TABLE_SLAB_BLOCK_ENTITY, pos, state);

        this.setMaxItemStack(((ItemStackInventoryBlock) ModBlocks.CRAFTING_TABLE_SLAB_BLOCK).getSize());
    }

    public int choose(){
        if (choose == 10){
            this.choose = 0;
        } else {
            this.choose++;
        }
        return this.choose;
    }

    public void setMaterial(ItemStack stack){
        if (this.choose < 1 || this.choose > 9 || stack.isEmpty()){
            return;
        }

        int choose = this.choose - 1;
        if (!this.getItemStack(choose, InventoryAllocation.MATERIAL).isEmpty()){
            return;
        }

        ItemStack copyStack = stack.copy();
        this.setItemStack(choose, copyStack, InventoryAllocation.MATERIAL);
        this.inventory.setStack(choose, copyStack);
        stack.setCount(0);
        this.syncInventory();
    }

    public void delMaterial(){
        if (this.choose < 1 || this.choose > 10){
            return;
        }

        ItemStack stack;
        if (this.choose == 10){
            stack = this.getItemStack(0, InventoryAllocation.RESULT);
            if (!world.isClient()){
                Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, this.inventory, world);
                if (!optional.isPresent()){
                    return;
                }

                List<ItemStack> materials = this.getInventory(InventoryAllocation.MATERIAL);
                for(int index = 0; index < materials.size(); index++){
                    ItemStack materialStack = materials.get(index);
                    if (materialStack.isEmpty()){
                        continue;
                    }

                    ItemStack remainderStack = materialStack.getRecipeRemainder();
                    if (remainderStack.isEmpty()){
                        materialStack.decrement(1);
                        continue;
                    }

                    this.setItemStack(index, remainderStack, InventoryAllocation.MATERIAL);
                    this.inventory.setStack(index, remainderStack);
                }
            }
        } else {
            stack = this.getItemStack(this.choose - 1, InventoryAllocation.MATERIAL);
        }

        if (stack.isEmpty()){
            return;
        }

        SpawnUtil.spawnItemToPos(stack.copy(), this.pos, this.world);
        stack.setCount(0);
        this.syncInventory();
    }

    public static void tick(World world, BlockPos pos, BlockState state, CraftingTableSlabBlockEntity craftingTableSlab) {
        if (world.isClient()){
            return;
        }

        Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingTableSlab.inventory, world);
        if (!optional.isPresent()){
            if (craftingTableSlab.getItemStack(0, InventoryAllocation.RESULT).isEmpty()){
                return;
            }

            craftingTableSlab.setItemStack(0, ItemStack.EMPTY, InventoryAllocation.RESULT);
            craftingTableSlab.syncInventory();
            return;
        }

        ItemStack resultStack = optional.get().craft(craftingTableSlab.inventory, world.getRegistryManager());
        if (resultStack.isOf(craftingTableSlab.getItemStack(0, InventoryAllocation.RESULT).getItem())){
            return;
        }

        craftingTableSlab.setItemStack(0, resultStack, InventoryAllocation.RESULT);
        craftingTableSlab.syncInventory();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory.copy(this.getInventory(InventoryAllocation.MATERIAL));
    }

    @Override
    public Text getName() {
        return null;
    }

    protected static class CraftingSlabInventory extends CraftingInventory {
        private final DefaultedList<ItemStack> stacks;

        public CraftingSlabInventory() {
            super(null, 3, 3);
            this.stacks = DefaultedList.ofSize(9, ItemStack.EMPTY);
        }

        @Override
        public boolean isEmpty() {
            for (ItemStack itemStack : this.stacks) {
                if (itemStack.isEmpty()) continue;
                return false;
            }
            return true;
        }

        @Override
        public ItemStack getStack(int slot) {
            if (slot >= this.size()) {
                return ItemStack.EMPTY;
            }
            return this.stacks.get(slot);
        }

        @Override
        public ItemStack removeStack(int slot) {
            return Inventories.removeStack(this.stacks, slot);
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return Inventories.splitStack(this.stacks, slot, amount);
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            this.stacks.set(slot, stack);
        }

        @Override
        public void provideRecipeInputs(RecipeMatcher finder) {
            for (ItemStack itemStack : this.stacks) {
                finder.addUnenchantedInput(itemStack);
            }
        }

        public void copy(List<ItemStack> stacks){
            for (int index = 0; index < this.stacks.size(); index++){
                this.stacks.set(index, stacks.get(index));
            }
        }
    }

    public enum InventoryAllocation implements Allocation{
        MATERIAL(9),
        RESULT(1);

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
