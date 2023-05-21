package fengliu.notheme.client.block.entity.renderer;

import fengliu.notheme.block.entity.ModBlockEntitys;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ModBlockEntityRenderers {

    public static void registerBlockEntityRenderers(){
        BlockEntityRendererRegistry.register(ModBlockEntitys.DISPENSING_MACHINE_BLOCK_ENTITY, DispensingMachineBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntitys.VENDING_MACHINE_BLOCK_ENTITY, VendingMachineBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntitys.CRAFTING_TABLE_SLAB_BLOCK_ENTITY, CraftingTableSlabBlockEntityRenderer::new);
    }
}
