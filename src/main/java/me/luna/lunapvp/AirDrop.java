/*
Created By: Luna T
Edited Last: 29/3/2022
Purpose: Creates Airdrops during the game
*/
package me.luna.lunapvp;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.Random;

public class AirDrop {
    LinkedList<Material> airDropItemList = new LinkedList<>();
    World w;

    public AirDrop(){
        airDropItemList.add(Material.GOLDEN_APPLE);
        airDropItemList.add(Material.DIAMOND);
        airDropItemList.add(Material.IRON_ORE);
        airDropItemList.add(Material.GOLDEN_CARROT);
        airDropItemList.add(Material.SHIELD);
        airDropItemList.add(Material.ENDER_PEARL);
        airDropItemList.add(Material.BREWING_STAND);
        airDropItemList.add(Material.BLAZE_POWDER);
        airDropItemList.add(Material.NETHER_WART);
    }
    private int generateLimitedInt(int limit){
        Random rand = new Random();
        return (rand.nextInt(limit * 2) - limit);
    }
    private Location getRandomLocation(){
        int xCoordinate = generateLimitedInt(500);
        int zCoordinate = generateLimitedInt(500);
        Location loc = new Location(w, xCoordinate, w.getHighestBlockYAt(xCoordinate,zCoordinate) + 1, zCoordinate);
        return loc;
    }

    private ItemStack generateRandomItem(){
        Random rand = new Random();
        ItemStack itemStack = new ItemStack(airDropItemList.get(rand.nextInt(airDropItemList.size() - 1)),rand.nextInt(3));
        return itemStack;
    }
    public void spawnAirDrop(Server s){
        Location chestLocation = getRandomLocation();
        chestLocation.getBlock().setType(Material.CHEST);
        Chest block = (Chest) chestLocation.getBlock().getState();
        for(int i = 0; i < 5; i++) {
            block.getBlockInventory().addItem(generateRandomItem());
        }
        s.broadcastMessage("Airdrop has been droppped at: " + chestLocation.getBlockX() + ", " + chestLocation.getBlockZ());
    }


}
