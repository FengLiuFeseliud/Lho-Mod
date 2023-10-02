package fengliu.notheme.item.tool;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.color.IColor;
import fengliu.notheme.util.item.BaseItem;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import static net.minecraft.data.client.Models.GENERATED_THREE_LAYERS;

public class Brush extends BaseItem implements IColor {
    public final DyeColor dyeColor;

    public Brush(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.dyeColor = dyeColor;
    }

    @Override
    public DyeColor getColor() {
        return this.dyeColor;
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

    public static BlockState sprayBlock(BlockState blockState, DyeColor color){
        String blockPath = Registries.BLOCK.getId(blockState.getBlock()).getPath();
        for(DyeColor oldColor: DyeColor.values()){
            if (!blockPath.startsWith(oldColor.getName())){
                continue;
            }

            return Registries.BLOCK.get(new Identifier(blockPath.replace(oldColor.getName(), color.getName()))).getDefaultState();
        }
        return blockState;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        context.getWorld().setBlockState(context.getBlockPos(), Brush.sprayBlock(context.getWorld().getBlockState(context.getBlockPos()), this.getColor()));
        if (context.getWorld().isClient()){
            return super.useOnBlock(context);
        }

        ItemStack itemStack = context.getStack();
        itemStack.damage(1, context.getWorld().random, (ServerPlayerEntity) context.getPlayer());
        if (itemStack.getDamage() >= itemStack.getMaxDamage()){
            context.getPlayer().setStackInHand(context.getHand(), ModItems.EMPTY_BRUSH.getDefaultStack());
        }
        return super.useOnBlock(context);
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        return super.onStackClicked(stack, slot, clickType, player);
    }
}
