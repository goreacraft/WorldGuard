// $Id$
/*
 * WorldGuard
 * Copyright (C) 2010 sk89q <http://www.sk89q.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

package com.sk89q.worldguard.protection.regions;

import java.util.List;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldedit.Vector;


public class ProtectedPolygonalRegion extends ProtectedRegion {
    protected List<BlockVector2D> points;
    protected int minY;
    protected int maxY;
    private BlockVector min;
    private BlockVector max;
    
    public ProtectedPolygonalRegion(String id, List<BlockVector2D> points, int minY, int maxY) {
        super(id);
        this.points = points;
        this.minY = minY;
        this.maxY = maxY;
        
        int minX = points.get(0).getBlockX();
        int minZ = points.get(0).getBlockZ();
        int maxX = points.get(0).getBlockX();
        int maxZ = points.get(0).getBlockZ();
        
        for (BlockVector2D v : points) {
            int x = v.getBlockX();
            int z = v.getBlockZ();
            if (x < minX) minX = x;
            if (z < minZ) minZ = z;
            if (x > maxX) maxX = x;
            if (z > maxZ) maxZ = z;
        }

        min = new BlockVector(minX, minY, minZ);
        max = new BlockVector(maxX, maxY, maxZ);
    }

    @Override
    public BlockVector getMinimumPoint() {
        return min;
    }

    @Override
    public BlockVector getMaximumPoint() {
        return max;
    }

    /**
     * Checks to see if a point is inside this region.
     */
    @Override
    public boolean contains(Vector pt) {
        int targetX = pt.getBlockX(); //wide
        int targetY = pt.getBlockY(); //height
        int targetZ = pt.getBlockZ(); //depth
        
        if (targetY < minY || targetY > maxY) {
            return false;
        }
        //Quick and dirty check.
        if(targetX < min.getBlockX() || targetX > max.getBlockX() || targetZ < min.getBlockZ() || targetZ > max.getBlockZ()){
        	return false;
        }
        boolean inside = false;
        int npoints = points.size();
        int xNew, zNew;
        int xOld, zOld;
        int x1, z1;
        int x2, z2;
        int i;

        xOld = points.get(npoints - 1).getBlockX();
        zOld = points.get(npoints - 1).getBlockZ();
        
        for (i = 0; i < npoints; i++) {
            xNew = points.get(i).getBlockX();
            zNew = points.get(i).getBlockZ();
            //Check for corner
            if(xNew == targetX && zNew == targetZ){
        	return true;
            }
            if (xNew > xOld) {
                x1 = xOld;
                x2 = xNew;
                z1 = zOld;
                z2 = zNew;
            } else {
                x1 = xNew;
                x2 = xOld;
                z1 = zNew;
                z2 = zOld;
            }
            if ((xNew < targetX) == (targetX <= xOld)
                    && ((long) targetZ - (long) z1) * (long) (x2 - x1) <= ((long) z2 - (long) z1)
                            * (long) (targetX - x1)) {
                inside = !inside;
            }
            xOld = xNew;
            zOld = zNew;
        }
        
        return inside;
    }

    /**
     * Return the type of region as a user-friendly name.
     * 
     * @return type of region
     */
    @Override
    public String getTypeName() {
        return "polygon";
    }

}