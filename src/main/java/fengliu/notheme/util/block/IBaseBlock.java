package fengliu.notheme.util.block;

import fengliu.notheme.util.IdUtil;
import net.minecraft.util.Identifier;

/**
 * 方块注册
 */
public interface IBaseBlock {
    /**
     * 方块 ID 字符串
     * @return ID 字符串
     */
    String getBlockName();

    /**
     * 方块 ID
     * @return Identifier
     */
    default Identifier getId(){
        return IdUtil.get(this.getBlockName());
    }
}
