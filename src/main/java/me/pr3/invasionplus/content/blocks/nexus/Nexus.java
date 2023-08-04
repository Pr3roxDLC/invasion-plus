package me.pr3.invasionplus.content.blocks.nexus;

import me.pr3.invasionplus.content.blocks.BlockEntities;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @author tim
 */
public class Nexus extends BlockWithEntity implements BlockEntityProvider {

    public Nexus() {
        super(FabricBlockSettings.create().strength(1.0f));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        updateIsOnValidBase(world, pos);
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        boolean isOnValidBase = updateIsOnValidBase(world, pos);
        if(isOnValidBase){
            if(!world.isClient){
                NamedScreenHandlerFactory factory = state.createScreenHandlerFactory(world, pos);
                if(factory != null){
                    player.openHandledScreen(factory);
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    private boolean updateIsOnValidBase(World world, BlockPos pos){
        boolean isOnValidBase = true;
        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                if (!world.getBlockState(pos.down().offset(Direction.Axis.X, x).offset(Direction.Axis.Z, z)).isOf(Blocks.NETHERITE_BLOCK)) {
                    isOnValidBase = false;
                }
            }
        }
        ((NexusBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos))).isOnValidBase = isOnValidBase;
        return isOnValidBase;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()){
            ItemScatterer.spawn(world, pos, ((NexusBlockEntity) Objects.requireNonNull(world.getBlockEntity(pos))).inventory);
            world.removeBlockEntity(pos);
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockEntities.NEXUS_BLOCK_ENTITY_BLOCK_ENTITY_TYPE, (world1, pos, state1, be) -> NexusBlockEntity.tick(world1, pos, state1, be));
    }



    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NexusBlockEntity(pos, state);
    }
}
