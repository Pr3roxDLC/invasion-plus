package me.pr3.invasionplus.content.screens.nexus;


import com.mojang.blaze3d.systems.RenderSystem;

import me.pr3.invasionplus.Constants;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
/**
 * @author tim
 */
public class NexusScreen extends HandledScreen<NexusScreenHandler> {
    private static final Identifier TEXTURE =
            new Identifier(Constants.INVASION_PLUS, "textures/gui/nexus_gui.png");

    public NexusScreen(NexusScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getRenderTypeGuiProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, -2, backgroundWidth, backgroundHeight);
    }

}