package me.luna.playerClasses;

import org.bukkit.entity.Player;

import me.luna.lunapvp.main;

public class AbilityTemplate {
    long cooldownTime = 0;
    protected Player player;
    String className = "";
    main plugin;
    int classID = 0;

    public void setPlugin(main p) {
    	this.plugin = p;
    }
    public void activatedAbility(){

    }

    public void playerHitAbility(Player attackedPlayer){

    }
    public void setPlayer(Player p) {
    	this.player = p;
    }
    public String getClassName() {
    	return this.className;
    }
    public Player getPlayer(){
    	return this.player;
    }
    protected boolean checkCooldown(){

        System.out.println(System.currentTimeMillis() - cooldownTime);
        return System.currentTimeMillis() - cooldownTime > 10000;
    }
}
