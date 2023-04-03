package me.luna.playerClasses;

import org.bukkit.entity.Player;

public class AbilityTemplate {
    public Player player;

    public AbilityTemplate(Player p){
        this.player = p;
    }
    public void rightClickAbility(){
    }

    public void playerHitAbility(Player defender){

    }

    public AbilityTemplate newInstance(){
        return new AbilityTemplate(player);
    }
}
