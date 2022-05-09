/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Creates Airdrops during the game
*/
package me.luna.lunapvp;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class AirDrop {

    static Material[] AIRDROP_ITEMS = {Material.GOLDEN_APPLE, Material.DIAMOND, Material.IRON_ORE,Material.GOLDEN_CARROT,Material.SHIELD,Material.ENDER_PEARL,Material.BREWING_STAND,Material.BLAZE_POWDER,Material.NETHER_WART};
    World worldInstance;
    // Creates Random Int within a limit
    private int randomInt(int limit){
        Random rand = new Random();
        return (rand.nextInt(limit * 2) - limit);
    }

    //Get's a random location within the world
    private Location get_random_location(){
        int x_coordinate = randomInt(500);
        int z_coordinate = randomInt(500);
        Location loc = new Location(worldInstance, x_coordinate, worldInstance.getHighestBlockYAt(x_coordinate,z_coordinate) + 1, z_coordinate);
        return loc;
    }

    //create's a randomized item stack
    private ItemStack createItemStack(){
        Random rand = new Random();
        ItemStack item_stack = new ItemStack(AIRDROP_ITEMS[rand.nextInt(AIRDROP_ITEMS.length - 1)], rand.nextInt(3));
        return item_stack;
    }
    public void spawnAirdrop(Server s){
        Location chest_location = get_random_location();
        chest_location.getBlock().setType(Material.CHEST);
        Chest block = (Chest) chest_location.getBlock().getState();
        for(int i = 0; i < 5; i++) {
            block.getBlockInventory().addItem(createItemStack());
        }
        s.broadcastMessage("Airdrop has been droppped at: " + chest_location.getBlockX() + ", " + chest_location.getBlockZ());
    }


}
