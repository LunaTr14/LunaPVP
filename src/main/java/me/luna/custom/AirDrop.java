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

    private static Material[] AIRDROP_ITEMS = {Material.GOLDEN_APPLE, Material.DIAMOND, Material.IRON_ORE,Material.GOLDEN_CARROT,Material.SHIELD,Material.ENDER_PEARL,Material.BREWING_STAND,Material.BLAZE_POWDER,Material.NETHER_WART};
    private World worldInstance;

    // Generates a random set of x,z coordinates
    private Location getRandomLocation(){
        int x = randomInt(500);
        int z = randomInt(500);
        Location loc = new Location(worldInstance, x, this.getHighestBlock(x,z) + 1, z);
        return loc;
    }

    // Generates a random number inside a limit
    private int randomInt(int limit){
        Random rand = new Random();
        return (rand.nextInt(limit * 2) - limit);
    }
    // Generates a random set of items
    private ItemStack createItemStack(){
        Random rand = new Random();
        ItemStack itemStack = new ItemStack(AIRDROP_ITEMS[rand.nextInt(AIRDROP_ITEMS.length - 1)], rand.nextInt(3));
        return itemStack;
    }

    private long getHighestBlock(int xCoordinate, int zCoordinate){
        return worldInstance.getHighestBlockYAt(xCoordinate,zCoordinate);
    }
    public void setWorld(World w){
        this.worldInstance = w;
    }

    public World getWorld(){
        return this.worldInstance;
    }
    public void spawnAirdrop(Server s){
        Location chestLocation = getRandomLocation();
        chestLocation.getBlock().setType(Material.CHEST);
        Chest block = (Chest) chestLocation.getBlock().getState();
        for(int i = 0; i < 5; i++) {
            block.getBlockInventory().addItem(createItemStack());
        }
        s.broadcastMessage("Airdrop has been droppped at: " + chestLocation.getBlockX() + ", " + chestLocation.getBlockZ());
    }


}
