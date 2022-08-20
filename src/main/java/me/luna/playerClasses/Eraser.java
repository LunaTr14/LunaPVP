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
    public void activatedAbility() {

    }

    @Override
    public void playerHitAbility(Player playerSpigotInstance) {
        for(PlayerTemplate playerInstance : plugin.playerInstanceList){
            if(playerInstance.() != playerSpigotInstance.getUniqueId() && !playerInstance.()){
                playerInstance.setIsErase(true);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        playerInstance.setIsErase(false);
                    }
                }.runTaskLater(plugin,60);
            }
        }
    }
}
