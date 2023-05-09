package fengliu.notheme.block.inventory;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.item.inventory.block.ReinforcedBag;
import fengliu.notheme.util.block.IBaseBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ReinforcedBagBlock extends ClothBagBlock {
    public ReinforcedBagBlock(Settings settings, int size) {
        super(settings, size);
    }

    @Override
    public BlockItem getItem() {
        return new ReinforcedBag((IBaseBlock) ModBlocks.REINFORCED_BAR_BLOCK, new FabricItemSettings().maxCount(1).maxDamageIfAbsent(this.getSize()));
    }

    @Override
    public boolean canPlaced(ItemStack stack) {
        Item item = stack.getItem();
        return !item.isFood() && !(item instanceof ToolItem) && !(item instanceof PotionItem);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item item = player.getStackInHand(hand).getItem();
        if (item.isFood() || item instanceof ToolItem || item instanceof PotionItem){
            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemStack(ModBlockItems.REINFORCED_BAR_BLOCK_ITEM, 1);
    }

    @Override
    public String getBlockName() {
        return "reinforced_bag";
    }
}
