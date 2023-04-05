package me.luna.playerClasses;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Gravity extends AbilityTemplate{

    private static final int PASSIVE_HEIGHT = 20;
    private static final int OFFENSE_HEIGHT = 15;
    private Location calculateTPHeight(Location baseLocation, int height){
        return baseLocation.add(0,height,0);
    }
    @Override
    public void playerHitAbility(EntityDamageByEntityEvent event) {
        Player attackedPlayer = (Player) event.getEntity();
        Location attackedPlayerPos = attackedPlayer.getLocation();
        attackedPlayer.teleport(calculateTPHeight(attackedPlayerPos, OFFENSE_HEIGHT));
    }

    @Override
    public void rightClickAbility(PlayerInteractEvent event) {
        player.teleport(calculateTPHeight(player.getLocation(),PASSIVE_HEIGHT));
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,100,1));
    }
    @Override
    public AbilityTemplate createNewInstance() {
        return new Gravity();
    }
}
