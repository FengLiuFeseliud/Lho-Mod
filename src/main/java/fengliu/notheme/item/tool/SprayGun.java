package fengliu.notheme.item.tool;

import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.color.IColor;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.data.client.Models.GENERATED_THREE_LAYERS;

public class SprayGun extends EmptySprayGun implements IColor {
    public final DyeColor dyeColor;

    public SprayGun(Settings settings, DyeColor dyeColor, String textureName) {
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

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos().west().north();
        Map<BlockState, BlockState> sprayBlocks = new HashMap<>();

        for(int index = 1; index < 10; index++){
            BlockState oldBlock = context.getWorld().getBlockState(pos);
            if (!sprayBlocks.containsKey(oldBlock)){
                sprayBlocks.put(oldBlock, Brush.sprayBlock(context.getWorld().getBlockState(pos), this.getColor()));
            }

            context.getWorld().setBlockState(pos, sprayBlocks.get(oldBlock));
            pos = pos.east();
            if (index % 3 == 0){
                pos = pos.west(3).south();
            }
        }

        if (context.getWorld().isClient()){
            return super.useOnBlock(context);
        }

        ItemStack itemStack = context.getStack();
        itemStack.damage(1, context.getWorld().random, (ServerPlayerEntity) context.getPlayer());
        if (itemStack.getDamage() >= itemStack.getMaxDamage()){
            PlayerEntity player = context.getPlayer();

            player.setStackInHand(context.getHand(), ModItems.EMPTY_SPRAY_GUN.getDefaultStack());
            context.getWorld().playSound(player, player.getBlockPos(), SoundEvents.ENTITY_IRON_GOLEM_DAMAGE, SoundCategory.PLAYERS);
        }
        return super.useOnBlock(context);
    }
}
