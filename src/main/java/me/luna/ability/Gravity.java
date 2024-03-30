package me.luna.ability;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Gravity extends AbilityTemplate{
    private float DAMAGE_BOOSTER = 3f;
    public int delay = 5;
    @Override
    public void activate(Event e) {
        if (isEventPlayerInteract(e)) {
            PlayerInteractEvent interactEvent = (PlayerInteractEvent) e;
            Player p = ((PlayerInteractEvent) e).getPlayer();
            if (isRightClick(interactEvent.getAction())) {
                p.getPlayer().teleport(p.getLocation().add(0, 10, 0));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 15, 1));
            }
        }
        else if(isEventEntityHit(e)){
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) e;
            damageEvent.getDamager().teleport(damageEvent.getDamager().getLocation().add(0,7.5,0));
        }
    }
}
