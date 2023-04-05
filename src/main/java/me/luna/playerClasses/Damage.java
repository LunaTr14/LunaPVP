package me.luna.playerClasses;


import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Damage extends AbilityTemplate{
    private static float DAMAGE_MULTIPLIER = 0.1f;

    private double increaseDamage(double damage){
        return damage + (damage * DAMAGE_MULTIPLIER);
    }
    public Damage() {}

    @Override
    public void playerHitAbility(EntityDamageByEntityEvent hitEvent) {
        double base_damage = hitEvent.getDamage();
        hitEvent.setDamage(increaseDamage(base_damage));
    }
}
