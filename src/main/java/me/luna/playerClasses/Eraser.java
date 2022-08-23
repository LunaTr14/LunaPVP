package me.luna.playerClasses;

import me.luna.lunapvp.PlayerTemplate;
import me.luna.lunapvp.PlayerTemplate;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Eraser extends AbilityTemplate{
    public Eraser(){
        this.classID = 111;
        this.className = "Eraser";
    }
    @Override
    public void contactAbility(Player playerSpigotInstance) {
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
