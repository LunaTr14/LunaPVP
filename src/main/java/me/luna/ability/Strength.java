package me.luna.ability;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Strength extends AbilityTemplate{
    private float DAMAGE_BOOSTER = 3f;

    int delay = 0;
    @Override
    public void activate(Event e) {
        if(!isEventEntityHit(e)) return;
        EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) e;
        double baseDamage = damageEvent.getDamage();
        damageEvent.setDamage(baseDamage * DAMAGE_BOOSTER);
        }
}
