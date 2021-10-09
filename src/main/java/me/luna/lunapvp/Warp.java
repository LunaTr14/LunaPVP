package me.luna.lunapvp;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.LinkedList;
import java.util.Random;

public class Warp extends AbilityTemplate{
    @Override
    public void playerHitAbility(Player attackedPlayer) {
        if(!checkCooldown()){
            return;
        }
        attackedPlayer.teleport(attackedPlayer.getLocation().add(0,-25,0));
        cooldownTime = System.currentTimeMillis();
    }

    @Override
    public void activatedAbility() {
        if(!checkCooldown()){
            return;
        }
        Random rand = new Random();
        LinkedList<Player> playerLinkedList = new LinkedList<>();
        for(Player p : plugin.getServer().getOnlinePlayers()){
            playerLinkedList.add(p);
        }

        player.teleport(playerLinkedList.get(rand.nextInt(plugin.getServer().getOnlinePlayers().size())));
        cooldownTime = System.currentTimeMillis();
    }
}
