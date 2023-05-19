package fengliu.notheme.item.heart.fake;

import fengliu.notheme.item.heart.EmptyHeart;
import fengliu.notheme.util.item.BaseItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FakeHeart extends EmptyHeart {
    public final BaseItem heart;

    public FakeHeart(BaseItem heart) {
        super("fake_" + heart.name);
        this.heart = heart;
    }

    /**
     * 返回需要伪装物品
     * @return 伪装物品
     */
    public BaseItem fakeItem(){
        return this.heart;
    }

    @Override
    public String getTextureName() {
        return this.fakeItem().getTextureName();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        // 伪装物品文字
        tooltip.clear();
        tooltip.add(this.fakeItem().getName());
        this.fakeItem().appendTooltip(stack, world, tooltip, context);
    }
}
