package me.luna;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathEventHandler implements Listener {
    private Main plugin = null;
    public DeathEventHandler(Main plugin) {
        this.plugin = plugin;
    }
    public void registerHandler(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e){
        if(e instanceof Player){
            Player player = (Player) e;
            player.setGameMode(GameMode.SPECTATOR);
            plugin.playerAbilityHashMap.remove(player);
            System.gc();
        }
    }
}
