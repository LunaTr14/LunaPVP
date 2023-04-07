/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Handles Events like Player Hit, Movement
*/
package me.luna.controller;

import me.luna.custom.AbilityTemplate;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

public class EventHandler implements Listener {
    public HashMap<Player,AbilityTemplate> playerAbilities = new HashMap<Player,AbilityTemplate>();
    private boolean isPvPAllowed = true;

    public void registerHandler(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @org.bukkit.event.EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player activator = event.getPlayer();
        if(isPlayerHoldingStick(activator)){
            getPlayerAbility(activator).activate(event);
        }
    }

    @org.bukkit.event.EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        Entity activator = event.getDamager();
        Entity target = event.getEntity();
        if(isEntityPlayer(activator) & isEntityPlayer(target) & isPvPAllowed){
            Player playerDamager = (Player) activator;
            if(isPlayerHoldingStick(playerDamager)){
                getPlayerAbility(playerDamager).activate(event);
            }
        }
    }

    private AbilityTemplate getPlayerAbility(Player p){
        return playerAbilities.get(p);
    }
    private boolean isPlayerHoldingStick(Player p){
        Material itemInHand = p.getInventory().getItemInMainHand().getType();
        if(itemInHand == Material.STICK){
            return true;
        }
        return false;
    }
    private boolean isEntityPlayer(Entity e){
        if(e instanceof Player){
            return true;
        }
        return false;
    }

    protected void enablePvP(){
        isPvPAllowed = true;
    }

    protected void disablePvP(){
        isPvPAllowed = false;
    }
}
