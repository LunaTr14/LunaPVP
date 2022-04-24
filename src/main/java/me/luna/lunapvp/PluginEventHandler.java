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

public class PluginEventHandler implements Listener {
    public boolean is_pvp_enabled = false;
    private final Main plugin;
    private final Server server;
    public PluginEventHandler(Main m) {
    	this.plugin = m;
    	this.server = m.getServer();
    }
    private void set_player_death(Player p) {
    	for(PlayerTemplate playerObject : plugin.player_instance_list) {
    		if(server.getPlayer(playerObject.getPlayer()) == p) {
    			playerObject.setPlayerDeathStatus(true);
    		}
    	}
    }
    
    private boolean is_player_dead(Player p) {
    	for(PlayerTemplate playerObject : plugin.player_instance_list) {
    		if(server.getPlayer(playerObject.getPlayer()) == p && playerObject.isPlayerDead()) {
    			return true;
    		}
    	}
		return false;
    }

    private boolean is_player_holding_stick (Player e) {
        return e.getInventory().getItemInMainHand().getType() == Material.STICK;
    }
    private boolean is_action_right_click(PlayerInteractEvent e) {
        return e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK;
    }
    
    private boolean is_attack_valid(Player attacker, Player recipient) {
        return attacker != recipient;
    }

    @org.bukkit.event.EventHandler
    public void on_player_hit(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player && is_pvp_enabled) {
            Player damager = (Player) e.getDamager();
            Player reciever = (Player) e.getEntity();
            if(!is_player_holding_stick(damager) || !is_attack_valid(damager, reciever))return;
            for(PlayerTemplate player_class : plugin.player_instance_list) {
            	if(server.getPlayer(player_class.getPlayer()) == e.getDamager() && !player_class.isPlayerErased() && !player_class.getIsCompressed()) {
            		player_class.getAbility().playerHitAbility(reciever);
            		return;
            	}
            }
        }
        else if(e.getEntity() instanceof Player && e.getDamager() instanceof Player && !is_pvp_enabled){
            e.setCancelled(true);
        }
    }
    @org.bukkit.event.EventHandler
    public void on_right_click(PlayerInteractEvent e){
        if(is_player_holding_stick(e.getPlayer()) && is_player_holding_stick(e.getPlayer()) && is_action_right_click(e)) {
        	for(PlayerTemplate playerClass : plugin.player_instance_list) {
        		if(server.getPlayer(playerClass.getPlayer()) == e.getPlayer() && !playerClass.isPlayerErased() && !playerClass.getIsCompressed()) {
        			playerClass.getAbility().activatedAbility();
        			return;
        		}
        	}
        }
        else return;
    }

    @org.bukkit.event.EventHandler
    public void on_teleport(PlayerTeleportEvent e){
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE && ! is_player_dead(e.getPlayer())){
            e.setCancelled(true);
        }
    }

    @org.bukkit.event.EventHandler
    public void on_death(PlayerDeathEvent e){
        Player p = e.getEntity();
        if(plugin.has_game_started) {
	        set_player_death(p);
	        p.setGameMode(GameMode.SPECTATOR);
        }
    }
    
    @org.bukkit.event.EventHandler
    public void on_player_join(PlayerJoinEvent e) {
    	e.getPlayer().sendMessage("Use /team to select team \nUse /ability to choose an ability");
    }
}
