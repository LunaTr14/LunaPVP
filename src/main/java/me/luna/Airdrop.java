package me.luna;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Airdrop {

    private static Material[] AIRDROP_MATERIAL_RARE = {Material.GOLDEN_APPLE, Material.DIAMOND};
    private static Material[] AIRDROP_MATERIAL_NORMAL = {Material.IRON_INGOT, Material.WATER_BUCKET, Material.COOKED_BEEF};
    private Location chestLocation = null;
    private Chest airDropChest = null;
    public Airdrop(Location loc){
        this.chestLocation = loc;
    }

    protected void spawnAirdrop(){
        chestLocation.getBlock().setType(Material.CHEST);
        if(chestLocation.getBlock().getType() == Material.CHEST) {
            this.airDropChest = (Chest) chestLocation.getBlock();
            this.generateItems();
            this.announceAidrop();
            return;
        }
        Bukkit.getLogger().log(new LogRecord(Level.WARNING, "Airdrop is not Type of Chest"));
    }

    private void  generateItems(){
        ItemStack[] items = {};
        for(int i = 0; i < 3; i++){
            items.
        }
        for(int i = 0; i < 7; i++){
            items[0] = new ItemStack();
        }
        this.airDropChest.getInventory().addItem();
    }

    protected void announceAidrop(){

    }
}
