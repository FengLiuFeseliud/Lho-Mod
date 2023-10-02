package fengliu.notheme.item.food.ice.cream;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.color.IColor;
import fengliu.notheme.util.item.BaseItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.DyeColor;

public class IceCreamBarPack extends BaseItem implements IColor {
    private final DyeColor color;

    public IceCreamBarPack(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.color = dyeColor;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ItemStack slotStack = slot.getStack();
        if (slotStack.isEmpty()){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        Item item = slotStack.getItem();
        if (!(item instanceof IceCreamBar iceCream)){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        if (iceCream.getIceCreams().keySet().stream().toList().indexOf(iceCream) != 0){
            return super.onStackClicked(stack, slot, clickType, player);
        }

        for(Item packItem: ModItems.PACK_ICE_CREAM_BARS) {
            if (((IColor) packItem).getColor().getId() != this.color.getId()) {
                continue;
            }

            ItemStack packIceCreamStack = packItem.getDefaultStack();
            packIceCreamStack.getOrCreateNbt().put(PackIceCream.PACK_ICE_CREAM_KEY, slotStack.writeNbt(new NbtCompound()));

            stack.decrement(1);
            slotStack.decrement(1);

            slot.setStack(packIceCreamStack);
        }
        return super.onStackClicked(stack, slot, clickType, player);
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }
}
