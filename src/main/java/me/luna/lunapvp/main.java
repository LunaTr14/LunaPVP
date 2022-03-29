/*
Created By: Luna T
Edited Last: 29/3/2022
Purpose: Main Function to start game
 */


package me.luna.lunapvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.luna.playerClasses.AbilityTemplate;
import me.luna.playerClasses.Cannon;
import me.luna.playerClasses.Ghost;
import me.luna.playerClasses.Gravity;
import me.luna.playerClasses.Medusa;
import me.luna.playerClasses.Miner;
import me.luna.playerClasses.UltraDamage;
import me.luna.playerClasses.Warp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class main extends JavaPlugin {
    private EventHandler eventHandler;
    private GameHandler gameHandler;
    volatile public LinkedList<playerObjectTemplate> playerInstanceList = new LinkedList<playerObjectTemplate>();
    protected boolean hasGameStarted = false;
    long gameInitailiseTime;
    public void onEnable() {
        playerInstanceList = new LinkedList<>();
        eventHandler = new EventHandler(this);
        gameHandler = new GameHandler(this);
        this.getServer().getPluginManager().registerEvents(eventHandler, this);
    }
    private void chooseClass(Player sender, AbilityTemplate playerClass) {
    	try {
    		for(playerObjectTemplate playerObject : playerInstanceList) {
    			if(playerObject.getPlayer() == sender.getUniqueId()){
    				playerInstanceList.remove(playerObject);
    			}
    		}
    	}
    	catch (Exception e) {
			System.out.println(e);
		}
    	playerObjectTemplate p = new playerObjectTemplate();
    	playerClass.setPlugin(this);
    	p.updateClassDetails(sender, playerClass);
    	this.playerInstanceList.add(p);
        sender.sendMessage("You have picked: " + p.getAbility().getClassName());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp() && label.equalsIgnoreCase("start")) {
            gameHandler.startGameTimer(this);
            gameInitailiseTime = System.currentTimeMillis();
            this.hasGameStarted = true;
            this.getServer().broadcastMessage("Use Wooden sticks to activate ability, \ndependent on your class it may be left click, right click or PlayerHit");
        }
        // Switch-Case to select Player's Class /ability [Class Name]
            if(label.equalsIgnoreCase("ability") && args.length != 0 && sender instanceof Player && !hasGameStarted){
                String argument0 = args[0].toLowerCase();
                switch (argument0) {
                    case("miner"):
                        chooseClass((Player) sender, new Miner());
                    case ("medusa"):
                        chooseClass((Player) sender, new Medusa());
                    case ("warp"):
                        chooseClass((Player) sender, new Warp());
                    case ("ultradamage"):
                        chooseClass((Player) sender, new UltraDamage());
                    case ("ghost"):
                        chooseClass((Player) sender, new Ghost());
                    case ("gravity"):
                        chooseClass((Player) sender, new Gravity());
                    case ("cannon"):
                        chooseClass((Player) sender, new Cannon());
                }
                return true;
            }
            else if(label.equalsIgnoreCase("team") && args.length == 0 && sender instanceof Player) {
            	for(playerObjectTemplate playerObject : playerInstanceList) {
            		if(this.getServer().getPlayer(playerObject.getPlayer()) == sender) {
            			this.getServer().getPlayer(playerObject.getPlayer()).sendMessage(playerObject.getTeamID());
            			return true;
            		}
            	}
            }
            else if(label.equalsIgnoreCase("team") && args.length != 0 && sender instanceof Player){
            	for(playerObjectTemplate p : playerInstanceList) {
            		p.setTeamID(args[0]);
            	}
                sender.sendMessage("You have chosen Team: " + args[0]);
                return true;
            }
        return false;
    }
    // Tab Completion for Class Selection
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if(command.getName().equalsIgnoreCase("ability") && args.length == 1){
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
