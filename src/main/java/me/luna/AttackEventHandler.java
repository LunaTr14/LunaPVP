package me.luna;

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
        if(e.getDamager() instanceof Player){
            Player damager = (Player) e.getDamager();
            plugin.playerAbilityHashMap.get(damager).activate(e);
        }
    }
}
