/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Handles Events like Player Hit, Movement
*/
package me.luna.lunapvp;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
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
    private void setPlayerDeath(Player p) {
    	for(LunaPlayerClass playerObject : plugin.getLunaPlayerClassList()) {
    		if(playerObject.getPlayer() == p) {
    			playerObject.setPlayerDead(true);
    		}
    	}
    }

    public EventHandler(Main plugin){
        this.plugin = plugin;
        this.server = plugin.getServer();
        registerHandler();
    }
    private void registerHandler(){
        server.getPluginManager().registerEvents(this,plugin);
    }

    private boolean isPlayerDead(Player p) {
    	for(LunaPlayerClass playerObject : plugin.getLunaPlayerClassList()) {
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

    private boolean canPlayerBeHit(LunaPlayerClass receiver){
        return !receiver.isPlayerErased() && !receiver.isPlayerCompressed();
    }

    private boolean isPlayerActive(Player p){
        LunaPlayerClass holderTemplate = findPlayerFromList(p);
        return !holderTemplate.isPlayerDead() && !holderTemplate.isPlayerErased() && !holderTemplate.isPlayerCompressed();
    }
    //List is playerInstance List
    private LunaPlayerClass findPlayerFromList(Player p){
        for(LunaPlayerClass playerClass : plugin.getLunaPlayerClassList()){
            if(playerClass.getPlayer() == p) return playerClass;
        }
        return null;
    }
    private void useContactAbility(Player hitter, Player receiver, EntityDamageByEntityEvent e){
        LunaPlayerClass receiverTemplate = findPlayerFromList(receiver);
        LunaPlayerClass hitterTemplate = findPlayerFromList(hitter);
        if(hitter != receiver && isPVPEnabled && canPlayerBeHit(receiverTemplate)){
            hitterTemplate.getPlayerAbility().contactAbility(receiver);
        }
        else{
            e.setCancelled(true);
        }
    }
    private void useRightClickAbility(Player activator,PlayerInteractEvent e){
        LunaPlayerClass activatorTemplate = findPlayerFromList(activator);
        // Shorten if Statement
        if(isPlayerHoldingStick(activator) && isActionRightClick(e) && isPlayerActive(activator)){
            activatorTemplate.getPlayerAbility().passiveAbility();
        }
    }
    @org.bukkit.event.EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) throws Exception{
        Player hitter = (Player) e.getDamager();
        Player receiver = (Player) e.getEntity();
        useContactAbility(hitter,receiver, e);
        return;
    }
    @org.bukkit.event.EventHandler
    public void onRightClick(PlayerInteractEvent e){
        useRightClickAbility(e.getPlayer(),e);
        return;
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
}
