package me.luna.ability;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class AbilityTemplate {

    public int delay = 5;
    public boolean activate(Event e) {
        return true;
    }
    protected boolean isEventPlayerInteract(Event e){
        return e instanceof PlayerInteractEvent;
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

    protected Entity getDamager(EntityDamageByEntityEvent e){
        return e.getDamager();
    }

    protected Entity getTarget(EntityDamageByEntityEvent e){
        return e.getEntity();
    }

}
