package me.luna;

import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerHandler {

    Player player = null;
    public PlayerHandler(Player player){
        this.player = player;
    }

    public void teleportPlayerToWorldCenter(){
        WorldBorder border = player.getWorldBorder();
        if(border == null){
            return;
        }
        Location worldBorderCenter = border.getCenter();
        Location tpLocation = worldBorderCenter.add(0,3,0); // +3 y so no teleporting inside blocks
        player.teleport(tpLocation);
    }

    public void addPotion(PotionEffectType potionType, double durationTicks, int strength  ) {
        PotionEffect effect = new PotionEffect(potionType, (int) durationTicks,strength);
        player.addPotionEffect(effect);
    }

    public void testTeleportPlayerToWorldCenter(){
        teleportPlayerToWorldCenter();
        WorldBorder border = player.getWorldBorder();
        try{
            assert player.getLocation().getX() == border.getCenter().getX();
            assert player.getLocation().getZ() == border.getCenter().getZ();
            System.out.println("Center Teleport FUNCTIONING");
        }
        catch (Exception e){
            System.err.println("Player Border Teleport Fail " + player.getLocation());
        }

    }

    public void testAddPotion(){
        addPotion(PotionEffectType.BLINDNESS,100, 2);
        try{
            assert this.player.getActivePotionEffects().contains(PotionEffectType.BLINDNESS) ;
            System.out.println("POTIONS FUNCTIONING");
        }
        catch (Exception e){
            System.err.println("Player does not contain PotionEffect\n Player Potions: " + player.getActivePotionEffects());
        }
    }
}
