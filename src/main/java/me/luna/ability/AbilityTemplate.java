package me.luna.ability;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class AbilityTemplate {

    public int delay = 5;
    public double nextActivation = 0;
    public boolean hasDelayCompleted(){
        return nextActivation < System.currentTimeMillis();
    }
    public void addDelay(){
        this.nextActivation = System.currentTimeMillis() + (delay * 1000L);
    }
    public boolean activate(Event e) {
        return true;
    }

    public boolean isPlayerHoldingStick(Player p){
        return p.getInventory().getItemInMainHand().getType() == Material.STICK;
    }
    protected boolean isEventEntityHit(Event e){
        return e instanceof EntityDamageByEntityEvent;
    }
    protected boolean isLeftClick(Action action){
        return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
    }
    protected boolean isRightClick(Action action){
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }

}
