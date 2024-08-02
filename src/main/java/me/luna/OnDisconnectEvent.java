package me.luna;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnDisconnectEvent implements Listener {

    private Main plugin = null;

    public void registerHandler(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDisconnectEvent(PlayerQuitEvent e){

        e.getPlayer().setGameMode(GameMode.SPECTATOR);
        e.getPlayer().getInventory().clear();
        plugin.playerAbilityHashMap.remove(e.getPlayer().getDisplayName());

    }
}
