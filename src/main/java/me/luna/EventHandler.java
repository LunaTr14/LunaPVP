/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Handles Events like Player Hit, Movement
*/
package me.luna;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class EventHandler implements Listener {
    public boolean isPvPAllowed = false;
    HashMap<Player, Boolean> playerAbilityAllowed = new HashMap();


    private Main plugin;

    public void registerHandler(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private boolean isPlayerHoldingStick(Player p) {
        Material itemInHand = p.getInventory().getItemInMainHand().getType();
        return itemInHand == Material.STICK;
    }

    private boolean isEntityPlayer(Entity e) {
        return e instanceof Player;
    }
    @org.bukkit.event.EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player activator = event.getPlayer();
        if (!playerAbilityAllowed.containsKey(activator)) {
            playerAbilityAllowed.put(activator, true);
        }
        if (!playerAbilityAllowed.get(activator)) {
            activator.sendMessage("Delay is still active");
            return;
        }
        if (isPlayerHoldingStick(activator)) {
            if (Main.ABILITY_ARRAY[plugin.playerAbilityHashMap.get(activator)].activate(event)) {
                playerAbilityAllowed.put(activator, false);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        playerAbilityAllowed.put(activator, true);
                    }
                }.runTaskLater(plugin, Main.ABILITY_ARRAY[plugin.playerAbilityHashMap.get(activator)].delay * 20L);
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!isEntityPlayer(event.getDamager())) {
            return;
        }

        if (isEntityPlayer(event.getEntity()) && !isPvPAllowed) {
            event.setCancelled(true);
            return;
        }

        Player playerDamager = (Player) event.getDamager();
        if (!playerAbilityAllowed.containsKey(playerDamager)) {
            playerAbilityAllowed.put(playerDamager, true);
        }

        // Verifies Delay
        if (!playerAbilityAllowed.get(playerDamager)) {
            playerDamager.sendMessage("Delay is still active");
            return;
        }

        if (isPlayerHoldingStick(playerDamager)) {
            if (Main.ABILITY_ARRAY[plugin.playerAbilityHashMap.get(playerDamager)].activate(event)) {
                playerAbilityAllowed.put(playerDamager, false);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        playerAbilityAllowed.put(playerDamager, true);
                    }
                }.runTaskLater(plugin, Main.ABILITY_ARRAY[plugin.playerAbilityHashMap.get(playerDamager)].delay * 20L);
            }
        }
    }

    @org.bukkit.event.EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (plugin.gameController.hasGameStarted && isEntityPlayer(e.getEntity())) {
            e.getEntity().setGameMode(GameMode.SPECTATOR);
            e.getEntity().getInventory().clear();
        }
    }
}
