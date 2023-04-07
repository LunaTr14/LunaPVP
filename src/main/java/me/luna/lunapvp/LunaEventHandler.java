package me.luna.lunapvp;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.UUID;

public class LunaEventHandler implements Listener {

   private Main plugin;

   public LunaEventHandler(Main plugin){
      this.plugin = plugin;
   }

   private PlayerInstance getPlayerFromList(UUID playerUUID){
      for(PlayerInstance instance : plugin.playerList){
         if(instance.getPlayerUUID() == playerUUID){
            return instance;
         }
      }
      return null;
   }
   private boolean isPlayerHoldingStick(Player p){
      if(p.getInventory().getItemInMainHand().getType() == Material.STICK) {
         return true;
      }
      return false;
   }
   private boolean isEventRightClick(PlayerInteractEvent event){
      Action action = event.getAction();
      if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK){
         return true;
      }
      return false;
   }

   private boolean isEntityPlayer(Entity entity){
      return entity instanceof Player;
   }
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event){
      Player player = event.getPlayer();
      UUID playerUUID = player.getUniqueId();
      if(!(isEventRightClick(event) | isPlayerHoldingStick(player) | plugin.isPlayerInList(playerUUID)))return;
      PlayerInstance instance = getPlayerFromList(playerUUID);
      instance.rightClickAbility(event);
   }
   @EventHandler
   public void onEntityDamage(EntityDamageByEntityEvent event){
      if(!(isEntityPlayer(event.getDamager()) & isEntityPlayer(event.getEntity()))) return;
      Player damagerPlayer = (Player) event.getDamager();
      UUID damagerUUID = damagerPlayer.getUniqueId();
      if(!plugin.isPvPEnabled){
         event.setCancelled(true);
         return;
      }
      if(!plugin.isPlayerInList(damagerPlayer.getUniqueId()))return;
      if(!isPlayerHoldingStick(damagerPlayer)) return;
      PlayerInstance damagerInstance = getPlayerFromList(damagerUUID);
      damagerInstance.playerHitAbility(event);
   }
}
