package fengliu.notheme.util.block;

import fengliu.notheme.util.IdUtil;
import net.minecraft.util.Identifier;

public interface IBaseBlock {

    String getBlockName();
    default Identifier getId(){
        return IdUtil.get(this.getBlockName());
    }
}
