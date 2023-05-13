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
    public static final Map<Block, ILevelBlock> CUSTOMERS_BLOCKS = LevelsUtil.getBlocks(CustomersBlock.CustomersBlockLevels.values());
    public static final Block BLOOD_POOL_BLOCK = new BloodPoolBlock(FabricBlockSettings.of(Material.STONE).strength(5.0f).requiresTool().nonOpaque());
    public static final Block CLOTH_BAG_BLOCK = new ClothBagBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 5);
    public static final Block REINFORCED_BAR_BLOCK = new ReinforcedBagBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 5);
    public static final Map<Block, ILevelBlock> BENTO_BOX_BLOCKS = LevelsUtil.getBlocks(BentoBoxBlock.BentoBoxBlockLevel.values());
    public static final Map<Block, ILevelBlock> DRINK_HOLDER_BLOCKS = LevelsUtil.getBlocks(DrinkHolderBlock.DrinkHolderBlockLevels.values());
    public static final Block BOTTLE_BLOCK = new BottleBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 16);
    public static final Block EMPTY_BOTTLE_BLOCK = new EmptyBottleBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 16);
    public static final Block BOTTLE_BOX_BLOCK = new BottleBoxBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 16);
    public static final Block BOTTLE_12_BOX_BLOCK = new Bottle12BoxBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 12);
    public static final Block DISPENSING_MACHINE_BLOCK = new DispensingMachineBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque(), 34);
    public static final Block VENDING_MACHINE_BLOCK = new VendingMachineBlock(FabricBlockSettings.of(Material.WOOL).strength(0.2f, 0.2f).requiresTool().nonOpaque().luminance(12), 118);
    public static final BaseBlock WITHER_BLOCK = new WitherBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "wither_block");
    public static final BaseBlock LIFE_BLOCK = new LifeBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "life_block");
    public static final BaseBlock ABSORPTION_BLOCK = new AbsorptionBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "absorption_block");
    public static final BaseBlock FROST_BLOCK = new BaseBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "frost_block");
    public static final BaseBlock POISON_BLOCK = new BaseBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "poison_block");
    public static final BaseBlock ANIMAL_BLOCK = new BaseBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "animal_block");
    public static final BaseBlock LONG_FOR_LIFE_BLOCK = new LongForLifeBlock(FabricBlockSettings.of(Material.SOIL).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "long_for_life_block");
    public static final Block BEDROCK_BREAKER_BLOCK = new BedrockBreakerBlock(FabricBlockSettings.of(Material.STONE).strength(0.5f, 1200).requiresTool().nonOpaque(), "bedrock_breaker");
    public static final Block UPDATE_SKIPPING_BLOCK = new UpdateSkippingBlock(FabricBlockSettings.of(Material.STONE).strength(0.5f, 0.5f).requiresTool().nonOpaque(), "update_skipping");

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

    public static final BaseBlock[] HEART_GROUP_BLOCK = new BaseBlock[]{
        WITHER_BLOCK,
        LIFE_BLOCK,
        ABSORPTION_BLOCK,
        FROST_BLOCK,
        ANIMAL_BLOCK,
        POISON_BLOCK,
        LONG_FOR_LIFE_BLOCK
    };

    public static final BaseBlock[] MINI_DEVICE_GROUP_BLOCK = new BaseBlock[]{

    };

    public static void registerAllBlock(){
        LevelsUtil.registerAllBlock(CUSTOMERS_BLOCKS);

        RegisterUtil.registerBlock((IBaseBlock) BLOOD_POOL_BLOCK);
        RegisterUtil.registerBlock((IBaseBlock) CLOTH_BAG_BLOCK);
        RegisterUtil.registerBlock((IBaseBlock) REINFORCED_BAR_BLOCK);
        RegisterUtil.registerBlock((IBaseBlock) BOTTLE_BLOCK);
        RegisterUtil.registerBlock((IBaseBlock) EMPTY_BOTTLE_BLOCK);
        RegisterUtil.registerBlock((IBaseBlock) BOTTLE_BOX_BLOCK);
        RegisterUtil.registerBlock((IBaseBlock) BOTTLE_12_BOX_BLOCK);
        RegisterUtil.registerBlock((IBaseBlock) DISPENSING_MACHINE_BLOCK);
        RegisterUtil.registerBlock((IBaseBlock) VENDING_MACHINE_BLOCK);

        LevelsUtil.registerAllBlock(BENTO_BOX_BLOCKS);
        LevelsUtil.registerAllBlock(DRINK_HOLDER_BLOCKS);

        RegisterUtil.registerBlocks(HEART_GROUP_BLOCK);
        RegisterUtil.registerBlocks(MINI_DEVICE_GROUP_BLOCK);

        RegisterUtil.registerBlock((IBaseBlock) BEDROCK_BREAKER_BLOCK);
        RegisterUtil.registerBlock((IBaseBlock) UPDATE_SKIPPING_BLOCK);
    }
}
