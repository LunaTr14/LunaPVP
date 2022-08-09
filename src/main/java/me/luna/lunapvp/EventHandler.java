/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Handles Events like Player Hit, Movement
*/
package me.luna.lunapvp;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.LinkedList;

public class EventHandler implements Listener {
    public boolean isPVPEnabled = false;
    private Main plugin;
    private Server server;

    protected void setPlugin(Main plugin){
        this.plugin = plugin;
    }

    protected void setServer(Server server){
        this.server = server;
    }
    private void setPlayerDeath(Player p) {
    	for(PlayerTemplate playerObject : plugin.playerInstanceList) {
    		if(server.getPlayer(playerObject.getPlayer()) == p) {
    			playerObject.setPlayerDeathStatus(true);
    		}
    	}
    }

    protected void registerHandler(){
        server.getPluginManager().registerEvents(this,plugin);
    }
    private boolean isPlayerDead(Player p) {
    	for(PlayerTemplate playerObject : plugin.playerInstanceList) {
    		if(server.getPlayer(playerObject.getPlayer()) == p && playerObject.isPlayerDead()) {
    			return true;
    		}
    	}
		return false;
    }

    private boolean isPlayerHoldingStick (Player e) {
        return e.getInventory().getItemInMainHand().getType() == Material.STICK;
    }
    private boolean isActionRightClick(PlayerInteractEvent e) {
        return e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK;
    }
    
    private boolean isAttackValid(Player attacker, Player recipient) {
        return attacker != recipient;
    }

    @org.bukkit.event.EventHandler
    public void on_player_hit(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player && isPVPEnabled) {
            Player damager = (Player) e.getDamager();
            Player reciever = (Player) e.getEntity();
            if(!isPlayerHoldingStick(damager) || !isAttackValid(damager, reciever))return;
            for(PlayerTemplate playerClass : plugin.playerInstanceList) {
            	if(server.getPlayer(playerClass.getPlayer()) == e.getDamager() && !playerClass.isPlayerErased() && !playerClass.getIsCompressed()) {
            		playerClass.getAbility().playerHitAbility(reciever);
            		return;
            	}
            }
        }
        else if(e.getEntity() instanceof Player && e.getDamager() instanceof Player && !isPVPEnabled){
            e.setCancelled(true);
        }
    }
    @org.bukkit.event.EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(isPlayerHoldingStick(e.getPlayer()) && isPlayerHoldingStick(e.getPlayer()) && isActionRightClick(e)) {
        	for(PlayerTemplate playerClass : plugin.playerInstanceList) {
        		if(server.getPlayer(playerClass.getPlayer()) == e.getPlayer() && !playerClass.isPlayerErased() && !playerClass.getIsCompressed()) {
        			playerClass.getAbility().activatedAbility();
        			return;
        		}
        	}
        }
        else return;
    }

    @org.bukkit.event.EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE && ! isPlayerDead(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @org.bukkit.event.EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        if(plugin.hasGameStarted) {
	        setPlayerDeath(p);
	        p.setGameMode(GameMode.SPECTATOR);
        }
    }
    
    @org.bukkit.event.EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
    	e.getPlayer().sendMessage("Use /team to select team \nUse /ability to choose an ability");
    }
}
