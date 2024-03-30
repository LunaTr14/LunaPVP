/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Main Function to start game
 */


package me.luna;

import me.luna.ability.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin {

    public final static AbilityTemplate[] ABILITY_ARRAY = {new Strength(), new Gravity()};

    public volatile HashMap<Player,Integer> playerAbilityHashMap = new HashMap<>();
    public final GameController gameController = new GameController(this);
    public EventHandler eventHandler = new EventHandler();

    public long getNow(){
        return System.currentTimeMillis();
    }
    @Override
    public void onEnable() {
        eventHandler.registerHandler(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp() && label.equalsIgnoreCase("game")){
            if(args[0].equalsIgnoreCase("start") && ! gameController.hasGameStarted ) {
                gameController.startGame();
            }
            else if(args[0].equalsIgnoreCase("disable")){
                this.getPluginLoader().disablePlugin(this);

            }
        }

        else if(label.equalsIgnoreCase("class") && sender instanceof Player && args.length > 0) {
            String class_name = args[0];
            if(gameController.hasGameStarted){
                sender.sendMessage("Game has started, Unable to select Class");
                return true;
            }
            switch (class_name){
                    case "strength":
                        this.playerAbilityHashMap.put(((Player) sender).getPlayer(), 0);
                        sender.sendMessage("Strength has been Selected");
                        break;
                    case "gravity":
                        this.playerAbilityHashMap.put(((Player) sender).getPlayer(), 1);
                        sender.sendMessage("Gravity has been Selected");
                        break;
                default:
                    sender.sendMessage("Invalid Class Name");
                }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(command.getName().equalsIgnoreCase("game") && sender.isOp()){
            return List.of(new String[]{"start", "disable"});
        }
        else if (command.getName().equalsIgnoreCase("class")){
            return List.of(new String[]{"strength","gravity"});
        }
        return super.onTabComplete(sender, command, alias, args);
    }
}
