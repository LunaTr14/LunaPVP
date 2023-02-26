package me.luna.custom;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class AbilityTemplate {

    public void activate(Event e) {

    }
    protected boolean isEventPlayerInteract(Event e){
        if(e instanceof PlayerInteractEvent){
            return true;
        }
        return false;
    }

    protected boolean isEventEntityHit(Event e){
        if(e instanceof EntityDamageByEntityEvent){
            return true;
        }
        return false;
    }
    protected boolean isLeftClick(Action action){
        if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK){
            return true;
        }
        return false;
    }
    protected boolean isRightClick(Action action){
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
            return true;
        }
        return false;
    }

    protected Entity getDamager(EntityDamageByEntityEvent e){
        return e.getDamager();
    }

    protected Entity getTarget(EntityDamageByEntityEvent e){
        return e.getEntity();
    }

}
