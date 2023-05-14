package fengliu.notheme.item.food.ice.cream;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.level.ILevelItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ChocolateCrustIceCreamBar extends IceCreamBar {
    public ChocolateCrustIceCreamBar(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public Map<Item, ILevelItem> getIceCreams() {
        return ModItems.CHOCOLATE_CRUST_ICE_CREAM_BARS;
    }

    public enum IceCreamLevels implements IIceCreamLevel {
        NOT_THAW(1, 600, 4, "not_thaw"),
        THAW_HALF(2, 300, 3, "thaw_half"),
        THAW_MOST(3, 150, 2, "thaw_most"),
        THAW_ALMOST_ALL(4, 100, 1, "thaw_almost_all");

        private final int level;
        private final int thawTime;
        private final int gain;
        private final String thawName;

        IceCreamLevels(int level, int thawTime, int gain, String thawName) {
            this.thawTime = thawTime * 20;
            this.level = level;
            this.gain = gain;
            this.thawName = thawName;
        }

        @Override
        public ItemStack getAllThawItemStack() {
            return ModItems.BAR.getDefaultStack();
        }

        @Override
        public FoodComponent getFoodComponent() {
            return new FoodComponent.Builder()
                .hunger((int) (1.5 * this.gain)).saturationModifier((float) (this.gain))
                .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 80 * this.gain), 1.0f)
                .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 5 * this.gain), 0.25f)
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
            return ChocolateCrustIceCreamBar.IceCreamLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getIdName() {
            return "chocolate_crust_ice_cream_bar";
        }

        @Override
        public String getPath() {
            return this.getIdName() + "_" + this.thawName;
        }

        @Override
        public Item getItem() {
            return new ChocolateCrustIceCreamBar(new FabricItemSettings().maxCount(1).maxDamage(this.getMaxLevel()).food(this.getFoodComponent()), this.getIdName());
        }
    }
}
