package me.pr3.invasionplus.content.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * @author tim
 */
public class Ruby extends Item {
    public Ruby() {
        super(new FabricItemSettings().maxCount(16));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(SoundEvents.AMBIENT_CAVE.value(), 1.0f, 1.0f);
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
