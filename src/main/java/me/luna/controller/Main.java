/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Main Function to start game
 */


package me.luna.controller;

import me.luna.custom.abilities.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin {
    static HashMap<String, Class> abilitiesHashmap;
    volatile HashMap<Player, Class> playerAbilityHashMap;
    HashMap<Player,String> playerTeamHashMap;
    private static LinkedList<String> abilityLinkedList;
    boolean hasGameStarted = false;
    private EventHandler eventHandler;
    private GameController gameController;
    private static void initAbilityMap(){
        abilitiesHashmap.put("gravity", Gravity.class);
        abilitiesHashmap.put("cannon", Cannon.class);
        abilitiesHashmap.put("ultradamage", UltraDamage.class);
    }

    private static void initAbilityList(){
        abilityLinkedList.add("gravity");
        abilityLinkedList.add("cannon");
        abilityLinkedList.add("ultradamage");
    }

    private void startGame(){
        this.hasGameStarted = true;
        this.eventHandler.enableEventHandler();
    }
    @Override
    public void onEnable() {
        initAbilityMap();
        initAbilityList();
        eventHandler = new EventHandler(this);
        //TODO Edit getWorld to plugin config
        gameController = new GameController(this,this.getServer().getWorld("world"));
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(hasGameStarted){
            sender.sendMessage("Command is invalid, Game has already Started");
            return true;
        }
        if(sender.isOp() && label.equalsIgnoreCase("game")){
            if(args[0].equalsIgnoreCase("start"))startGame();
        }
        else if(label.equalsIgnoreCase("class") && sender instanceof Player){
            if(abilitiesHashmap.containsKey(args[0].toLowerCase())){
                Class abilityClass = abilitiesHashmap.get(args[0].toLowerCase());
                this.playerAbilityHashMap.put(((Player) sender).getPlayer(),abilityClass);

            }
        }
        else if(label.equalsIgnoreCase("team") && sender instanceof Player){
            this.playerTeamHashMap.put(((Player) sender).getPlayer(),args[0].toLowerCase());
        }
        return false;
    }

    private String generateRandomTeam(){
        Random rand = new Random();
        String team = String.valueOf(rand.nextDouble());
        return team;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(alias.equalsIgnoreCase("class")){
            return abilityLinkedList;
        }
        return null;
    }
}
