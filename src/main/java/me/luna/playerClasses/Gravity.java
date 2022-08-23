package me.luna.playerClasses;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Gravity extends AbilityTemplate{
	public Gravity() {
		this.className = "Gravity";
		this.classID = 5;
	}
    public void passiveAbility() {
        if(!checkCooldown()){
            return;
        }
        player.teleport(player.getLocation().add(0,25,0));
        player.removePotionEffect(PotionEffectType.SLOW_FALLING);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 600, 1));
        cooldownTime = System.currentTimeMillis();
    }

    public void playerHitAbility(Player attackedPlayer) {
        if(!checkCooldown()){
            return;
        }
        attackedPlayer.teleport(attackedPlayer.getLocation().add(0,25,0));
        cooldownTime = System.currentTimeMillis();
    }
}
