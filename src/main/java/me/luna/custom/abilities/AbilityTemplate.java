package me.luna.custom.abilities;

import me.luna.controller.Main;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class AbilityTemplate {
    long cooldownTime = 0;
    protected Player player;
    String className = "";
    Main plugin;
    int classID = 0;

    public void setPlugin(Main p) {
        this.plugin = p;
    }
    public void usePassive(){}
    public void usePlayerHit(Player attackedPlayer){}
    public void userBlockHit (Block b){}
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
