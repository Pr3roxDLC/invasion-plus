package me.pr3.invasionplus.content.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author tim
 */
public class SpawnUtil {
    public static List<BlockPos> generateRandomPointsOnCircle(BlockPos center, int numberOfPoints, double radius, double deviation) {
        List<BlockPos> points = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfPoints; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double deviationAmount = random.nextDouble() * deviation;

            // Calculate the point on the circle without deviation
            int x = center.getX() + (int) (radius * Math.cos(angle));
            int z = center.getZ() + (int) (radius * Math.sin(angle));

            // Deviate the point inward or outward
            double deviationSign = random.nextBoolean() ? 1.0 : -1.0;
            double deviatedRadius = radius + deviationAmount * deviationSign;

            // Calculate the final deviated point
            int deviatedX = center.getX() + (int) (deviatedRadius * Math.cos(angle));
            int deviatedZ = center.getZ() + (int) (deviatedRadius * Math.sin(angle));

            points.add(new BlockPos(deviatedX, 255, deviatedZ));
        }

        return points;
    }


    public static int getHighestBlockY(World world, int x, int z) {

        for (int y = world.getTopY() - 2; y >= world.getBottomY(); y--) {
            BlockPos pos = new BlockPos(x, y, z);
            // Check if the block is solid (not air)
            if (!world.getBlockState(pos).isOf(Blocks.AIR)) {
                return y; // Found the highest solid block
            }
        }

        return world.getBottomY(); // No solid block found, return the bottom Y coordinate
    }

}
