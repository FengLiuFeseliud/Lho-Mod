package fengliu.notheme.block.inventory;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.block.entity.ClothBagBlockEntity;
import fengliu.notheme.item.ModFoodComponents;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.item.inventory.block.DrinkHolder;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.level.ILevelBlock;
import fengliu.notheme.util.level.LevelsUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class DrinkHolderBlock extends ClothBagBlock {
    public static final VoxelShape DRINK_HOLDER_BLOCK_SHAPE = VoxelShapes.cuboid(0, 0, 0, 1, 0.45, 1);

    public DrinkHolderBlock(Settings settings, int size) {
        super(settings, size);
    }

    @Override
    public boolean canPlaced(ItemStack stack) {
        return stack.getItem() instanceof PotionItem || stack.isEmpty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return DRINK_HOLDER_BLOCK_SHAPE;
    }

    @Override
    public BlockItem getItem() {
        return LevelsUtil.getBlockItem(ModBlockItems.DRINK_HOLDER_BLOCK_ITEMS, this);
    }

    @Override
    public ItemStack getItemStack() {
        return this.getItem().getDefaultStack();
    }

    @Override
    public IntProperty getInventoryProperty() {
        return IntProperty.of("inventory", 0, 15);
    }

    @Override
    public String getBlockName() {
        return ModBlocks.DRINK_HOLDER_BLOCKS.get(this).getIdName();
    }

    public enum DrinkHolderBlockLevels implements ILevelBlock {
        Lv1(1, 1, "drink_holder"),
        Lv2(2, 2, "double_deck_drink_holder"),
        Lv3(3, 3, "three_deck_drink_holder");

        public final int level;
        public final int gain;
        public final String name;

        DrinkHolderBlockLevels(int level, int gain, String name){
            this.level = level;
            this.gain = gain;
            this.name = name;
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getMaxLevel() {
            return DrinkHolderBlockLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getIdName() {
            return this.name;
        }

        @Override
        public String getPath() {
            return this.name;
        }

        @Override
        public Block getBlock() {
            return new DrinkHolderBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 5 * this.gain);
        }

        @Override
        public BlockItem getItem(Block block) {
            return new DrinkHolder((IBaseBlock) block, new FabricItemSettings().maxCount(1).maxDamageIfAbsent(5 * this.gain).food(ModFoodComponents.BENTO_BOX));
        }

        @Override
        public FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> getBlockEntityNew() {
            return ClothBagBlockEntity::new;
        }
    }
}
