package fengliu.notheme.util.item;

import fengliu.notheme.criterion.ModCriteria;
import fengliu.notheme.util.IdUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.ModelIds;
import net.minecraft.data.client.TextureMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.data.client.Models.GENERATED;

public class BaseItem extends Item {
    public static final String PREFIXED_PATH = "item/";
    public final String name;
    public final String tooltipKey;
    public final String textureName;

    public BaseItem(Settings settings, String name) {
        super(settings);
        this.name = name;
        this.textureName = name;
        this.tooltipKey = IdUtil.getItemTooltip(name);
    }

    public BaseItem(String name) {
        this(new Settings().maxCount(64), name);
    }

    public BaseItem(Settings settings, DyeColor dyeColor, String textureName) {
        super(settings);
        this.name = dyeColor.getName() + "_" + textureName;
        this.textureName = textureName;
        this.tooltipKey = IdUtil.getItemTooltip(textureName);
    }

    public BaseItem(DyeColor dyeColor, String textureName) {
        this(new Settings().maxCount(64), dyeColor, textureName);
    }

    /**
     * 获取纹理名 (用于生成)
     * @return 纹理名
     */
    public String getTextureName(){
        return this.textureName;
    }

    /**
     * 获取纹理路径 (用于生成模型)
     * @return 纹理路径
     */
    public String getPrefixedPath(){
        return PREFIXED_PATH;
    }

    public void uploadModel(ItemModelGenerator itemModelGenerator){
        GENERATED.upload(ModelIds.getItemModelId(this), TextureMap.layer0(IdUtil.get(this.getTextureName()).withPrefixedPath(this.getPrefixedPath())), itemModelGenerator.writer);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()){
            return super.use(world, user, hand);
        }

        ModCriteria.MOD_ITEM_USE.trigger((ServerPlayerEntity) user, user.getBlockPos(), user.getStackInHand(hand));
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable(this.tooltipKey));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
