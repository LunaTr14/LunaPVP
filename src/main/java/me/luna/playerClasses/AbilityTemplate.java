package me.luna.playerClasses;

import net.md_5.bungee.api.chat.hover.content.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class AbilityTemplate {
    public Player player;

    public void setPlayer(Player player){
        this.player = player;
    }
    public void rightClickAbility(PlayerInteractEvent event){}

    public void playerHitAbility(EntityDamageByEntityEvent event){

    }
    public AbilityTemplate createNewInstance(){
        return new AbilityTemplate();
    }
}
