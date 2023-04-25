package fengliu.lho.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fengliu.lho.screen.handler.CustomersBlockScreenHandler;
import fengliu.lho.util.IdUtil;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CustomersBlockScreen extends HandledScreen<CustomersBlockScreenHandler> {
    private static final Identifier TEXTTURE = IdUtil.get("textures/gui/customers_block.png");
    public CustomersBlockScreen(CustomersBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.passEvents = false;
        this.backgroundHeight = 167;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(RenderSystem::getShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTTURE);

        drawTexture(matrices, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        if (!this.handler.isShowPos()){
            return;
        }

        int width = this.handler.getWidth();
        int height = this.handler.getHeight();

        drawTextWithShadow(matrices, this.textRenderer, Text.translatable("lho.block.customers_block.info.width", width), this.x + 89, this.y + 43, 0xffffff);
        drawTextWithShadow(matrices, this.textRenderer, Text.translatable("lho.block.customers_block.info.height", height), this.x + 89, this.y + 51, 0xffffff);
        drawTextWithShadow(matrices, this.textRenderer, Text.translatable("lho.block.customers_block.info.size", width, height, width * height), this.x + 89, this.y + 59, 0xffffff);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
