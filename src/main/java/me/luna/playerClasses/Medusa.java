package me.luna.playerClasses;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Medusa extends AbilityTemplate{
	public Medusa() {
		this.classID = 4;
		this.className = "Medusa";
	}
    public void playerHitAbility(Player attackedPlayer) {
        if(!checkCooldown()){
            return;
        }
        for(int z = -3; z < 3; z++){
            for(int x = -3; x < 3;x++){
                for(int y =-3; y < 3;y++){
                    player.getWorld().getBlockAt(attackedPlayer.getLocation().add(x,y,z)).setType(Material.STONE);
                }
            }
        }
        player.teleport(new Location(player.getWorld(), player.getLocation().getX(),player.getWorld().getHighestBlockYAt(player.getLocation()),player.getLocation().getZ()));
        cooldownTime = System.currentTimeMillis();
    }

    @Override
    public void passiveAbility() {
        if(!checkCooldown()){
            return;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 60,1000));
        cooldownTime = System.currentTimeMillis();
    }
}
