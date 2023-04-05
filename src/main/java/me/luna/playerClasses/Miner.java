package me.luna.playerClasses;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Miner extends AbilityTemplate {
    private static int OFFENSE_RADIUS = 3;
    private static int PASSIVE_RADIUS = 5;
    private static Vector ADD_X = new Vector(1,0,0);
    private static Vector ADD_Y = new Vector(0,1,0);
    private static Vector ADD_Z = new Vector(0,0,1);

    private void destroyBlock(Location blockLocation){
        if(blockLocation.getBlock().getType() == Material.AIR)return;
        blockLocation.getBlock().breakNaturally();
    }
    private void mineArea(Location loc, int radius){
        Location cursorLocation = loc;
        for(int x = -radius; x  < radius; x++){
            for(int y = -radius; y < radius; y++){
                for(int z = -radius; z < radius; z++){
                    cursorLocation.add(ADD_Z);
                    destroyBlock(cursorLocation);
                }
                cursorLocation.add(ADD_Y);
                destroyBlock(cursorLocation);
            }
            cursorLocation.add(ADD_X);
            destroyBlock(cursorLocation);
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
