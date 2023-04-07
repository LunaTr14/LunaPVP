package me.luna.playerClasses;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;

public class Miner extends AbilityTemplate {
    private static int OFFENSE_RADIUS = 5;
    private static int PASSIVE_RADIUS = 3;
    private HashMap<Material,Integer> dropItems = new HashMap<>();


    private void replaceType(Block block, Material type){
        block.setType(type);
        block.getState().update(true);
    }

    private void mineArea(Location loc, int radius){
        int xCursor = (int) loc.getX();
        int yCursor = (int) loc.getY();
        int zCursor = (int) loc.getZ();
        World w = loc.getWorld();

            for(int x = -radius; x  < radius; x++) {
                for (int y = -2; y < 4; y++) {
                    for (int z = -radius; z < radius ; z++) {
                        Block b = w.getBlockAt(x + xCursor,y + yCursor,z + zCursor);
                        Material blockMaterial = b.getType();
                        if(blockMaterial != Material.AIR & blockMaterial != Material.BEDROCK) {
                            if (!dropItems.containsKey(blockMaterial)) {
                                dropItems.put(blockMaterial, 0);
                            }
                            dropItems.put(blockMaterial, dropItems.get(blockMaterial) + 1);
                            replaceType(b,Material.AIR);
                            b.getState().update(true);
                        }
                    }
                }
            }
    }
    private void dropItems(Inventory inv){
        for(Material material: dropItems.keySet()){
            inv.addItem(new ItemStack(material,dropItems.get(material)));
        }
    }
    @Override
    public void playerHitAbility(EntityDamageByEntityEvent event) {
        mineArea(event.getEntity().getLocation(),OFFENSE_RADIUS);
    }

    @Override
    public void rightClickAbility(PlayerInteractEvent event) {
        mineArea(event.getPlayer().getLocation(),PASSIVE_RADIUS);
        dropItems(event.getPlayer().getInventory());
    }

    @Override
    public AbilityTemplate createNewInstance() {
        return new Miner();
    }
}
