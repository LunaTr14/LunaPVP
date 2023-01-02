/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Handles Events like Player Hit, Movement
*/
package me.luna.controller;

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

public class EventHandler implements Listener {
    Main plugin;
    public EventHandler(Main p){
        plugin = p;
    }

    public void enableEventHandler(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }
}
