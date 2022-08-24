package me.luna.abilities;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Ghost extends AbilityTemplate{
	public Ghost() {
		this.classID = 6;
		this.className = "Ghost";
	}
    private void addPotions(){
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,90000,10));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1009000 ,9000));
    }

    private void removePotions(){
        player.removePotionEffect(PotionEffectType.BLINDNESS);
        player.removePotionEffect(PotionEffectType.SLOW);
    }
    public void passiveAbility() {
        if(!checkCooldown()){
            return;
        }
        player.setGameMode(GameMode.SPECTATOR);
        player.setFlySpeed(0.1f);
        addPotions();


        new BukkitRunnable() {
            @Override
            public void run() {
                player.setGameMode(GameMode.SURVIVAL);
                cooldownTime = System.currentTimeMillis();
                removePotions();
            }
        }.runTaskLater(this.plugin,130);

    }

    public void contactAbility(Player attackedPlayer) {
        if(!checkCooldown()){
            return;
        }
        attackedPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 140, 3));
        attackedPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 140, 3));
        cooldownTime = System.currentTimeMillis();
    }
}
