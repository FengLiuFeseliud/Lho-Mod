package fengliu.notheme.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fengliu.notheme.screen.handler.BloodPoolBlockScreenHandler;
import fengliu.notheme.util.IdUtil;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BloodPoolBlockScreen extends HandledScreen<BloodPoolBlockScreenHandler> {
    private static final Identifier TEXTTURE = IdUtil.get("textures/gui/blood_pool_block.png");

    public BloodPoolBlockScreen(BloodPoolBlockScreenHandler handler, PlayerInventory inventory, Text title) {
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

        int heartType = this.handler.getHeartType();
        if (heartType == 0){
            return;
        }

        int offset = ((int) Math.ceil(31 / 9)) * (this.handler.getLevel() + 1) - 1;
        drawTexture(matrices, this.x + 71, this.y + 47 - offset, 31 * (heartType - 1), 222 - offset, 31, 31);
        drawTexture(matrices, this.x + 102, this.y + 18, 176, 15, (15 / 20) * this.handler.getCokyTick(), 6);

        offset = 6 * (heartType - 1);
        drawTexture(matrices, this.x + 85, this.y + 47, 176 + offset, 0, 6, (15 / 20) * this.handler.getFillTick());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);

        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
