package me.pr3.invasionplus.content.core;

import me.pr3.invasionplus.content.blocks.nexus.Nexus;
import me.pr3.invasionplus.content.blocks.nexus.NexusBlockEntity;
import me.pr3.invasionplus.content.entities.InvasionZombieEntity;
import me.pr3.invasionplus.content.util.SpawnUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.FuzzyPositions;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.gen.chunk.placement.SpreadType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tim
 */

//This class represents an ongoing invasion, it therefor has to keep track of the nexus core that started it, progress of the invasion etc
public class Invasion {

    NexusBlockEntity nexusBlockEntity;
    int wave = 0;
    int radius = 30;
    private Set<Entity> currentWaveMobs;

    private List<PlayerEntity> boundPlayers;

    public Invasion(NexusBlockEntity nexusBlockEntity){
        this.nexusBlockEntity = nexusBlockEntity;
        currentWaveMobs = new HashSet<>();
        onInvasionStart();
        spawnNextWave();
    }

    public void addMobToCurrentWave(Entity entity){
        currentWaveMobs.add(entity);
    }

    public void removeMobFromCurrentWave(Entity entity){
        currentWaveMobs.remove(entity);
    }

    public boolean allMobsDead(){
        return currentWaveMobs.isEmpty();
    }

    public void onInvasionStart(){
        boundPlayers = nexusBlockEntity.getWorld().getPlayers().stream().filter(player -> player.getPos().distanceTo(nexusBlockEntity.getPos().toCenterPos()) <= radius).collect(Collectors.toList());
    }

    public void spawnNextWave(){
        SpawnUtil.generateRandomPointsOnCircle(nexusBlockEntity.getPos(), wave, radius, 5).forEach(pos -> {
            InvasionZombieEntity zombieEntity = new InvasionZombieEntity( nexusBlockEntity);
            zombieEntity.setPosition(pos.getX(), SpawnUtil.getHighestBlockY(nexusBlockEntity.getWorld(), pos.getX(), pos.getZ()) + 1, pos.getZ());
            nexusBlockEntity.getInvasion().addMobToCurrentWave(zombieEntity);
            nexusBlockEntity.getWorld().spawnEntity(zombieEntity);
        });
    }

    public void onTick(){
        if(allMobsDead()){
            wave++;
            spawnNextWave();
        }
    }

}
