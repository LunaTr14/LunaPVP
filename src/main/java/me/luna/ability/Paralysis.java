package me.luna.ability;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Paralysis extends AbilityTemplate{
    public Paralysis(){
        this.delay = 7;
    }

    public int POTION_LENGTH = 10 * 20;
    @Override
    public boolean activate(Event e) {
        if(isEventEntityHit(e)){
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) e;
            Player reciever = (Player) damageEvent.getEntity();
            reciever.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,POTION_LENGTH,9999));
            reciever.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,POTION_LENGTH,9999));
            return true;
        }
        return false;
    }
}
