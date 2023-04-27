package fengliu.notheme.item.internet;

import fengliu.notheme.NoThemeMod;
import fengliu.notheme.screen.handler.PhoneScreenHandler;
import fengliu.notheme.util.item.BaseItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Phone extends BaseItem implements NamedScreenHandlerFactory {
    public Phone(Settings settings, String name) {
        super(settings, name);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        NoThemeMod.LOGGER.info(world.getBlockState(new BlockPos(10000, 100, 10000)).toString());
        return super.use(world, user, hand);
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Phone");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new PhoneScreenHandler(syncId, player.getActiveItem(), playerInventory);
    }
}
