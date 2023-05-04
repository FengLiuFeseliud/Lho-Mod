package fengliu.notheme.item.heart.drop;

import fengliu.notheme.util.level.ILevelItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class HeartAbsorptionDevice extends HeartDropDevice {
    public HeartAbsorptionDevice(Settings settings, String name, int gain) {
        super(settings, name, gain);
    }

    public enum HeartAbsorptionDeviceLevels implements ILevelItem {
        Lv1(1, 1),
        Lv2(2, 2),
        Lv3(3, 3);

        private final int level;
        private final int gain;

        HeartAbsorptionDeviceLevels(int level, int gain){
            this.level = level;
            this.gain = gain;
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getMaxLevel() {
            return HeartDropDeviceLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getIdName() {
            return "heart_absorption_device";
        }

        @Override
        public Item getItem() {
            return new HeartAbsorptionDevice(new FabricItemSettings().maxCount(1).maxDamageIfAbsent(90 * this.gain), this.getPath(), this.getGain());
        }
    }
}
