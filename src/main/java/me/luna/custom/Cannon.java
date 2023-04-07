package me.luna.custom;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

public class Cannon extends AbilityTemplate{

    @Override
    public void activate(Event e)  {
        if(!isEventPlayerInteract(e))return;
        PlayerInteractEvent playerAction = ((PlayerInteractEvent) e);
        if(isLeftClick(playerAction.getAction())){
            Location loc = playerAction.getPlayer().getLocation();
            createExplosion(loc,5,false);
        }
    }

    private void createExplosion(Location loc, int strength, boolean fire){
        loc.getWorld().createExplosion(loc,strength,fire);
    }
}
