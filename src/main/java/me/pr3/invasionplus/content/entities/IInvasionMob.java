package me.pr3.invasionplus.content.entities;

import me.pr3.invasionplus.content.blocks.nexus.NexusBlockEntity;

/**
 * @author tim
 */
public interface IInvasionMob {
    NexusBlockEntity getBoundNexusBlockEntity();
    void setBoundNexusBlockEntity(NexusBlockEntity nexusBlockEntity);
}
