/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Main Function to start game
 */


package me.luna.lunapvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
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

public final class Main extends JavaPlugin {
    private PluginEventHandler plugin_event_handler;
    private MinigameHandler minigame_event_handler;
    volatile public LinkedList<PlayerTemplate> player_instance_list = new LinkedList<PlayerTemplate>();
    protected boolean has_game_started = false;
    long initial_game_time = 0;
    public void onEnable() {
        player_instance_list = new LinkedList<>();
        plugin_event_handler = new PluginEventHandler(this);
        minigame_event_handler = new MinigameHandler(this);
        this.getServer().getPluginManager().registerEvents(plugin_event_handler, this);
    }
    private void choose_class(Player sender, AbilityTemplate playerClass) {
    	try {
    		for(PlayerTemplate player_object : player_instance_list) {
    			if(player_object.getPlayer() == sender.getUniqueId()){
    				player_instance_list.remove(player_object);
    			}
    		}
    	}
    	catch (Exception e) {
			System.out.println(e);
		}
    	PlayerTemplate player_template = new PlayerTemplate();
    	playerClass.setPlugin(this);
    	player_template.updateClassDetails(sender, playerClass);
    	this.player_instance_list.add(player_template);
        sender.sendMessage("You have picked: " + player_template.getAbility().getClassName());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp() && label.equalsIgnoreCase("start")) {
            initial_game_time = System.currentTimeMillis();
            this.has_game_started = true;
            this.getServer().broadcastMessage("Use Wooden sticks to activate ability, \ndependent on your class it may be left click, right click or PlayerHit");
        }
        // Switch-Case to select Player's Class /ability [Class Name]
            if(label.equalsIgnoreCase("ability") && args.length != 0 && sender instanceof Player && !has_game_started){
                String argument0 = args[0].toLowerCase();
                switch (argument0) {
                    case("miner"):
                        choose_class((Player) sender, new Miner());
                    case ("medusa"):
                        choose_class((Player) sender, new Medusa());
                    case ("warp"):
                        choose_class((Player) sender, new Warp());
                    case ("ultradamage"):
                        choose_class((Player) sender, new UltraDamage());
                    case ("ghost"):
                        choose_class((Player) sender, new Ghost());
                    case ("gravity"):
                        choose_class((Player) sender, new Gravity());
                    case ("cannon"):
                        choose_class((Player) sender, new Cannon());
                }
                return true;
            }
            else if(label.equalsIgnoreCase("team") && args.length == 0 && sender instanceof Player) {
            	for(PlayerTemplate player_object : player_instance_list) {
            		if(this.getServer().getPlayer(player_object.getPlayer()) == sender) {
            			this.getServer().getPlayer(player_object.getPlayer()).sendMessage(player_object.getTeamID());
            			return true;
            		}
            	}
            }
            else if(label.equalsIgnoreCase("team") && args.length != 0 && sender instanceof Player){
            	for(PlayerTemplate p : player_instance_list) {
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
        String[] on_completion_list = {"Gravity","Ghost","Cannon","UltraDamage","Warp","Medusa","Miner"};
        return List.of(on_completion_list);
    }
}
