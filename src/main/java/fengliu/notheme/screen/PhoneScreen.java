package fengliu.notheme.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fengliu.notheme.NoThemeMod;
import fengliu.notheme.screen.handler.PhoneScreenHandler;
import fengliu.notheme.util.IdUtil;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PhoneScreen extends HandledScreen<PhoneScreenHandler> {
    private static final int texturesWidth = 456;
    private static final int texturesHeight = 256;
    protected int phoneBackgroundHeight = 207;
    protected int phoneBackgroundWidth = 300;
    protected int allBackgroundY = 0;
    protected int allBackgroundX = 0;
    protected int backgroundX = 0;
    protected int backgroundY = 0;

    private static final Identifier TEXTTURE = IdUtil.get("textures/gui/phone.png");
    private ButtonWidget testBut;

    public PhoneScreen(PhoneScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.passEvents = false;
        this.backgroundHeight = this.phoneBackgroundHeight;
        this.backgroundWidth = this.phoneBackgroundWidth;
    }

    @Override
    protected void init() {
        this.allBackgroundX = (this.width - this.phoneBackgroundWidth) / 2;
        this.allBackgroundY = (this.height - this.phoneBackgroundHeight) / 2 - 5;
        this.x = allBackgroundX + 129;
        this.y = this.allBackgroundY - 82;

        this.testBut = this.addDrawableChild(ButtonWidget.builder(Text.literal("test"), (button) -> {
            NoThemeMod.LOGGER.info("test Button Widget");
        }).size(24, 24).position(this.x, this.y).build());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(RenderSystem::getShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTTURE);

        drawTexture(matrices, this.allBackgroundX, this.allBackgroundY, 0, 0, this.phoneBackgroundWidth, this.phoneBackgroundHeight, texturesWidth, texturesHeight);
        this.testBut.render(matrices, mouseX, mouseY, delta);
    }
}
