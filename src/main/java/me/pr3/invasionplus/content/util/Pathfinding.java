package me.pr3.invasionplus.content.util;

import net.minecraft.util.math.BlockPos;

/**
 * @author tim
 */
public class Pathfinding {
    public static BlockPos getNextBlockToPlace(BlockPos current, BlockPos target){
        // Calculate the differences in coordinates between the current and target BlockPos
        int deltaX = target.getX() - current.getX();
        int deltaZ = target.getZ() - current.getZ();
        int offsetX = 0;
        int offsetZ = 0;
        int offsetY = -1;

        // Determine the larger coordinate difference and create the offset in that direction
        if(Math.abs(deltaX) > Math.abs(deltaZ)){
            offsetX = (int) Math.signum(deltaX);
        }else{
            offsetZ = (int) Math.signum(deltaZ);
        }
        if(target.getY() - 1 > current.getY())offsetY = 0;

        return new BlockPos(offsetX + current.getX(), current.getY() + offsetY, offsetZ + current.getZ());
    }
}
