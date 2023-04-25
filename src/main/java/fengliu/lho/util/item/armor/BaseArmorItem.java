package fengliu.lho.util.item.armor;

import fengliu.lho.util.IdUtil;
import fengliu.lho.util.item.armor.material.ExtendArmorMaterial;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseArmorItem extends ArmorItem {
    public final ExtendArmorMaterial material;
    public final String name;
    public final String tooltipKey;

    public BaseArmorItem(ExtendArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
        this.material = material;
        this.name = material.getName() + "_" + type.getName();
        this.tooltipKey = IdUtil.getItemTooltip(this.name);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(this.tooltipKey));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
