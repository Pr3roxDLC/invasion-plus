package me.pr3.invasionplus.content.entities;

import me.pr3.invasionplus.content.blocks.nexus.NexusBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Objects;

/**
 * @author tim
 */
public class InvasionZombieEntity extends ZombieEntity implements IInvasionMob {

    NexusBlockEntity nexusBlockEntity;

    public InvasionZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
    }

    public InvasionZombieEntity(NexusBlockEntity be) {
        super(Objects.requireNonNull(be.getWorld()));
        this.nexusBlockEntity = be;
        nexusBlockEntity.getInvasion().addMobToCurrentWave(this);
        this.setCustomNameVisible(true);
        this.setCustomName(Text.literal("InvasionZombie"));
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        this.nexusBlockEntity.getInvasion().removeMobFromCurrentWave(this);
    }

    @Override
    public NexusBlockEntity getBoundNexusBlockEntity() {
        return nexusBlockEntity;
    }

    @Override
    public void setBoundNexusBlockEntity(NexusBlockEntity nexusBlockEntity) {
        this.nexusBlockEntity = nexusBlockEntity;
    }
}
