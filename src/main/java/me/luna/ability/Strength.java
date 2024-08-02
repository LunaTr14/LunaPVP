package me.luna.ability;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Strength extends AbilityTemplate{
    public final float DAMAGE_BOOSTER = 6f;

    public Strength(){
        delay = 0;
    }
    @Override
    public boolean activate(Event e) {
        if(!isEventEntityHit(e)) return false;
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) e;
        double baseDamage = damageEvent.getDamage();
        damageEvent.setDamage(baseDamage * DAMAGE_BOOSTER);
        return true;
        }
}
