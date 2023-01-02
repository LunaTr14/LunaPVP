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

import java.util.LinkedList;

public class GameController {

    private World world;
    long gameStartTime = 0;
    public boolean isGracePeriodActive = true;

    public float gracePeriodSeconds = 300;

    private Main plugin;

    public void startGame(){
        gameStartTime = System.currentTimeMillis();
        teleportAllPlayers(0,world.getHighestBlockYAt(0,0),0);
        startGracePeriodTimer();
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
    public GameController(Main p, World w){
        plugin = p;
        this.world = w;
    }
    private void teleportAllPlayers(double x, double y, double z){
        for(Player p : plugin.getServer().getOnlinePlayers()){
            p.teleport(new Location(p.getWorld(),x,y,z));
        }
    }

    //Resets Hunger and Health of all online Players
    private void resetPlayerStatus(){
        for(Player p : plugin.getServer().getOnlinePlayers()){
            p.setHealth(20);
            p.setFoodLevel(20);
        }
    }
}
