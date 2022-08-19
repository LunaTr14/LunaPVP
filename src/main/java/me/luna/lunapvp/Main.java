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

    protected boolean hasStarted = false;
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
    private void selectClass(Player sender, String className){
        switch (className) {
            case("miner"):
                setPlayerClass(sender, new Miner());
            case ("medusa"):
                setPlayerClass( sender, new Medusa());
            case ("warp"):
                setPlayerClass( sender, new Warp());
            case ("ultradamage"):
                setPlayerClass( sender, new UltraDamage());
            case ("ghost"):
                setPlayerClass( sender, new Ghost());
            case ("gravity"):
                setPlayerClass( sender, new Gravity());
            case ("cannon"):
                setPlayerClass( sender, new Cannon());
        }
    }
    private PlayerTemplate createPlayerTemplateObject(Player sender, AbilityTemplate abilityClass){
        PlayerTemplate playerTemplate = new PlayerTemplate();
        abilityClass.setPlugin(this);
        playerTemplate.updateClassDetails(sender , abilityClass);
        return playerTemplate;
    }

    private void setPlayerClass(Player sender, AbilityTemplate abilityClass) {
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

        PlayerTemplate playerTemplate = createPlayerTemplateObject(sender,abilityClass);
    	this.playerInstanceList.add(playerTemplate);
        sender.sendMessage("You have picked: " + playerTemplate.getAbility().getClassName());
    }

    private boolean isStartCommand(CommandSender sender, String label){
        return sender.isOp() && label.equalsIgnoreCase("start") && !hasStarted;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(isStartCommand(sender,label)) {
            initialGameTime = System.currentTimeMillis();
            this.hasStarted = true;
            this.getServer().broadcastMessage("Use Wooden sticks to activate ability, \ndependent on your class it may be left click, right click or PlayerHit");
        }

        else if(label.equalsIgnoreCase("ability") && args.length != 0 && sender instanceof Player && !hasStarted){
            String abilityName = args[0].toLowerCase();
            selectClass((Player) sender, abilityName);
            return true;
        }
        else if(label.equalsIgnoreCase("team") && args.length != 0 && sender instanceof Player){
            for(PlayerTemplate p : playerInstanceList) {
                p.setTeamID(args[0]);
            }
            sender.sendMessage("You have chosen Team: " + args[0]);
            return true;
        }
        sender.sendMessage("Error on command sent | Command " + label);
        return false;
    }


    // Tab Completion for Class Selection
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String[] onCompletionList = {"Gravity","Ghost","Cannon","UltraDamage","Warp","Medusa","Miner"};
        return List.of(onCompletionList);
    }
}
