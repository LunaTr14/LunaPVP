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
public class MiniGameHandler {
	private final WorldBorder worldBorder;
	private final Main plugin;
	private final Server server;
	private final World world;
	public MiniGameHandler(Main p) {
		this.server = p.getServer();
		this.world = p.getServer().getWorld("world");
		this.worldBorder = p.getServer().getWorld("world").getWorldBorder();
		this.plugin = p;
	}
	// Teleport Players in the beginning of the game
    private void teleport_players(){
        for(Player player : server.getOnlinePlayers()){
            int highestBlock = server.getWorld("world").getHighestBlockYAt(0,0);
            player.teleport(new Location(player.getWorld(), 0,highestBlock + 1,0));
        }
    }
    
    private void shrink_world_border(double size, long timeToShrink){
        worldBorder.setSize(size,timeToShrink);

    }
    // Resets Player Health, Inventory and Food Level
    private void reset_player_conditions(Main plugin){
        for(Player p : server.getOnlinePlayers()){
            p.getInventory().clear();
            p.setHealth(20);
            p.setFoodLevel(25);
        }
    }

    // Creates the World Border during the start of the game
    private void initialize_world_border() {
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
    public void shrink_world_border() {
    	if((worldBorder.getSize() > 500)){
            shrink_world_border(worldBorder.getSize() / 2,300);
            return;
        }
        shrink_world_border(worldBorder.getSize() - 100,120);
    }
    protected void start_game_timer(Main p){
    	AirDrop airdrop = new AirDrop();
        initialize_world_border();
        teleport_players();
        airdrop.worldInstance= world;
        reset_player_conditions(p);
        new BukkitRunnable() {
            @Override
            public void run() {
                shrink_world_border();
                String worldBoderSizeString = Double.toString(worldBorder.getSize());
                airdrop.spawnAirdrop(p.getServer());
                p.getServer().broadcastMessage("Worldborder: " + worldBoderSizeString + "\nHead To 0,0");
            }
        }.runTaskTimer(p,20,8400);
    }
}
