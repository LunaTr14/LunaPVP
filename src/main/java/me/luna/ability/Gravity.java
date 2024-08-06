package me.luna.ability;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Gravity extends AbilityTemplate {

    public Gravity() {
        delay = 5;
    }

    @Override
    public boolean activate(Event e) {
        if (e instanceof  PlayerInteractEvent) {
            PlayerInteractEvent interactEvent = (PlayerInteractEvent) e;
            Player p = interactEvent.getPlayer();
            if (isRightClick(interactEvent.getAction())) {
                p.getPlayer().teleport(p.getLocation().add(0, 10, 0));
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 20 * 15, 1));
            }
        } else if (isEventEntityHit(e)) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) e;
            damageEvent.getEntity().teleport(damageEvent.getDamager().getLocation().add(0, 7.5, 0));
        }
        addDelay();
        return true;
    }
}
