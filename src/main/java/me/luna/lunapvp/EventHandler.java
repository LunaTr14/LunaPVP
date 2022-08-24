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
import java.util.UUID;

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
    		if(playerObject.getPlayer() == p) {
    			playerObject.setPlayerDead(true);
    		}
    	}
    }

    protected void registerHandler(){
        server.getPluginManager().registerEvents(this,plugin);
    }
    private boolean isPlayerDead(Player p) {
    	for(PlayerTemplate playerObject : plugin.playerInstanceList) {
    		if(playerObject.getPlayer() == p && playerObject.isPlayerDead()) {
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

    // Is Attacker the same with Recipient
    private boolean isSamePlayer(Player attacker, Player recipient) {
        return attacker != recipient;
    }

    private boolean canPlayerBeHit(PlayerTemplate receiver){
        return !receiver.isPlayerErased() && !receiver.isPlayerCompressed();
    }

    private boolean isObjectPlayer(Object obj){
        return obj instanceof Player;
    }
    @org.bukkit.event.EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player && isPVPEnabled) {
            Player damager = (Player) e.getDamager();
            Player receiver = (Player) e.getEntity();
            if(!isPlayerHoldingStick(damager) || !isSamePlayer(damager, receiver))return;

            for(PlayerTemplate playerClass : plugin.playerInstanceList) {
            	if(playerClass.getPlayer() == e.getDamager() && canPlayerBeHit(playerClass)) {
            		playerClass.getPlayerAbility().contactAbility(receiver);
            		return;
            	}
            }
        }
        else if( isObjectPlayer(e.getEntity()) && isObjectPlayer(e.getDamager()) && !isPVPEnabled){
            e.setCancelled(true);
        }
    }
    @org.bukkit.event.EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(isPlayerHoldingStick(e.getPlayer()) && isPlayerHoldingStick(e.getPlayer()) && isActionRightClick(e)) {
        	for(PlayerTemplate playerClass : plugin.playerInstanceList) {
        		if(playerClass.getPlayer() == e.getPlayer() && !playerClass.isPlayerErased() && !playerClass.isPlayerCompressed()) {
        			playerClass.getPlayerAbility().passiveAbility();
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
        if(plugin.hasStarted) {
	        setPlayerDeath(p);
	        p.setGameMode(GameMode.SPECTATOR);
        }
    }
    
    @org.bukkit.event.EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
    	e.getPlayer().sendMessage("Use /team to select team \nUse /ability to choose an ability");
    }


    /*
    Class Tests
     */

    private void testPlayerJoin(){
        Player p = null;
        PlayerJoinEvent e = new PlayerJoinEvent(p,"test");
        onPlayerJoin(e);
        e.getPlayer().chat
    }
}
