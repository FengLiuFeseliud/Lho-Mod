package fengliu.notheme.util.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface ITickUpdate {

    void update(ItemStack stack, PlayerEntity player);
}
