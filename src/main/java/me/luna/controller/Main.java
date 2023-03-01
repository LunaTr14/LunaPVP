/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Main Function to start game
 */


package me.luna.controller;

import me.luna.custom.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin {
    private static final HashMap<String,Class> abilityMap = new HashMap<String, Class>();
    private final HashMap<Player, Class> playerAbilityMap = new HashMap<>();
    long startTime = 0;
    private final GameController gameController = new GameController();
    private final EventHandler eventHandler = new EventHandler();

    private void appendAbility(String abilityName, Class abilityClass){
        abilityMap.put(abilityName,abilityClass);
    }

    public long getNow(){
        return System.currentTimeMillis();
    }
    public Player[] getOnlinePlayers(){
        return (Player[]) this.getServer().getOnlinePlayers().toArray();
    }
    @Override
    public void onEnable() {
        appendAbility("GRAVITY", Gravity.class);
        appendAbility("CANNON", Cannon.class);
        appendAbility("STRENGTH", Strength.class);
        gameController.setPlugin(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(startTime > 0){

            sender.sendMessage("Command is invalid, Game has already Started");
            return true;
        }
        if(sender.isOp() && label.equalsIgnoreCase("game")){
            if(args[0].equalsIgnoreCase("start")) {
                gameController.startGame();
                broadcastToOperators("GameController activated");
                eventHandler.registerHandler(this);
                broadcastToOperators("EventHandler Registered");
                startTime = getNow();
            }
        }
        }
        else if(label.equalsIgnoreCase("class") && sender instanceof Player){
            if(abilitiesHashmap.containsKey(args[0].toLowerCase())){
                Class abilityClass = abilitiesHashmap.get(args[0].toLowerCase());
                this.playerAbilityHashMap.put(((Player) sender).getPlayer(),abilityClass);

    private void broadcastToOperators(String message){
        for(Player p : this.getOnlinePlayers()){
            if(p.isOp()){
                p.sendMessage(message);
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
