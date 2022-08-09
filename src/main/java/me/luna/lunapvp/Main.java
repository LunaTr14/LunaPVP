/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Main Function to start game
 */


package me.luna.lunapvp;

import me.luna.playerClasses.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin {
    private volatile EventHandler eventHanlder;
    private volatile GameController gameController;
    volatile public LinkedList<PlayerTemplate> playerInstanceList = new LinkedList<PlayerTemplate>();

    private HashMap<String,Class> playerClasses = new HashMap<String,Class>();
    protected boolean hasGameStarted = false;
    long initialGameTime = 0;
    public void onEnable() {
        playerInstanceList = new LinkedList<>();

        eventHanlder = new EventHandler();
        eventHanlder.setPlugin(this);
        eventHanlder.setServer(this.getServer());
        eventHanlder.registerHandler();


        gameController = new GameController();
        gameController.setServer(this.getServer());
        gameController.setWorld(this.getServer().getWorld("world"));
        gameController.setPlugin(this);
    }


    private void chooseClass(Player sender, AbilityTemplate playerClass) {
    	try {
    		for(PlayerTemplate player_object : playerInstanceList) {
    			if(player_object.getPlayer() == sender.getUniqueId()){
    				playerInstanceList.remove(player_object);
    			}
    		}
    	}
    	catch (Exception e) {
			System.out.println(e);
		}
    	PlayerTemplate playerTemplate = new PlayerTemplate();
    	playerClass.setPlugin(this);
    	playerTemplate.updateClassDetails(sender, playerClass);
    	this.playerInstanceList.add(playerTemplate);
        sender.sendMessage("You have picked: " + playerTemplate.getAbility().getClassName());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp() && label.equalsIgnoreCase("start")) {
            initialGameTime = System.currentTimeMillis();
            this.hasGameStarted = true;
            this.getServer().broadcastMessage("Use Wooden sticks to activate ability, \ndependent on your class it may be left click, right click or PlayerHit");
        }

        // Switch-Case to select Player's Class /ability [Class Name]
            if(label.equalsIgnoreCase("ability") && args.length != 0 && sender instanceof Player && !hasGameStarted){
                String className = args[0].toLowerCase();
                switch (className) {
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
            	for(PlayerTemplate player_object : playerInstanceList) {
            		if(this.getServer().getPlayer(player_object.getPlayer()) == sender) {
            			this.getServer().getPlayer(player_object.getPlayer()).sendMessage(player_object.getTeamID());
            			return true;
            		}
            	}
            }
            else if(label.equalsIgnoreCase("team") && args.length != 0 && sender instanceof Player){
            	for(PlayerTemplate p : playerInstanceList) {
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
        String[] onCompletionList = {"Gravity","Ghost","Cannon","UltraDamage","Warp","Medusa","Miner"};
        return List.of(onCompletionList);
    }
}
