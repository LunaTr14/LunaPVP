/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Controls the Start, PvP and timing of game
*/

package me.luna;

import me.luna.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class GameController {
    private static double INITIAL_BORDER_SIZE = 1500;
    private static int GAME_TICKS = 20;
    private static int PVP_DELAY = 60 * 5;
    protected boolean hasGameStarted = false;
    private World world;
    private Main plugin;
    private long shrinkDelay = 60*5;

    public GameController(Main plugin){
        this.plugin = plugin;
    }
    public void startGame(){
        Player onlinePlayers[] = plugin.getServer().getOnlinePlayers().toArray(new Player[0]);
        world = onlinePlayers[0].getWorld();
        for(Player p : onlinePlayers){
            setHunger(p,20);
            setHealth(p,20);
            clearInventory(p);
            teleportPlayer(p,0, getHighestBlock(world,0,0) ,0);
        }
        setBorderCenter(0,0);
        setBorderSize(INITIAL_BORDER_SIZE,0);
        startShrinkTimer();
        new BukkitRunnable(){

            @Override
            public void run() {

                plugin.eventHandler.isPvPAllowed = true;
                plugin.getServer().broadcastMessage("PVP Has been enabled");
            }
        }.runTaskLater(plugin,GAME_TICKS* PVP_DELAY);
        this.hasGameStarted = true;
        }
    private void startShrinkTimer(){
        new BukkitRunnable(){
            long lastShrink = plugin.getNow();
            @Override
            public void run() {
                    plugin.getServer().broadcastMessage("WorldBorder Size is at: " + Math.round( world.getWorldBorder().getSize()));
                    if(getBorderSize() > 1500){
                        setBorderSize(getBorderSize() / 2, (long) (shrinkDelay * 0.5));
                    }
                    else if(getBorderSize() <= 1500 || getBorderSize() > 500){
                        setBorderSize(getBorderSize() - 250, (long) (shrinkDelay * 0.5));
                    }
                    else{
                        cancel();
                    }
            }
        }.runTaskTimer(plugin,2 *GAME_TICKS, 2 * GAME_TICKS * shrinkDelay);
    }

    private double getBorderSize(){
        return world.getWorldBorder().getSize();
    }
    private void setBorderCenter(double x, double z){
        this.world.getWorldBorder().setCenter(x,z);
    }
    private long getHighestBlock(World w, int x,int z){
        return w.getHighestBlockYAt(x,z);
    }
    private void teleportPlayer(Player p, double x, double y, double z) {
        p.teleport(new Location(p.getWorld(), x, y, z));
    }
    private void setHealth(Player p, int health){
        p.setHealth(health);
    }

    private void setHunger(Player p, int hunger){
        p.setFoodLevel(hunger);
    }

    private void clearInventory(Player p){
        p.getInventory().clear();
    }
    protected void setBorderSize(double size, long shrinkTime){
        this.world.getWorldBorder().setSize(size,shrinkTime);
    }
}
