package fengliu.notheme.client.block.entity.renderer;

import fengliu.notheme.block.entity.DispensingMachineBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import static fengliu.notheme.block.entity.DispensingMachineBlockEntity.InventoryAllocation.SLOT_1;
import static fengliu.notheme.block.entity.DispensingMachineBlockEntity.InventoryAllocation.SLOT_2;

@Environment(EnvType.CLIENT)
public class DispensingMachineBlockEntityRenderer implements BlockEntityRenderer<DispensingMachineBlockEntity> {
    private final ModelData modelData = new ModelData();

    public DispensingMachineBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(DispensingMachineBlockEntity be, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        matrices.push();
        matrices.translate(0.71, 0.82, 0.17);
        matrices.scale(0.25f, 0.25f, 0.25f);

        ItemStack stack = be.getItemStack(0, SLOT_1);
        if (!stack.isEmpty()){
            itemRenderer.renderItem(stack, ModelTransformationMode.GUI, light, overlay, matrices, vertexConsumers, null, 1);
        }

        matrices.translate(-1.75, 0, 0);
        stack = be.getItemStack(0, SLOT_2);
        if (!stack.isEmpty()){
            itemRenderer.renderItem(stack, ModelTransformationMode.GUI, light, overlay, matrices, vertexConsumers, null, 1);
        }
        matrices.pop();
    }
}
