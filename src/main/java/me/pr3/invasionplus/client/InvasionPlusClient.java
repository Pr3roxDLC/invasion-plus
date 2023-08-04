package me.pr3.invasionplus.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;


import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static net.fabricmc.api.EnvType.*;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;

/**
 * @author tim
 */
@Environment(CLIENT)
public class InvasionPlusClient  implements ClientModInitializer {


    @Override
    public void onInitializeClient() {

        WorldRenderEvents.AFTER_ENTITIES.register((worldRenderContext) -> {
            worldRenderContext.world().getEntities().forEach(entity -> {
                if(entity instanceof MobEntity mob){
                   BlockPos target = mob.getNavigation().getTargetPos();
                   if(target!=null) {
                       Box box = new Box(target.getX(), target.getY(), target.getZ(), target.getX() + 1, target.getY() + 1, target.getZ() + 1);
                       drawOutlinedBox(box, worldRenderContext.matrixStack());
                   }
                }
            });
        });
    }

    public static void drawOutlinedBox(Box bb, MatrixStack matrixStack)
    {
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINES,
                VertexFormats.POSITION);
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.minY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.minY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.maxX, (float)bb.maxY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float)bb.minX, (float)bb.maxY, (float)bb.minZ)
                .next();
        tessellator.draw();
    }

}
