package fengliu.notheme.item.food.ice.cream;

import fengliu.notheme.NoThemeMod;
import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.item.BaseItem;
import fengliu.notheme.util.level.ILevelItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class IceCreamBar extends BaseItem implements FabricItem {
    public static final String THAW_TIME_KEY = NoThemeMod.MOD_ID + ".thawTime";

    public IceCreamBar(Settings settings, String name) {
        super(settings, name);
    }

    public Map<Item, ILevelItem> getIceCreams(){
        return ModItems.ICE_CREAM_BARS;
    }

    public int getThawTime(){
        return ((IIceCreamLevel) this.getIceCreams().get(this)).getThawTime();
    }

    /**
     * 获取融化下一阶段冰淇淋
     * @param stack 冰淇淋
     * @return 下一阶段冰淇淋
     */
    public ItemStack getIceThawCreamItemStack(ItemStack stack){
        boolean nextIn = false;
        Map.Entry<Item, ILevelItem> iceCreamEnd = null;

        for (Map.Entry<Item, ILevelItem> iceCream: this.getIceCreams().entrySet()){
            if (nextIn){
                ItemStack iceCreamStack = iceCream.getKey().getDefaultStack();
                iceCreamStack.setDamage(iceCream.getValue().getLevel() - 1);
                return iceCreamStack;
            }

            if (stack.isOf(iceCream.getKey())){
                nextIn = true;
            }

            iceCreamEnd = iceCream;
        }

        if (iceCreamEnd == null){
            return ItemStack.EMPTY;
        }
        return ((IIceCreamLevel) iceCreamEnd.getValue()).getAllThawItemStack();
    }

    /**
     * 每 tick 在背包融化
     */
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player)){
            return;
        }

        NbtCompound nbt = stack.getOrCreateNbt();
        if (!nbt.contains(IceCreamBar.THAW_TIME_KEY, NbtElement.INT_TYPE)){
            nbt.putInt(IceCreamBar.THAW_TIME_KEY, ((IIceCreamLevel) this.getIceCreams().get(this)).getThawTime());
            return;
        }

        int thawTime = nbt.getInt(IceCreamBar.THAW_TIME_KEY);
        if (thawTime > 0){
            nbt.putInt(IceCreamBar.THAW_TIME_KEY, --thawTime);
            return;
        }

        player.getInventory().setStack(slot, this.thaw(stack, player));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setFireTicks(0);
        attacker.setStackInHand(attacker.getActiveHand(), this.getIceThawCreamItemStack(stack));
        return super.postHit(stack, target, attacker);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.setFireTicks(0);
        ItemStack iceCreamStack = this.getIceThawCreamItemStack(stack);
        super.finishUsing(stack, world, user);
        return iceCreamStack;
    }

    /**
     * 融化时调用
     * @param stack 冰淇淋
     * @param player 玩家
     * @return 新冰淇淋
     */
    public ItemStack thaw(ItemStack stack, PlayerEntity player){
        ItemStack iceCreamStack = this.getIceThawCreamItemStack(stack);
        stack.decrement(1);
        return iceCreamStack;
    }

    @Override
    public String getTextureName() {
        return ((IIceCreamLevel) this.getIceCreams().get(this)).getThawName();
    }

    @Override
    public String getPrefixedPath() {
        return super.getPrefixedPath() + this.getIceCreams().get(this).getIdName() + "/";
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains(THAW_TIME_KEY, NbtElement.INT_TYPE)){
            tooltip.add(Text.translatable(IdUtil.getItemInfo("ice_cream", 1), nbt.getInt(THAW_TIME_KEY) / 20));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    public enum IceCreamLevels implements IIceCreamLevel {
        NOT_THAW(1, 600, 3, "not_thaw"),
        THAW_HALF(2, 300, 2, "thaw_half"),
        THAW_MOST(3, 150, 1, "thaw_most");

        private final int level;
        private final int thawTime;
        private final int gain;
        private final String thawName;

        IceCreamLevels(int level, int thawTime, int gain, String thawName){
            this.thawTime = thawTime * 20;
            this.level = level;
            this.gain = gain;
            this.thawName = thawName;
        }

        @Override
        public FoodComponent getFoodComponent() {
            return new FoodComponent.Builder()
                .hunger(2 * this.gain).saturationModifier((float) (this.gain))
                .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100 * this.gain), 1.0f)
                .alwaysEdible().build();
        }

        @Override
        public int getThawTime() {
            return this.thawTime;
        }

        @Override
        public String getThawName() {
            return this.thawName;
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getMaxLevel() {
            return IceCreamLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getIdName() {
            return "ice_cream_bar";
        }

        @Override
        public Item getItem() {
            return new IceCreamBar(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getIdName());
        }
    }
}
