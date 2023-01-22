/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Controls the Start, PvP and timing of game
*/

package me.luna.controller;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class GameController {
    private static int GAME_TICKS = 20;
    private World world;
    long gameStartTime = 0;
    public boolean isGracePeriodActive = true;

    public float gracePeriodSeconds = 300;
    private long borderShrinkSeconds = 120;

    private Main plugin;
    private WorldBorder border;
    private double lastShrink = System.currentTimeMillis();

    public GameController(Main p, World w){
        plugin = p;
        this.world = w;
        this.border = w.getWorldBorder();
    }

    public void startGame(){
        gameStartTime = System.currentTimeMillis();
        teleportAllPlayers(0,world.getHighestBlockYAt(0,0),0);
        border.setCenter(0,0);
        startGracePeriodTimer();
        runWorldBorderTimer();
    }
    private void startGracePeriodTimer(){
        new BukkitRunnable() {
            @Override
            public void run() {
                long timeElapsed = gameStartTime - System.currentTimeMillis();
                if(timeElapsed >= gracePeriodSeconds){
                    isGracePeriodActive = false;
                    plugin.getServer().broadcastMessage("Grace Period is off");
                    cancel();
                }
                else if(timeElapsed >= (gracePeriodSeconds / 2)){
                    plugin.getServer().broadcastMessage("Half of Grace period");
                }
            }
        }.runTaskTimer(plugin,0,10);
    }
    private void runWorldBorderTimer(){
        new BukkitRunnable(){
            @Override
            public void run() {
                if(border.getSize() < 500){
                    cancel();
                }
                shrinkWorldBorder();
            }
        }.runTaskTimer(plugin,0,GAME_TICKS * 10);
    }
    private void teleportAllPlayers(double x, double y, double z){
        for(Player p : plugin.getServer().getOnlinePlayers()){
            p.teleport(new Location(p.getWorld(),x,y,z));

    // shrinkWorldBorder used for the worldborder timer and shrinking based on previous size
    private void shrinkWorldBorder (){
        int borderSize = (int) border.getSize();
        if(lastShrink - gameStartTime > borderShrinkSeconds){
            if(borderSize < 20){
                server.broadcastMessage("Worldborder will no longer shrink, Size is 20 ");
            }
            else if(borderSize > 500 && border.getSize() < 1000){
                server.broadcastMessage("Worldborder will shrink to: " + (borderSize - 250) + "\nCurrent Size: "+ borderSize);
                setWorldBorder(borderSize - 250);
            }
            else{
                server.broadcastMessage("Worldborder will shrink to: " + (borderSize /2) + "\nCurrent Size: "+borderSize);
                setWorldBorder(borderSize / 2);
            }
        }
    }

    //Resets Hunger and Health of all online Players
    private void resetPlayerStatus(){
        for(Player p : plugin.getServer().getOnlinePlayers()){
            p.setHealth(20);
            p.setFoodLevel(20);
        }
    //setWorldBorder used for manually setting the size of the Border
    private void setWorldBorder(double size){
        world.getWorldBorder().setSize(size,borderShrinkSeconds / 2);
    }

}
