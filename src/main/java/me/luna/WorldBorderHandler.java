package me.luna;

import org.bukkit.Location;
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
    protected void testBorderCenter(){
        this.setCenter(0,0);
        try {
            assert this.getBorderCenter().getX() == 0;
            System.out.println("Border Center X Test Succesful");
        }
        catch (Exception e){
            System.err.println("WorldBorder Center X does not == 0");
            throw e;
        }
        try {
            assert this.getBorderCenter().getZ() == 0;
            System.out.println("Border Center Z Test Succesful");
        }
        catch (Exception e){
            System.err.println("WorldBorder Center Z does not == 0");
            throw e;
        }
    }

    protected  void testBorderSize(){
        this.shrinkBorder(2000,0);
        try {
            assert this.getBorderSize() == 2000;
            System.out.println("Border Size Test Succesful");
        }
        catch (Exception e) {
            System.err.println("WorldBorder Size does not equal 20000");
            throw e;
        }

    }
}
