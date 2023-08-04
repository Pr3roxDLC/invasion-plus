package me.pr3.invasionplus.content.blocks.nexus;

import me.pr3.invasionplus.InvasionPlus;
import me.pr3.invasionplus.content.blocks.BlockEntities;
import me.pr3.invasionplus.content.blocks.InventoryImpl;
import me.pr3.invasionplus.content.core.Invasion;
import me.pr3.invasionplus.content.screens.nexus.NexusScreenHandler;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author tim
 */
public class NexusBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, InventoryImpl {

    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    boolean isOnValidBase = false;
    private PropertyDelegate propertyDelegate;

    private Invasion invasion;

    public Invasion getInvasion() {
        return invasion;
    }
    public NexusBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntities.NEXUS_BLOCK_ENTITY_BLOCK_ENTITY_TYPE, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {

            }

            @Override
            public int size() {
                return 0;
            }
        };
    }

    public static void tick(World world, BlockPos pos, BlockState state, NexusBlockEntity be) {
        float offsetX = world.getRandom().nextFloat();
        float offsetZ = world.getRandom().nextFloat();
        if (be.isOnValidBase) {
            if (world.getRandom().nextBoolean()) {
                world.addParticle(ParticleTypes.MYCELIUM, pos.getX() + offsetX * 3 - 1, pos.getY() + 0.1, pos.getZ() + offsetZ * 3 - 1, 0f, 0f, 0f);
            } else {
                world.addParticle(ParticleTypes.CRIMSON_SPORE, pos.getX() + offsetX, pos.up().getY(), pos.getZ() + offsetZ, 0f, 0f, 0f);
            }
            if(be.invasion == null && !world.isClient){
                be.invasion = new Invasion(be);
            }
            if(be.invasion != null){
                be.invasion.onTick();
            }
        }
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("isOnValidBase", isOnValidBase);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        isOnValidBase = nbt.getBoolean("isOnValidBase");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Nexus");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new NexusScreenHandler(syncId, inv, this, this.propertyDelegate);
    }


}
