package me.luna.playerClasses;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class AbilityTemplate {
    public Player player;
    public int abilityCooldown = 5;

    public void rightClickAbility(PlayerInteractEvent event){}

    public void playerHitAbility(EntityDamageByEntityEvent event){

    }
    public AbilityTemplate createNewInstance(){
        return new AbilityTemplate();
    }
}
