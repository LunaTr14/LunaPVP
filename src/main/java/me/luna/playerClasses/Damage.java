package me.luna.playerClasses;


import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Damage extends AbilityTemplate{

    int DAMAGE = 8;

    public Damage() {
        this.abilityCooldown = 2;
    }

    @Override
    public void playerHitAbility(EntityDamageByEntityEvent hitEvent) {
        Player p = (Player) hitEvent.getEntity();
        if(p.getHealth() - DAMAGE > 0){
            p.setHealth(p.getHealth() - DAMAGE);
        }
    }
    @Override
    public AbilityTemplate createNewInstance() {
        return new Damage();
    }
}
