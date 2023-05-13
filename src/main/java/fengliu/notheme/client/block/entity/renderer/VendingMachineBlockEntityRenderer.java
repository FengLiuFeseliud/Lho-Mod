package fengliu.notheme.client.block.entity.renderer;

import fengliu.notheme.block.entity.VendingMachineBlockEntity;
import fengliu.notheme.block.inventory.VendingMachineBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class VendingMachineBlockEntityRenderer implements BlockEntityRenderer<VendingMachineBlockEntity> {

    public VendingMachineBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }
    @Override
    public void render(VendingMachineBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        matrices.push();

        switch (entity.getCachedState().get(VendingMachineBlock.FACING)){
            case NORTH -> {
                matrices.multiply(RotationAxis.POSITIVE_Z.rotation(0));
            }
            case SOUTH -> {
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
                matrices.translate(-1, 0, -1);
            }
            case WEST -> {
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                matrices.translate(-1, 0, 0);
            }
            case EAST -> {
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(270));
                matrices.translate(0, 0, -1);
            }
        }

        matrices.scale(0.25f, 0.25f, 0.25f);
        matrices.translate(2.5, 6.8, 1);
        int count = 0;

        ItemStack[] stacks = entity.getCommodity();
        for (ItemStack stack: stacks){
            itemRenderer.renderItem(stack, ModelTransformationMode.GUI, light, overlay, matrices, vertexConsumers, entity.getWorld(), 1);

            count++;
            if (count % 3 == 0){
                matrices.translate(2.6, -1.7, 0);
            } else {
                matrices.translate(-1.3, 0, 0);
            }
        }

        matrices.translate(0.5, -0.5, 0);
        for (ItemStack pick: entity.getInventory(VendingMachineBlockEntity.InventoryAllocation.CLAIM)){
            if (pick.isEmpty()){
                matrices.translate(-0.5, 0, 0);
                continue;
            }

            itemRenderer.renderItem(pick, ModelTransformationMode.GUI, light, overlay, matrices, vertexConsumers, entity.getWorld(), 1);
            matrices.translate(-0.5, 0, 0);
        }

        matrices.scale(0.7f, 0.7f, 0.7f);
        matrices.translate(-0.2, 6.2, -1.5);
        itemRenderer.renderItem(entity.getItemStack(0, VendingMachineBlockEntity.InventoryAllocation.CURRENCY), ModelTransformationMode.NONE, light, overlay, matrices, vertexConsumers, entity.getWorld(), 1);

        BlockState state = entity.getWorld().getBlockState(entity.getPos());
        if (!(state.getBlock() instanceof VendingMachineBlock)){
            matrices.pop();
            return;
        }

        int choose = state.get(VendingMachineBlock.CHOOSE) - 1;
        if (choose != -1){
            matrices.scale(1.3f, 1.3f, 1.3f);
            matrices.translate(0, 1, 0);
            itemRenderer.renderItem(stacks[choose], ModelTransformationMode.GUI, light, overlay, matrices, vertexConsumers, entity.getWorld(), 1);

            matrices.scale(0.08f, 0.08f, 0.08f);
            matrices.translate(5.5, -18, 0);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
            MinecraftClient.getInstance().textRenderer.draw(matrices, entity.getPrice(choose)+"", 0, 0, 0x000000);
        }

        matrices.pop();
    }
}
