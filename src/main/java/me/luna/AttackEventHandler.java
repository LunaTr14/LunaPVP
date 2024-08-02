package me.luna;

import me.luna.ability.AbilityTemplate;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AttackEventHandler implements Listener {

    private Main plugin = null;

    public void registerHandler(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void attackEvent(EntityDamageByEntityEvent e){
        if(!plugin.isPvPAllowed && e.getEntity() instanceof  Player){
            e.setCancelled(true);
            return;
        }
        if(e.getDamager() instanceof Player){
            Player damager = (Player) e.getDamager();
            AbilityTemplate abilityObject = plugin.playerAbilityHashMap.get(damager.getDisplayName());
            if(abilityObject.hasDelayCompleted() && abilityObject.isPlayerHoldingStick(damager)) {
                abilityObject.activate(e);
            }
            damager = null;
            abilityObject = null;
            System.gc();
        }
    }
}
