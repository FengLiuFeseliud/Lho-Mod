package fengliu.notheme.block.entity;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.screen.handler.CustomersBlockScreenHandler;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.level.ILevelBlock;
import fengliu.notheme.util.block.entity.ScreenBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


public class CustomersBlockEntity extends ScreenBlockEntity {
    private final ILevelBlock levelBlock;
    private Block pavementBlock;
    public int width = 0;
    public int height = 0;

    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            if (index == 0) {
                return width;
            }

            if (index == 1) {
                return height;
            }

            if (index == 2){
                if (pavementBlock == null){
                    return 1;
                }else {
                    return 0;
                }
            }

            return 0;
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int size() {
            return 3;
        }
    };

    public CustomersBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntitys.CustomersBlockEntityTypes.get(ModBlocks.CUSTOMERS_BLOCKS.get(state.getBlock())), pos, state);

        this.levelBlock = ModBlocks.CUSTOMERS_BLOCKS.get(state.getBlock());
        this.setMaxItemStack(1);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity be){
        Item item = ((CustomersBlockEntity) be).getItems().get(0).getItem();
        if (!(item instanceof BlockItem)){
            ((CustomersBlockEntity) be).setPavementBlock(null);
            return;
        }

        ((CustomersBlockEntity) be).setPavementBlock((BlockItem) item);
        Block pavementBlock = ((CustomersBlockEntity) be).getPavementBlock();

        BlockState blockState;
        BlockPos pos1 = pos;
        for (int index = 0; index < 3; index++){
            pos1 = pos1.east();

            blockState = world.getBlockState(pos1);
            if (!blockState.isOf(pavementBlock) && !blockState.isOf(state.getBlock())) {
                pos1 = pos1.west();
                break;
            }
        }

        for (int index = 0; index < 3; index++){
            pos1 = pos1.north();

            blockState = world.getBlockState(pos1);
            if (!blockState.isOf(pavementBlock) && !blockState.isOf(state.getBlock())){
                pos1 = pos1.south();
                break;
            }
        }

        BlockPos pos2 = pos;
        for (int index = 0; index < 3; index++){
            pos2 = pos2.west();

            blockState = world.getBlockState(pos2);
            if (!blockState.isOf(pavementBlock) && !blockState.isOf(state.getBlock())){
                pos2 = pos2.east();
                break;
            }
        }

        for (int index = 0; index < 3; index++){
            pos2 = pos2.south();

            blockState = world.getBlockState(pos2);
            if (!blockState.isOf(pavementBlock) && !blockState.isOf(state.getBlock())){
                pos2 = pos2.north();
                break;
            }
        }

        ((CustomersBlockEntity) be).width = pos2.getZ() - pos1.getZ() + 1;
        ((CustomersBlockEntity) be).height = pos1.getX() - pos2.getX() + 1;

        for (int indexH = 0; indexH < ((CustomersBlockEntity) be).height; indexH++){
            for (int indexw = 0; indexw < ((CustomersBlockEntity) be).width; indexw++){
                blockState = world.getBlockState(pos2.north(indexw));
                if (blockState.isOf(pavementBlock) || blockState.isOf(state.getBlock())){
                    continue;
                }

                if (((CustomersBlockEntity) be).width > indexw){
                    ((CustomersBlockEntity) be).width = indexw;
                }
            }

            blockState = world.getBlockState(pos2.east(indexH));
            if (blockState.isOf(pavementBlock) || blockState.isOf(state.getBlock())){
                continue;
            }

            ((CustomersBlockEntity) be).height = indexH;
            break;
        }

    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(IdUtil.getDisplayName(levelBlock.getPath()));
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CustomersBlockScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }

    public Block getPavementBlock(){
        return this.pavementBlock;
    }

    public void setPavementBlock(BlockItem blockItem){
        if (blockItem == null){
            this.pavementBlock = null;
            return;
        }

        this.pavementBlock = blockItem.getBlock();
    }
}
