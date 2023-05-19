package fengliu.notheme.block;

import fengliu.notheme.block.heart.*;
import fengliu.notheme.block.inventory.*;
import fengliu.notheme.block.inventory.bottle.Bottle12BoxBlock;
import fengliu.notheme.block.inventory.bottle.BottleBlock;
import fengliu.notheme.block.inventory.bottle.BottleBoxBlock;
import fengliu.notheme.block.inventory.bottle.EmptyBottleBlock;
import fengliu.notheme.block.mini.device.BedrockBreakerBlock;
import fengliu.notheme.block.mini.device.UpdateSkippingBlock;
import fengliu.notheme.util.RegisterUtil;
import fengliu.notheme.util.block.BaseBlock;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.level.ILevelBlock;
import fengliu.notheme.util.level.LevelsUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;

import java.util.Map;

public class ModBlocks {
    public static final Block BLOOD_POOL_BLOCK = register(new BloodPoolBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().nonOpaque()));
    public static final Block CLOTH_BAG_BLOCK = register(new ClothBagBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 5));
    public static final Block REINFORCED_BAR_BLOCK = register(new ReinforcedBagBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 5));
    public static final Map<Block, ILevelBlock> BENTO_BOX_BLOCKS = LevelsUtil.registerBlocks(BentoBoxBlock.BentoBoxBlockLevel.values());
    public static final Map<Block, ILevelBlock> DRINK_HOLDER_BLOCKS = LevelsUtil.registerBlocks(DrinkHolderBlock.DrinkHolderBlockLevels.values());
    public static final Block BOTTLE_BLOCK = register(new BottleBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 16));
    public static final Block EMPTY_BOTTLE_BLOCK = register(new EmptyBottleBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 16));
    public static final Block BOTTLE_BOX_BLOCK = register(new BottleBoxBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 16));
    public static final Block BOTTLE_12_BOX_BLOCK = register(new Bottle12BoxBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 12));
    public static final Block DISPENSING_MACHINE_BLOCK = register(new DispensingMachineBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 34));
    public static final Block VENDING_MACHINE_BLOCK = register(new VendingMachineBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque().luminance(12), 118));
    public static final Block WITHER_BLOCK = register(new WitherBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "wither_block"));
    public static final Block LIFE_BLOCK = register(new LifeBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "life_block"));
    public static final Block ABSORPTION_BLOCK = register(new AbsorptionBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "absorption_block"));
    public static final Block FROST_BLOCK = register(new BaseBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "frost_block"));
    public static final Block POISON_BLOCK = register(new BaseBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "poison_block"));
    public static final Block ANIMAL_BLOCK = register(new BaseBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "animal_block"));
    public static final Block LONG_FOR_LIFE_BLOCK = register(new LongForLifeBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "long_for_life_block"));
    public static final Block BEDROCK_BREAKER_BLOCK = register(new BedrockBreakerBlock(FabricBlockSettings.of(Material.STONE).strength(0.5f, 1200).requiresTool().nonOpaque(), "bedrock_breaker"));
    public static final Block UPDATE_SKIPPING_BLOCK = register(new UpdateSkippingBlock(FabricBlockSettings.of(Material.STONE).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "update_skipping"));

    @Environment(EnvType.CLIENT)
    public static void setAllBlockRenderLayerMap(){
        BlockRenderLayerMap.INSTANCE.putBlock(BLOOD_POOL_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BOTTLE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(EMPTY_BOTTLE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BOTTLE_BOX_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(BOTTLE_12_BOX_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(DISPENSING_MACHINE_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(VENDING_MACHINE_BLOCK, RenderLayer.getTranslucent());
    };

    public static Block register(IBaseBlock block){
        return RegisterUtil.registerBlock(block);
    }

    public static void registerAllBlock(){}
}
