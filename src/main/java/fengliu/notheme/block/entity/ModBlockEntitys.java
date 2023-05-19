package fengliu.notheme.block.entity;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.util.RegisterUtil;
import fengliu.notheme.util.block.IBaseBlock;
import fengliu.notheme.util.level.ILevelBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.Map;

public class ModBlockEntitys {
    public static Map<ILevelBlock, BlockEntityType<?>> CustomersBlockEntityTypes;
    public static BlockEntityType<?> BLOOD_POOL_BLOCK_ENTITY;
    public static BlockEntityType<?> CLOTH_BAG_BLOCK_ENTITY;
    public static BlockEntityType<?> BOTTLE_BOX_BLOCK_ENTITY;
    public static BlockEntityType<?> BEDROCK_BREAKER_BLOCK_ENTITY;
    public static BlockEntityType<DispensingMachineBlockEntity> DISPENSING_MACHINE_BLOCK_ENTITY;
    public static BlockEntityType<VendingMachineBlockEntity> VENDING_MACHINE_BLOCK_ENTITY;

    public static void registerAllBlockEntity(){
        BLOOD_POOL_BLOCK_ENTITY = RegisterUtil.registerBlockEntity((IBaseBlock) ModBlocks.BLOOD_POOL_BLOCK, BloodPoolBlockEntity::new);
        CLOTH_BAG_BLOCK_ENTITY = RegisterUtil.registerBlockEntity((IBaseBlock) ModBlocks.CLOTH_BAG_BLOCK, ClothBagBlockEntity::new);
        BOTTLE_BOX_BLOCK_ENTITY = RegisterUtil.registerBlockEntity((IBaseBlock) ModBlocks.BOTTLE_BOX_BLOCK, BottleBoxBlockEntity::new);
        BEDROCK_BREAKER_BLOCK_ENTITY = RegisterUtil.registerBlockEntity((IBaseBlock) ModBlocks.BEDROCK_BREAKER_BLOCK, BedrockBreakerBlockEntity::new);
        DISPENSING_MACHINE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, ((IBaseBlock) ModBlocks.DISPENSING_MACHINE_BLOCK).getId(),  FabricBlockEntityTypeBuilder.create(DispensingMachineBlockEntity::new, ModBlocks.DISPENSING_MACHINE_BLOCK).build(null));
        VENDING_MACHINE_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, ((IBaseBlock) ModBlocks.VENDING_MACHINE_BLOCK).getId(),  FabricBlockEntityTypeBuilder.create(VendingMachineBlockEntity::new, ModBlocks.VENDING_MACHINE_BLOCK).build(null));
    }
}
