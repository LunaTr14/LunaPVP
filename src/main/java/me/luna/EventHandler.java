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
    HashMap<Player,Boolean> playerAbilityAllowed = new HashMap();


    private Main plugin;

    public void registerHandler(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    private boolean isPlayerHoldingStick(Player p){
        Material itemInHand = p.getInventory().getItemInMainHand().getType();
        if(itemInHand == Material.STICK){
            return true;
        }
        return false;
    }
    @org.bukkit.event.EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player activator = event.getPlayer();
        if(!playerAbilityAllowed.containsKey(activator)){
            playerAbilityAllowed.put(activator,true);
        }
        if(!playerAbilityAllowed.get(activator)){
            activator.sendMessage("Delay is still active");
            return;
        }
        if(isPlayerHoldingStick(activator) && plugin.playerAbilityHashMap.containsKey(activator) && playerAbilityAllowed.get(activator)){
            plugin.ABILITY_ARRAY[plugin.playerAbilityHashMap.get(activator)].activate(event);
            playerAbilityAllowed.put(activator,false);
            new BukkitRunnable() {
                @Override
                public void run() {
                    playerAbilityAllowed.put(activator,true);
                }
            }.runTaskLater(plugin,plugin.ABILITY_ARRAY[plugin.playerAbilityHashMap.get(activator)].delay * 20);
        }
    }

    @org.bukkit.event.EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event){
        Entity activator = event.getDamager();
        Entity damagedEntity = event.getEntity();

        if(isEntityPlayer(activator) && isEntityPlayer(damagedEntity) && isPvPAllowed){
            event.setCancelled(true);
        }
        if(!playerAbilityAllowed.containsKey((Player) activator)){
            playerAbilityAllowed.put((Player) activator,true);
        }
        if(!playerAbilityAllowed.get((Player) activator)){
            activator.sendMessage("Delay is still active");
            return;
        }
        else if(isEntityPlayer(activator) & isPvPAllowed && plugin.playerAbilityHashMap.containsKey(activator) && playerAbilityAllowed.get(activator)){
            Player playerDamager = (Player) activator;
            if(isPlayerHoldingStick(playerDamager)){
                plugin.ABILITY_ARRAY[plugin.playerAbilityHashMap.get(activator)].activate(event);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        playerAbilityAllowed.put((Player) activator,true);
                    }
                }.runTaskLater(plugin,plugin.ABILITY_ARRAY[plugin.playerAbilityHashMap.get(activator)].delay * 20);
            }
            activator.sendMessage(Double.toString(event.getDamage()));
            System.out.println(plugin.ABILITY_ARRAY[plugin.playerAbilityHashMap.get(activator)].delay);
        }
    }

    @org.bukkit.event.EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        if(plugin.gameController.hasGameStarted && isEntityPlayer(e.getEntity())){
            e.getEntity().setGameMode(GameMode.SPECTATOR);
            e.getEntity().getInventory().clear();
        }
    }

    private boolean isEntityPlayer(Entity e){
        if(e instanceof Player){
            return true;
        }
        return false;
    }
}
