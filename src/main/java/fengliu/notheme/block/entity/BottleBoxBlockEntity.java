package fengliu.notheme.block.entity;

import fengliu.notheme.item.inventory.block.bottle.Bottle;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.math.BlockPos;

public class BottleBoxBlockEntity extends ClothBagBlockEntity{
    public BottleBoxBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public boolean saveItemStack(ItemStack stack, PlayerEntity player) {
        Potion potion = PotionUtil.getPotion(Bottle.getPotion(stack));
        for(ItemStack slotStack: this.getItems()){
            if (slotStack.isEmpty()){
                continue;
            }

            if (potion.finishTranslationKey("").equals(PotionUtil.getPotion(Bottle.getPotion(slotStack)).finishTranslationKey(""))){
                return super.saveItemStack(stack, player);
            }
            return false;
        }
        return super.saveItemStack(stack, player);
    }
}
