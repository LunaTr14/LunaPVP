package me.luna.playerClasses;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Gravity extends AbilityTemplate{

    private static int PASSIVE_HEIGHT = 40;
    private static int OFFENSE_HEIGHT = 30;
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
    }
}
