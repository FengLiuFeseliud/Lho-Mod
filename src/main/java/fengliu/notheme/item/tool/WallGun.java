package fengliu.notheme.item.tool;

import fengliu.notheme.entity.WallShellEntity;
import fengliu.notheme.item.ModItems;
import fengliu.notheme.util.IdUtil;
import fengliu.notheme.util.color.IColor;
import fengliu.notheme.util.item.BaseItem;
import net.minecraft.block.Block;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.world.World;

import java.util.List;

import static net.minecraft.data.client.Models.GENERATED_THREE_LAYERS;

public class WallGun extends EmptyWallGun implements IColor {
    private final DyeColor dyeColor;

    public WallGun(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings, dyeColor, textureName);
        this.dyeColor = dyeColor;
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
    public DyeColor getColor() {
        return this.dyeColor;
    }

    @Override
    public WallShellEntity getShellEntity(World world, PlayerEntity player, List<Block> wallBlocks){
        WallShellEntity wallShell = new WallShellEntity(player, world, wallBlocks);
        for(BaseItem item: ModItems.WALL_SHELLS){
            if (!((IColor) item).getColor().equals(this.dyeColor)){
                continue;
            }

            wallShell.setItem(item.getDefaultStack());
        }
        return wallShell;
    }
}
