package fengliu.notheme.item.tool;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.color.IColor;
import fengliu.notheme.util.item.BaseItem;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.List;

import static net.minecraft.data.client.Models.GENERATED_THREE_LAYERS;

public class ColorPicker extends EmptyColorPicker implements IColor {
    private final DyeColor color;

    public ColorPicker(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.color = dyeColor;
    }

    @Override
    public DyeColor getColor() {
        return this.color;
    }

    public Item getEmptyItem(){
        return ModItems.EMPTY_COLOR_PICKER;
    }

    @Override
    public void uploadModel(ItemModelGenerator itemModelGenerator) {
        GENERATED_THREE_LAYERS.upload(
                ModelIds.getItemModelId(this),
                TextureMap.layered(
                        IdUtil.get(this.getTextureName()+"/"+this.getTextureName()).withPrefixedPath(this.getPrefixedPath()),
                        IdUtil.get(this.getTextureName()+"/"+this.getTextureName()).withPrefixedPath(this.getPrefixedPath()),
                        IdUtil.get(this.getTextureName()+"/"+this.getTextureName()+"_overlay").withPrefixedPath(this.getPrefixedPath())
                ),
                itemModelGenerator.writer
        );
    }

    public Boolean takeColor(List<BaseItem> colorItem, Slot slot, PlayerEntity player){
        for(BaseItem item: colorItem){
            if (!((IColor) item).getColor().equals(this.getColor())){
                continue;
            }

            slot.getStack().decrement(1);
            player.getInventory().insertStack(item.getDefaultStack());
            return true;
        }
        return false;
    }

    public boolean damageColor(Slot slot, ItemStack stack, PlayerEntity player){
        int slotCount = slot.getStack().getCount();
        if (!(stack.getDamage() + slotCount <= stack.getMaxDamage())) {
            return false;
        }

        stack.setDamage(slotCount + stack.getDamage());
        if (stack.getDamage() >= stack.getMaxDamage()) {
            stack.decrement(1);
            player.getInventory().insertStack(this.getEmptyItem().getDefaultStack());
            player.world.playSound(player, player.getBlockPos(), SoundEvents.ENTITY_IRON_GOLEM_DAMAGE, SoundCategory.PLAYERS);
        }
        return true;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        ItemStack slotStack = slot.getStack();
        if ((slotStack.getItem() instanceof ColorPicker) && (stack.getItem() instanceof Brush || stack.getItem() instanceof SprayGun)){
            return false;
        }

        if (!(slotStack.getItem() instanceof ColorPicker) && !(stack.getItem() instanceof Brush || stack.getItem() instanceof SprayGun) && !(slotStack.getItem() instanceof WallGun)){
            if (slotStack.isOf(ModItems.EMPTY_SPRAY_GUN)){
                stack.setDamage(stack.getDamage()+1);
                return this.takeColor(ModItems.SPRAY_GUNS, slot, player);
            }

            if (slotStack.isOf(ModItems.EMPTY_BRUSH)){
                stack.setDamage(stack.getDamage()+1);
                return this.takeColor(ModItems.BRUSHS, slot, player);
            }

            if (slotStack.isOf(ModItems.EMPTY_WALL_GUN)){
                stack.setDamage(stack.getDamage()+1);
                return this.takeColor(ModItems.WALL_GUNS, slot, player);
            }
            return false;
        }

        if (!this.damageColor(slot, stack, player)){
            return false;
        }

        if (slotStack.isOf(ModItems.WATER_BALLOON)){
            for(Item item: ModItems.COLOR_WATER_BALLOONS){
                if (!this.getColor().equals(((IColor) item).getColor())){
                    continue;
                }

                player.getInventory().insertStack(new ItemStack(item, slotStack.getCount()));
                slotStack.decrement(slotStack.getCount());
            }
            return true;
        }

        Identifier itemId = Registries.ITEM.getId(slotStack.getItem());
        String itemPath = itemId.getPath();
        for (Item item : ModItems.COLOR_PICKERS) {
            String colorName = ((IColor) item).getColor().getName();
            if(!itemPath.contains(colorName)){
                continue;
            }

            int slotCount = slotStack.getCount();
            ItemStack newItem = new ItemStack(
                    Registries.ITEM.get(
                            new Identifier(
                                    itemId.getNamespace(),
                                    itemId.getPath().replace(colorName, this.getColor().getName())
                            )
                    ),
                    slotCount
            );

            if (!(newItem.getItem() instanceof Brush) && !(newItem.getItem() instanceof SprayGun) && !(newItem.getItem() instanceof WallGun)){
                newItem.setDamage(slotStack.getDamage());
            }

            slotStack.decrement(slotCount);
            player.getInventory().insertStack(newItem);
        }
        return false;
    }
}
