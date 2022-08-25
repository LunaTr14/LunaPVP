/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Controls the Start, PvP and timing of game
*/

package me.luna.lunapvp;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
public class GameController {
	private Main plugin;
	private World world;
    private Server server;

    private WorldBorder worldBorder;

    public GameController(Main plugin, World w){
        this.plugin = plugin;
        this.server =plugin.getServer();
        this.world = w;
        this.worldBorder = w.getWorldBorder();
    }

    private AirDrop createAirDrop(){
        AirDrop airDrop = new AirDrop();
        airDrop.setWorld(world);
        airDrop.spawnAirdrop(server);
        return airDrop;
    }

    public void startGame(){
        initialiseWorld();
        teleportPlayers();
        resetPlayers();
        new BukkitRunnable() {
            @Override
            public void run() {
                double newWorldBorder = generateWorldBorderSize();
                server.broadcastMessage("Previous World Border Size: " + world.getWorldBorder().getSize());
                server.broadcastMessage("New World Border Size" + newWorldBorder);
                worldBorder.setSize(newWorldBorder,60);
                createAirDrop();
            }
        }.runTaskTimer(plugin,20,8400);
    }

    private void initialiseWorld() {
        worldBorder.setCenter(0,0);
        worldBorder.setSize(2500);
        worldBorder.setWarningDistance(50);
        worldBorder.setWarningTime(20);
    }

    private void teleportPlayers(){
        for(Player player : server.getOnlinePlayers()){
            int highestBlock = server.getWorld("world").getHighestBlockYAt(0,0);
            player.teleport(new Location(player.getWorld(), 0,highestBlock + 1,0));
        }
    }


    // Resets Player Health, Inventory and Food Level
    private void resetPlayers(){
        for(Player p : server.getOnlinePlayers()){
            p.getInventory().clear();
            p.setHealth(20);
            p.setFoodLevel(25);
        }
    }

    private double generateWorldBorderSize(){
        double previousBorderSize = world.getWorldBorder().getSize();
        if(previousBorderSize> 1500){
            return previousBorderSize / 2;
        }
        return previousBorderSize - 500;
    }
}
