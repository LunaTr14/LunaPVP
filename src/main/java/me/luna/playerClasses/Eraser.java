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
        for(PlayerTemplate playerInstance : plugin.player_instance_list){
            if(playerInstance.getPlayer() != playerSpigotInstance.getUniqueId() && !playerInstance.isPlayerErased()){
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
