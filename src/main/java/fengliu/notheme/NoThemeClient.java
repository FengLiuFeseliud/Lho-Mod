package fengliu.notheme;

import fengliu.notheme.block.ModBlocks;
import fengliu.notheme.client.block.entity.renderer.ModBlockEntityRenderers;
import fengliu.notheme.entity.ModEntity;
import fengliu.notheme.networking.packets.server.ModServerMessage;
import fengliu.notheme.screen.ModScreens;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

@Environment(EnvType.CLIENT)
public class NoThemeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModBlocks.registerAllBlock();
        ModBlocks.setAllBlockRenderLayerMap();
        ModScreens.registerAllScreen();
        ModServerMessage.registerS2CPackets();
        ModBlockEntityRenderers.registerBlockEntityRenderers();

        EntityRendererRegistry.register(ModEntity.COLOR_WATER_BALLOON_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntity.WATER_BALLOON_ENTITY_TYPE, FlyingItemEntityRenderer::new);
    }
}
