/*
Created By: Luna T
Edited Last: 29/3/2022
Purpose: Controls the Start, PvP and timing of game
*/

package me.luna.lunapvp;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
public class GameHandler {
	private final WorldBorder worldBorder;
	private final main plugin;
	private final Server server;
	private final World world;
	public GameHandler(main p) {
		this.server = p.getServer();
		this.world = p.getServer().getWorld("world");
		this.worldBorder = p.getServer().getWorld("world").getWorldBorder();
		this.plugin = p;
	}
	// Teleport Players in the beginning of the game
    private void teleportPlayers(){
        for(Player player : server.getOnlinePlayers()){
            int highestBlock = server.getWorld("world").getHighestBlockYAt(0,0);
            player.teleport(new Location(player.getWorld(), 0,highestBlock + 1,0));
        }
    }
    
    private void shrinkWorldBorder(double size, long timeToShrink){
        worldBorder.setSize(size,timeToShrink);

    }
    // Resets Player Health, Inventory and Food Level
    private void resetPlayer(main plugin){
        for(Player p : server.getOnlinePlayers()){
            p.getInventory().clear();
            p.setHealth(20);
            p.setFoodLevel(25);
        }
    }

    // Creates the World Border during the start of the game
    private void initializeWorldBorder() {
    	worldBorder.setCenter(0,0);
        worldBorder.setSize(2500);
        worldBorder.setWarningDistance(50);
        worldBorder.setWarningTime(20);
    }
    /*
        Shrinks borders when game has a begun
        If World Border is above 500 -> Devides by 2
        If World Border is Below 500 ->  Minus 100
     */
    public void shrinkBorder() {
    	if((worldBorder.getSize() > 500)){
            shrinkWorldBorder(worldBorder.getSize() / 2,300);
            return;
        }
        shrinkWorldBorder(worldBorder.getSize() - 100,120);
    }
    protected void startGameTimer(main p){
    	AirDrop airdrop = new AirDrop();
        initializeWorldBorder();
        teleportPlayers();
        airdrop.w = world;
        resetPlayer(p);
        new BukkitRunnable() {
            @Override
            public void run() {
                shrinkBorder();
                String worldBoderSizeString = Double.toString(worldBorder.getSize());
                airdrop.spawnAirDrop(p.getServer());
                p.getServer().broadcastMessage("Worldborder: " + worldBoderSizeString + "\nHead To 0,0");
            }
        }.runTaskTimer(p,20,8400);
    }
}
