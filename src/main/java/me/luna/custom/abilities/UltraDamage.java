package me.luna.custom.abilities;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UltraDamage extends AbilityTemplate{
	public UltraDamage() {
		this.classID = 2;
		this.className = "UltraDamage";
	}
    @Override
    public void passiveAbility() {
        if(!checkCooldown()){
            return;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,100,0));
        cooldownTime = System.currentTimeMillis();
    }

    @Override
    public void contactAbility(Player attackedPlayer) {
        attackedPlayer.setHealth(attackedPlayer.getHealth() - 4);
    }
}
