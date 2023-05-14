package fengliu.notheme.item.food.ice.cream;

import fengliu.notheme.NoThemeMod;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.color.IColor;
import fengliu.notheme.util.item.BaseItem;
import fengliu.notheme.util.item.ITickUpdate;
import fengliu.notheme.util.level.ILevelItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class PackIceCream extends BaseItem implements ITickUpdate, IColor {
    public static final String ICE_CREAM_PACK_TIME_KEY = NoThemeMod.MOD_ID + ".iceCreamPackTime";
    public static final String PACK_ICE_CREAM_KEY = NoThemeMod.MOD_ID + ".packIceCream";
    private final DyeColor color;
    private final String textureName;

    public PackIceCream(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.color = dyeColor;
        this.textureName = textureName;
    }

    public ItemStack getIceCreamItemStack(ItemStack iceCreamPack){
        NbtCompound nbt = iceCreamPack.getOrCreateNbt();
        if (!nbt.contains(PACK_ICE_CREAM_KEY, NbtElement.COMPOUND_TYPE)){
            return iceCreamPack;
        }

        ItemStack iceCreamItemStack = ItemStack.fromNbt(nbt.getCompound(PACK_ICE_CREAM_KEY));
        if (!(iceCreamItemStack.getItem() instanceof IceCreamBar iceCreamItem)){
            return iceCreamPack;
        }

        int iceCreamPackTime = 0;
        if (nbt.contains(ICE_CREAM_PACK_TIME_KEY, NbtElement.INT_TYPE)){
            iceCreamPackTime = nbt.getInt(ICE_CREAM_PACK_TIME_KEY);
        }

        NbtCompound iceCreamNbt = iceCreamItemStack.getOrCreateNbt();
        if (iceCreamNbt.contains(IceCreamBar.THAW_TIME_KEY, NbtElement.INT_TYPE)){
            iceCreamPackTime += ((IceCreamBar) iceCreamItemStack.getItem()).getThawTime() - iceCreamNbt.getInt(IceCreamBar.THAW_TIME_KEY);
        }

        int allThawTime = 0;
        for (Map.Entry<Item, ILevelItem> iceCream: iceCreamItem.getIceCreams().entrySet()){
            allThawTime += ((IIceCreamLevel) iceCream.getValue()).getThawTime();
            if (iceCreamPackTime > allThawTime){
                continue;
            }

            ItemStack PackIceCreamItemStack = iceCream.getKey().getDefaultStack();
            PackIceCreamItemStack.setDamage(iceCream.getValue().getLevel());
            PackIceCreamItemStack.getOrCreateNbt().putInt(IceCreamBar.THAW_TIME_KEY, allThawTime - iceCreamPackTime);
            return PackIceCreamItemStack;
        }
        return ((IIceCreamLevel) iceCreamItem.getIceCreams().get(iceCreamItem)).getAllThawItemStack();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setStackInHand(hand, this.getIceCreamItemStack(user.getStackInHand(hand)));
        return super.use(world, user, hand);
    }

    @Override
    public void update(ItemStack stack, PlayerEntity player) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(PACK_ICE_CREAM_KEY, NbtElement.COMPOUND_TYPE)){
            return;
        }

        if (!nbt.contains(ICE_CREAM_PACK_TIME_KEY, NbtElement.INT_TYPE)){
            nbt.putInt(ICE_CREAM_PACK_TIME_KEY, 0);
            return;
        }

        nbt.putInt(ICE_CREAM_PACK_TIME_KEY, nbt.getInt(ICE_CREAM_PACK_TIME_KEY) + 1);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains(ICE_CREAM_PACK_TIME_KEY, NbtElement.INT_TYPE)){
            tooltip.add(Text.translatable(IdUtil.getItemInfo("pack_ice_cream", 1), nbt.getInt(ICE_CREAM_PACK_TIME_KEY) / 20));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }

    @Override
    public String getTextureName() {
        return this.textureName;
    }

}
