package me.luna.custom.abilities;

import org.bukkit.entity.Player;

public class PlayerTemplate {
    private String team;
    private Player player;
    private AbilityTemplate playerAbility;

    public boolean isPlayerActive = false;

    public void setPlayer(Player p ){
        this.player = p;
    }

    public void setTeam(String team){
        this.team = team;
    }

    public void setAbility(AbilityTemplate ability){
        this.playerAbility = ability;
    }

    public AbilityTemplate getPlayerAbility(){
        return this.playerAbility;
    }
}
