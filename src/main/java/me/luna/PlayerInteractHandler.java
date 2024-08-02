package me.luna;

import me.luna.ability.AbilityTemplate;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractHandler implements Listener {
    private volatile long tickDelay = System.currentTimeMillis();
    private Main plugin = null;
    public void registerHandler(Main plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if(tickDelay > System.currentTimeMillis() || e.getPlayer().getGameMode() == GameMode.SPECTATOR){
            return;
        }
        Player player = e.getPlayer();
        AbilityTemplate abilityObject = plugin.playerAbilityHashMap.get(player.getDisplayName());
        if(abilityObject.hasDelayCompleted() && abilityObject.isPlayerHoldingStick(player)) {
            abilityObject.activate(e);
        }
        player = null;
        abilityObject = null;
        System.gc();
        tickDelay = System.currentTimeMillis() + 1000;
    }
}
