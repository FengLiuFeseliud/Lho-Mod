package fengliu.notheme.util.level;

import net.minecraft.block.Block;

/**
 * 升级方块物品
 */
public interface ICanUpgradeBlock {

    /**
     * 是否可以升级方块
     * @param block 方块
     * @return 可以 true
     */
    boolean canUpgrade(Block block);
}
