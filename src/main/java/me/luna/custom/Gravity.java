package me.luna.custom;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Gravity extends AbilityTemplate{
    @Override
    public void activate(Event e) {
        if(!isEventEntityHit(e))return;
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) e;
        Entity target = damageEvent.getEntity();
        Entity damager = damageEvent.getDamager();
        if(isEntityPlayer(target) & isEntityPlayer(damager)){
            Location teleportLocation = target.getLocation().add(0,25,0);
            target.teleport(teleportLocation);
        }
    }

    private boolean isEntityPlayer(Entity e){
        if(e instanceof Player){
            return true;
        }
        return false;
    }
}
