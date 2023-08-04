package me.pr3.invasionplus.content.blocks;

import me.pr3.invasionplus.InvasionPlus;
import me.pr3.invasionplus.content.blocks.nexus.NexusBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

/**
 * @author tim
 */
public class BlockEntities {
    public static BlockEntityType<NexusBlockEntity> NEXUS_BLOCK_ENTITY_BLOCK_ENTITY_TYPE;

    public static void registerBlockEntities(){
        NEXUS_BLOCK_ENTITY_BLOCK_ENTITY_TYPE = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier("invasionplus", "nexus_block_entity"),
                FabricBlockEntityTypeBuilder.create(NexusBlockEntity::new, InvasionPlus.NEXUS).build()
        );
    }
}
