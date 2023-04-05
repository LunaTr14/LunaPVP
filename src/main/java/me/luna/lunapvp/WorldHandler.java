package me.luna.lunapvp;


import org.bukkit.World;

public class WorldHandler {
    private static int SHRINK_PERIOD = 90;
    private static int SHRINK_DELAY_SEC = 240;

    private World world;
    public void spawnAirDrop(){

    }
    private double getBorderSize(){
        return this.world.getWorldBorder().getSize();
    }
    private void setBorderCenter(double x, double z){
        this.world.getWorldBorder().setCenter(x,z);
    }
    private void setBorderSize(double size){
        this.world.getWorldBorder().setSize(size);
    }
    private double getNewBorderRadius(){
        double borderLength = getBorderSize();
        if(borderLength <= 500){
            borderLength = borderLength - 100;
        }
        else if(borderLength >500 | borderLength <= 2000){
            borderLength = borderLength * 0.75;
        }
        else{
            borderLength = borderLength / 2;
        }
        return borderLength / 2;
    }
    public void shrinkBorder(){
        if(getBorderSize() <= 50) return;
        world.getWorldBorder().setSize(getNewBorderRadius(),SHRINK_PERIOD);
    }

    public void initWorldBorder(){
        setBorderCenter(0,0);
        setBorderSize(10000);
    }

    public void setWorld(World world){
        this.world = world;
    }
}
