package me.luna.custom;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Strength extends AbilityTemplate{
    private float DAMAGE_BOOSTER = 1.25f;
    @Override
    public void activate(Event e) {
        if(!isEventEntityHit(e)) return;
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) e;
        Entity damager = getDamager(damageEvent);
        Entity target = getTarget(damageEvent);
        if(isEntityPlayer(target) && isEntityPlayer(damager)){
            double baseDamage = damageEvent.getDamage();
            damageEvent.setDamage(baseDamage * DAMAGE_BOOSTER);
            }
        }

    private boolean isEntityPlayer(Entity e){
        if(e instanceof Player){
            return true;
        }
        return false;
    }
}
