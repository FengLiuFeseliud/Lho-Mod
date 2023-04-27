package fengliu.notheme.item;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.util.item.BaseItem;
import fengliu.notheme.util.level.ICanUpgradeBlock;
import fengliu.notheme.util.level.ILevelBlock;
import fengliu.notheme.util.level.ILevelItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ExpandPassenger extends BaseItem {

    public ExpandPassenger(Settings settings, String id) {
        super(settings, id);
    }

    public enum ExpandPassengerLevels implements ILevelItem, ICanUpgradeBlock {
        Lv1(1),
        Lv2(2),
        Lv3(3),
        Lv4(4),
        Lv5(5),
        Lv6(6),
        Lv7(7),
        Lv8(8),
        Lv9(9);

        private final int level;
        private final int gain;

        ExpandPassengerLevels(int level){
            this.level = level;
            this.gain = 0;
        }

        @Override
        public boolean canUpgrade(Block block) {
            ILevelBlock level = ModBlocks.CUSTOMERS_BLOCKS.get(block);
            if (level == null){
                return false;
            }

            return level.getLevel() <= this.getLevel();
        }

        @Override
        public int getLevel() {
            return this.level;
        }

        @Override
        public int getMaxLevel() {
            return ExpandPassengerLevels.values().length;
        }

        @Override
        public int getGain() {
            return this.gain;
        }

        @Override
        public String getIdName() {
            return "expand_passenger";
        }

        @Override
        public Item getItem() {
            return new ExpandPassenger(new FabricItemSettings().maxCount(1), this.getPath());
        }
    }
}
