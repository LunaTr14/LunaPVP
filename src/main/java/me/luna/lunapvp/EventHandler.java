/*
Created By: Luna T
Edited Last: 29/3/2022
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
    public boolean isPVPAllowed = false;
    private final main plugin;
    private final Server server;
    public EventHandler(main m) {
    	this.plugin = m;
    	this.server = m.getServer();
    }
    private void setPlayerDeath(Player p) {
    	for(playerObjectTemplate playerObject : plugin.playerInstanceList) {
    		if(server.getPlayer(playerObject.getPlayer()) == p) {
    			playerObject.setPlayerDeathStatus(true);
    		}
    	}
    }
    
    private boolean checkIfPlayerIsDead(Player p) {
    	for(playerObjectTemplate playerObject : plugin.playerInstanceList) {
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
    
    private boolean isAttackingPlayerValid(Player attacker, Player recipient) {
        return attacker != recipient;
    }

    @org.bukkit.event.EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player && isPVPAllowed) {
            Player damager = (Player) e.getDamager();
            Player reciever = (Player) e.getEntity();
            if(!isPlayerHoldingStick(damager) || !isAttackingPlayerValid(damager, reciever))return;
            for(playerObjectTemplate playerClass : plugin.playerInstanceList) {
            	if(server.getPlayer(playerClass.getPlayer()) == e.getDamager() && !playerClass.isPlayerErased() && !playerClass.getIsCompressed()) {
            		playerClass.getAbility().playerHitAbility(reciever);
            		return;
            	}
            }
        }
        else if(e.getEntity() instanceof Player && e.getDamager() instanceof Player && !isPVPAllowed){
            e.setCancelled(true);
        }
    }
    @org.bukkit.event.EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(isPlayerHoldingStick(e.getPlayer()) && isPlayerHoldingStick(e.getPlayer()) && isActionRightClick(e)) {
        	for(playerObjectTemplate playerClass : plugin.playerInstanceList) {
        		if(server.getPlayer(playerClass.getPlayer()) == e.getPlayer() && !playerClass.isPlayerErased() && !playerClass.getIsCompressed()) {
        			playerClass.getAbility().activatedAbility();
        			return;
        		}
        	}
        }
        else return;
    }

    @org.bukkit.event.EventHandler
    public void teleportPreventionEvent(PlayerTeleportEvent e){
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE && !checkIfPlayerIsDead(e.getPlayer())){
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
