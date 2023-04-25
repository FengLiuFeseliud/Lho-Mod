package fengliu.lho.util.level;

import fengliu.lho.item.block.BaseBlockItem;
import fengliu.lho.util.IdUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;

/**
 * 等级方块项
 */
public interface ILevelBlock extends ILevel {
    /**
     * 实例等级方块
     * @return 等级方块
     */
    Block getBlock();

    /**
     * 实例等级方块物品
     * @param block 等级方块
     * @return 等级方块物品
     */
    default BlockItem getItem(Block block){
        return new BaseBlockItem(block, this.getPath());
    }

    /**
     * 获取等级方块实体 Factory
     * @return 等级方块实体 Factory
     */
    FabricBlockEntityTypeBuilder.Factory<? extends BlockEntity> getBlockEntityNew();
}
