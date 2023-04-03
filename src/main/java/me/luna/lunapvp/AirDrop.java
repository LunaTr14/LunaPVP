package me.luna.lunapvp;

import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class AirDrop {
    private World world;

    public AirDrop(World world){
        this.world = world;
    }
    Material AIRDROP_ITEMS[] = {Material.GOLDEN_APPLE,Material.DIAMOND,
            Material.IRON_ORE,Material.GOLDEN_CARROT,
            Material.SHIELD, Material.ENDER_PEARL,
            Material.BREWING_STAND, Material.BLAZE_POWDER,
            Material.NETHER_WART
    };
    private Location getBorderCenter(){
        return world.getWorldBorder().getCenter();
    }

    private double getBorderRadius() {
        return world.getWorldBorder().getSize() / 2;
    }
    private int getMaxXAxis(){
        return (int) (getBorderRadius() + getBorderCenter().getX());
    }

    private int getMaxZAxis(){
        return (int) getBorderRadius() + getBorderCenter().getZ());
    }

    private Location getRandomLocation(){
        Random rand = new Random();
        double x = rand.nextInt(getMaxXAxis()) - getBorderRadius();
        double z = rand.nextInt(getMaxZAxis()) -getBorderRadius();
        Location loc = new Location(world,x, world.getHighestBlockYAt((int) x,(int)z)+ 1, z);
        return loc;
    }

    private ItemStack generateRandomItem(){
        Random rand = new Random();
        int i = rand.nextInt(AIRDROP_ITEMS.length);
        int amount = rand.nextInt(5);
        return new ItemStack(AIRDROP_ITEMS[i],amount);
    }

    public void spawnAirDrop(Server s){
        Location chestLocation = getRandomLocation();
        chestLocation.getBlock().setType(Material.CHEST);
        Chest block = (Chest) chestLocation.getBlock().getState();
        for(int i = 0; i < 5; i++) {
            block.getBlockInventory().addItem(generateRandomItem());
        }
        s.broadcastMessage("Airdrop has been droppped at: " + Integer.toString(chestLocation.getBlockX()) + ", " + Integer.toString(chestLocation.getBlockZ()));
    }
}
