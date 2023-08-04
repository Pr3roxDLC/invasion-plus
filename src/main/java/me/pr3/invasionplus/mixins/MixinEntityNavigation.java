package me.pr3.invasionplus.mixins;

import me.pr3.invasionplus.content.util.Pathfinding;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author tim
 */
@Mixin(EntityNavigation.class)
public abstract class MixinEntityNavigation {
    @Shadow
    @Final
    protected World world;

    @Shadow public abstract boolean isIdle();

    @Shadow @Final protected MobEntity entity;

    @Inject(method = "startMovingAlong", at = @At("RETURN"))
    public void startMovingAlong(Path path, double speed, CallbackInfoReturnable<Boolean> cir) {
        if(path == null)return;
        if(world instanceof ServerWorld serverWorld) {
            for (int i = 0; i < path.getLength(); i++) {
                PathNode pathNode = path.getNode(i);
                serverWorld.spawnParticles(ParticleTypes.SONIC_BOOM, pathNode.x + 0d, pathNode.y + 0d, pathNode.z + 0d, 10, 0d, 0d, 0d, 0d);
            }
            if(!path.reachesTarget() && path.getCurrentNode() == path.getEnd()){
                BlockPos nextBlock = Pathfinding.getNextBlockToPlace(path.getCurrentNode().getBlockPos(), path.getTarget());
                if(world.getBlockState(nextBlock).isOf(Blocks.AIR)){
                    world.setBlockState(nextBlock, Blocks.OAK_PLANKS.getDefaultState());
                }else if(!world.getBlockState(nextBlock.up()).isOf(Blocks.AIR)){
                    world.breakBlock(nextBlock.up(), true, entity);
                }else if(!world.getBlockState(entity.getBlockPos().up().up()).isOf(Blocks.AIR)){
                    world.breakBlock(entity.getBlockPos().up().up(), true, entity);
                }else if(!world.getBlockState(nextBlock.up().up()).isOf(Blocks.AIR)){
                    world.breakBlock(nextBlock.up().up(), true, entity);
                }
            }
        }
    }
}
