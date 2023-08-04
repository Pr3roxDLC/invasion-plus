package me.pr3.invasionplus.mixins;

import me.pr3.invasionplus.client.InvasionPlusClient;
import me.pr3.invasionplus.content.blocks.nexus.Nexus;
import me.pr3.invasionplus.content.blocks.nexus.NexusBlockEntity;
import net.minecraft.client.gui.Drawable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.BreakDoorGoal;
import net.minecraft.entity.ai.goal.GoToWalkTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

/**
 * @author tim
 */
@Mixin(ZombieEntity.class)
public abstract class MixinZombieEntity extends HostileEntity {

    protected MixinZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        throw new IllegalStateException("Created Instance of MixinClass");
    }


    @Inject(method = "initGoals", at = @At("RETURN"))
    public void initGoals(CallbackInfo ci) {
        goalSelector.clear((goal) -> true);
        targetSelector.add(1, new MoveToTargetPosGoal(((ZombieEntity)(Object)this), 1, 60, 50) {
            @Override
            protected boolean isTargetPos(WorldView world, BlockPos pos) {
                return world.getBlockEntity(pos) instanceof NexusBlockEntity;
            }
        });
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci){

        if(getNavigation().getCurrentPath() == null)return;
        for(int i = 0; i < getNavigation().getCurrentPath().getLength(); i++){
            PathNode pathNode = getNavigation().getCurrentPath().getNode(i);
            getEntityWorld().addParticle(ParticleTypes.SONIC_BOOM, pathNode.x, pathNode.y, pathNode.z, 0f, 0f, 0f);
        }
    }

}
