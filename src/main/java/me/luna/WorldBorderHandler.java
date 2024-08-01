package me.luna;

import org.bukkit.World;
import org.bukkit.WorldBorder;

public class WorldBorderHandler {

    private WorldBorder worldBorder = null;
    public WorldBorderHandler(World world){
        this.worldBorder = world.getWorldBorder();
    }

    public void shrinkBorder(long newSize, long delayTicks){
        this.worldBorder.setSize(newSize,delayTicks);
    }

    public void setCenter(double xCenter, double zCenter){
        this.worldBorder.setCenter(xCenter,zCenter);
    }

    public double getBorderSize(){
        return this.worldBorder.getSize();
    }
}
