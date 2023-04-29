package fengliu.notheme.util.block;

import net.minecraft.block.Block;

public class BaseBlock extends Block implements IBaseBlock {
    public final String name;
    public BaseBlock(Settings settings, String name) {
        super(settings);
        this.name = name;
    }

    @Override
    public String getBlockName() {
        return this.name;
    }
}
