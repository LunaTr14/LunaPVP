package me.luna.lunapvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.luna.playerClasses.AbilityTemplate;
import me.luna.playerClasses.Cannon;
import me.luna.playerClasses.Ghost;
import me.luna.playerClasses.Gravity;
import me.luna.playerClasses.Medusa;
import me.luna.playerClasses.Miner;
import me.luna.playerClasses.UltraDamage;
import me.luna.playerClasses.Warp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public final class main extends JavaPlugin {
    private EventHandler eventHandler;
    private GameHandler gameHandler;
    private LinkedList<playerInstance> playerInstanceList = new LinkedList<playerInstance>();
    protected boolean hasGameStarted = false;
    public void onEnable() {
        playerInstanceList = new LinkedList<>();
        eventHandler = new EventHandler(this);
        gameHandler = new GameHandler(this);
        this.getServer().getPluginManager().registerEvents(eventHandler, this);
    }

    public void onDisable() {
    }
    
    private void chooseClass(Player sender, AbilityTemplate playerClass) {
    	playerInstance p = new playerInstance();
    	playerClass.setPlugin(this);
    	p.updateClassDetails(sender, playerClass);
    	this.playerInstanceList.add(p);
        sender.sendMessage("You have picked: " + p.getAbility().getClassName());
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp() && label.equalsIgnoreCase("start")){
            gameHandler.startGameTimer(this);
            eventHandler.updateAbilityList(playerInstanceList);
            this.getServer().broadcastMessage("Use Wooden sticks to activate ability, \n dependent on your class it may be left click, right click or PlayerHit");
            new BukkitRunnable() {
                @Override
                public void run() {
                    eventHandler.isPVPAllowed = true;
                    getServer().broadcastMessage("PVP has been enabled");
                }
            }.runTaskLater(this,8400);
            return true;
        }
            if(label.equalsIgnoreCase("ability") && args.length != 0 && sender instanceof Player && !hasGameStarted){
                if(args[0].equalsIgnoreCase("Gravity")){
                	chooseClass((Player) sender, new Gravity());
                }
                else if(args[0].equalsIgnoreCase("Cannon")){
                	chooseClass((Player) sender, new Cannon());
                }
                else if(args[0].equalsIgnoreCase("Ghost")){
                	chooseClass((Player) sender, new Ghost());
                }
                else if(args[0].equalsIgnoreCase("UltraDamage")){
                	chooseClass((Player) sender, new UltraDamage());
                }
                else if(args[0].equalsIgnoreCase("Warp")){
                	chooseClass((Player) sender, new Warp());
                }
                else if(args[0].equalsIgnoreCase("Medusa")){
                	chooseClass((Player) sender, new Medusa());
                }
                else if(args[0].equalsIgnoreCase("Miner")){
                	chooseClass((Player) sender, new Miner());
                }
                else {
                	return false;
                }
                return true;
            }
            else if(label.equalsIgnoreCase("team") && args.length == 0 && sender instanceof Player) {
            	for(playerInstance p : playerInstanceList) {
            		if(this.getServer().getPlayer(p.getPlayer()) == (Player) sender) {
            			this.getServer().getPlayer(p.getPlayer()).sendMessage(p.getTeamID());
            			return true;
            		}
            	}
            }
            else if(label.equalsIgnoreCase("team") && args.length != 0 && sender instanceof Player){
            	for(playerInstance p : playerInstanceList) {
            		p.setTeamID(args[0]);
            	}
                sender.sendMessage("You have chosen Team: " + args[0]);
                return true;
            }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if(command.getName().equalsIgnoreCase("ability") && args.length >= 0){
            if(sender instanceof Player){
                list.add("Gravity");
                list.add("Ghost");
                list.add("Cannon");
                list.add("UltraDamage");
                list.add("Warp");
                list.add("Medusa");
                list.add("Miner");
            }
        }
        return list;
    }
}