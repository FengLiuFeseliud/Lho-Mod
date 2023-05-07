package fengliu.notheme.item.mini.device;

import fengliu.notheme.item.block.BaseBlockItem;
import fengliu.notheme.item.block.ModBlockItems;
import fengliu.notheme.util.block.IBaseBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BedrockBreaker extends BaseBlockItem {
    public BedrockBreaker(IBaseBlock block, Settings settings) {
        super(block, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (stack.getDamage() == 0){
            return super.use(world, user, hand);
        }

        PlayerInventory inventory = user.getInventory();

        ItemStack tntStack = null;
        for(ItemStack itemStack: inventory.main){
            if (!itemStack.isOf(Items.TNT)){
                continue;
            }

            tntStack = itemStack;
        }

        if (tntStack == null){
            return super.use(world, user, hand);
        }

        if (!(tntStack.getCount() >= 2)){
            return super.use(world, user, hand);
        }

        tntStack.decrement(2);
        stack.setDamage(stack.getDamage()-1);
        return super.use(world, user, hand);
    }
}
