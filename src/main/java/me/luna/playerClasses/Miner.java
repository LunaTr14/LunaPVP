package me.luna.playerClasses;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Miner extends AbilityTemplate{
	
	public Miner() {
		this.className = "Miner";
		this.classID = 3;
	}
    @Override
    public void passiveAbility() {
        if(!checkCooldown()){
            return;
        }
        for(int z = -3; z <3; z++){
            for(int x = -3; x < 3; x++){
                for(int y = -3; y < 3; y++){
                    if(z == 0 && x ==0){
                        continue;
                    }
                    else {
                        player.getWorld().getBlockAt(player.getLocation().add(x, y, z)).breakNaturally();
                    }
                }
            }
        }
        cooldownTime = System.currentTimeMillis();
    }

    @Override
    public void contactAbility(Player attackedPlayer) {
        if(!checkCooldown()){
            return;
        }
        ItemStack[] itemList = attackedPlayer.getInventory().getContents();
        Random rand = new Random();
        int randomInt = rand.nextInt(itemList.length - 1);
        attackedPlayer.getInventory().remove(itemList[randomInt]);
        cooldownTime = System.currentTimeMillis();
    }
}
