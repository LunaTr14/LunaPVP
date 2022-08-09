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

    private void createAirDrop(){
        AirDrop airDrop = new AirDrop();
        airDrop.setWorld(world);
        airDrop.spawnAirdrop(server);
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

    // Creates the World Border during the start of the game
    private void initialiseWorld() {
        worldBorder.setCenter(0,0);
        worldBorder.setSize(2500);
        worldBorder.setWarningDistance(50);
        worldBorder.setWarningTime(20);
    }

    protected void setPlugin(Main plugin){
        this.plugin = plugin;
    }

    protected void setWorld(World world){
        this.world = world;
        this.worldBorder = world.getWorldBorder();
    }

    protected void setServer(Server server){
        this.server = server;
    }
    protected void shrinkWorldBorder(double borderLength, int seconds) {
        this.worldBorder.setSize(borderLength,seconds);
    }
    protected void startGame(){
        initialiseWorld();
        teleportPlayers();
        resetPlayers();
        new BukkitRunnable() {
            @Override
            public void run() {
                shrinkWorldBorder((worldBorder.getSize() - 100) * 0.75,60);
                String worldBoderSizeString = Double.toString(worldBorder.getSize());
                createAirDrop();
                plugin.getServer().broadcastMessage("Worldborder: " + worldBoderSizeString + "\nHead To 0,0");
            }
        }.runTaskTimer(plugin,20,8400);
    }
}
