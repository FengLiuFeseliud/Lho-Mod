package fengliu.notheme.client.block.entity.renderer;

import fengliu.notheme.block.entity.CraftingTableSlabBlockEntity;
import fengliu.notheme.block.mini.device.CraftingTableSlabBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

public class CraftingTableSlabBlockEntityRenderer implements BlockEntityRenderer<CraftingTableSlabBlockEntity> {

    public CraftingTableSlabBlockEntityRenderer(BlockEntityRendererFactory.Context ctx){

    }

    @Override
    public void render(CraftingTableSlabBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
        matrices.scale(0.15f, 0.15f, 0.15f);
        matrices.translate(1.87, -1.87, 1.68);

        Direction facing = entity.getCachedState().get(CraftingTableSlabBlock.FACING);
        if (facing == Direction.EAST){
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
            matrices.translate(-2.92, 0, 0);
        } else if (facing == Direction.WEST) {
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90));
            matrices.translate(0, 2.92, 0);
        } else if (facing == Direction.NORTH) {
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
            matrices.translate(-2.92, 2.92, 0);
        }

        int count = 0;
        for (ItemStack stack: entity.getInventory(CraftingTableSlabBlockEntity.InventoryAllocation.MATERIAL)){
            if (!(stack.getItem() instanceof BlockItem)){
                matrices.translate(0, 0, -0.53);
                itemRenderer.renderItem(stack, ModelTransformationMode.NONE, light, overlay, matrices, vertexConsumers, entity.getWorld(), 1);
                matrices.translate(0, 0, 0.53);
            } else {
                itemRenderer.renderItem(stack, ModelTransformationMode.NONE, light, overlay, matrices, vertexConsumers, entity.getWorld(), 1);
            }

            count++;
            if (count % 3 == 0){
                matrices.translate(-2.92, -1.46, 0);
            } else {
                matrices.translate(1.465, 0, 0);
            }
        }

        ItemStack resultStack = entity.getItemStack(0, CraftingTableSlabBlockEntity.InventoryAllocation.RESULT);
        if (!resultStack.isEmpty()){
            matrices.scale(1.3f, 1.3f, 1.3f);
            matrices.translate(4.33, 2.25, 0);
            if (!(resultStack.getItem() instanceof BlockItem)) {
                matrices.translate(0, 0, -0.38);
            }

            itemRenderer.renderItem(resultStack, ModelTransformationMode.NONE, light, overlay, matrices, vertexConsumers, entity.getWorld(), 1);
        }

        matrices.pop();
    }
}
