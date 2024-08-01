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

    public final static AbilityTemplate[] ABILITY_ARRAY = {new Strength(), new Gravity(), new Paralysis()};

    public volatile HashMap<Player,Integer> playerAbilityHashMap = new HashMap<>();
    public WorldBorderHandler worldBorderHandler = null;
    public EventHandler eventHandler = new EventHandler();

    public long getNow(){
        return System.currentTimeMillis();
    }
    private static boolean IS_DEBUG = true;
    @Override
    public void onEnable() {
        eventHandler.registerHandler(this);
        this.worldBorderHandler = new WorldBorderHandler(getServer().getWorlds().get(0));
        if(IS_DEBUG){
            this.worldBorderHandler.testBorderSize();
            this.worldBorderHandler.testBorderCenter();
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp() && label.equalsIgnoreCase("game")){
            if(args[0].equalsIgnoreCase("start") && ! gameController.hasGameStarted ) {
                gameController.startGame();
                this.getServer().broadcastMessage("Game has begun, Ability activation item is stick");
            }
            else if(args[0].equalsIgnoreCase("disable")){
                this.getPluginLoader().disablePlugin(this);
            }
            else if(args[0].equalsIgnoreCase("pvp")){
                if(args[1].equalsIgnoreCase("enable")){
                    this.eventHandler.isPvPAllowed = true;
                }
                else if (args[1].equalsIgnoreCase("disable")){
                    this.eventHandler.isPvPAllowed = false;
                }
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
                    case "paralysis":
                        this.playerAbilityHashMap.put(((Player) sender).getPlayer(), 2);
                        sender.sendMessage("Paralysis has been Selected");
                        break;
                default:
                    sender.sendMessage("Invalid Class Name");
                }
        }
        else if (label.equalsIgnoreCase("about") && args.length > 0){
            String class_name = args[0];
            switch (class_name) {
                case "strength":
                    sender.sendMessage("Strength multiplies damage of a stick by a factor of "+ ((Strength) ABILITY_ARRAY[0]).DAMAGE_BOOSTER);
                    break;
                case "gravity":
                    sender.sendMessage("Gravity on Right Click: Teleports player to the sky and applies slow falling");
                    sender.sendMessage("Gravity on Player attack: Teleports attacked player to the sky without slow falling");
                    break;
                case "paralysis":
                    sender.sendMessage("Paralysis on Player Attack: Applies Blindness and Slowness" + ((Paralysis) ABILITY_ARRAY[2]).POTION_LENGTH / 20 +" seconds");
                    break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(command.getName().equalsIgnoreCase("game") && sender.isOp()){
            return List.of(new String[]{"start", "disable"});
        }
        else if (command.getName().equalsIgnoreCase("class") || command.getName().equalsIgnoreCase("about")){
            return List.of(new String[]{"strength","gravity","paralysis"});
        }
        return super.onTabComplete(sender, command, alias, args);
    }
}
