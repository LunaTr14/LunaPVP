package me.luna;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEventHandler implements Listener {

    private Main plugin = null;
    public void registerHandler(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(plugin.playerAbilityHashMap.containsKey(e.getPlayer().getDisplayName())){
            return;
        }
        plugin.playerAbilityHashMap.put(e.getPlayer().getDisplayName(),null);
    }

}
