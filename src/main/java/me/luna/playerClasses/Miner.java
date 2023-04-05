package me.luna.playerClasses;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Random;

public class Miner extends AbilityTemplate {
    private static int OFFENSE_RADIUS = 3;
    private static int PASSIVE_RADIUS = 5;

    private void destroyBlock(Location blockLocation){
        if(blockLocation.getBlock().getType() == Material.AIR)return;
        blockLocation.getBlock().breakNaturally();
    }
    private void mineArea(Location loc, int radius){
        Location end = loc.multiply(radius);
        Location center = loc.add(loc.add(end).multiply(0.5));
        for(int i = 0; i < radius;i++){
            destroyBlock(center.multiply(i));
        }
    }

    @Override
    public void playerHitAbility(EntityDamageByEntityEvent event) {
        super.playerHitAbility(event);
        mineArea(event.getEntity().getLocation(),OFFENSE_RADIUS);
    }

    @Override
    public void rightClickAbility(PlayerInteractEvent event) {
        mineArea(player.getLocation(),PASSIVE_RADIUS);
    }
}
