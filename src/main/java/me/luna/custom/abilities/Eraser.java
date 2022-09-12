package me.luna.custom.abilities;

import me.luna.custom.abilities.AbilityTemplate;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Eraser extends AbilityTemplate{
    public void usePlayerHit(Player p) {
        for(PlayerTemplate playerInstance : plugin.playerInstanceList){
            if(playerInstance.getPlayer() != playerSpigotInstance.getPlayer() && !playerInstance.isPlayerErased()){
                playerInstance.setErased(true);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        playerInstance.setErased(false);
                    }
                }.runTaskLater(plugin,60);
            }
        }
    }
}
