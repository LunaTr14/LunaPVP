/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Main Function to start game
 */


package me.luna.lunapvp;

import me.luna.abilities.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin {
    private volatile EventHandler eventHandler;
    private volatile GameController gameController;
    public volatile  LinkedList<LunaPlayerClass> lunaPlayerList = new LinkedList<LunaPlayerClass>();
    private static String[] AUTO_FILL = {"Gravity","Ghost","Cannon","UltraDamage","Warp","Medusa","Miner"};
    protected boolean hasStarted = false;
    public void onEnable() {
        lunaPlayerList = new LinkedList<>();
        eventHandler = new EventHandler(this);
        gameController = new GameController(this,this.getServer().getWorld("world"));
    }

    public void broadcastMessage(String message){
        getServer().broadcastMessage(message);
    }

    private void startGame(){
        this.hasStarted = true;
        broadcastMessage("Use Wooden sticks to activate ability, \ndependent on your class it may be left click, right click or PlayerHit");
    }
    private LunaPlayerClass createLunaPlayerClassObject(Player sender, PvPClass abilityClass){
        LunaPlayerClass LunaPlayerClass = new LunaPlayerClass();
        LunaPlayerClass.setPlayer(sender.getPlayer());
        LunaPlayerClass.setAbility(abilityClass);
        abilityClass.setPlugin(this);
        return LunaPlayerClass;
    }

    public LunaPlayerClass findTemplateFromPlayer(Player p){
        for(LunaPlayerClass playerClass : lunaPlayerList){
            if(playerClass.getPlayer() == p) return playerClass;
        }
        return null;
    }

    private void selectClass(Player sender, String className){

        LunaPlayerClass lunaPlayer = new LunaPlayerClass();
        switch (className) {
            case("miner"):
                lunaPlayer = createLunaPlayerClassObject(sender, new Miner());
            case ("medusa"):
                lunaPlayer = createLunaPlayerClassObject(sender,new Medusa());
            case ("warp"):
                lunaPlayer = createLunaPlayerClassObject(sender,new Warp());
            case ("ultradamage"):
                lunaPlayer = createLunaPlayerClassObject(sender,new UltraDamage());
            case ("ghost"):
                lunaPlayer = createLunaPlayerClassObject(sender,new Ghost());
            case ("gravity"):
                lunaPlayer = createLunaPlayerClassObject(sender,new Gravity());
            case ("cannon"):
                lunaPlayer = createLunaPlayerClassObject(sender,new Cannon());
        }
        this.lunaPlayerList.add(lunaPlayer);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp() && label.equalsIgnoreCase("start") && !hasStarted && sender instanceof Player) {
            this.startGame();
            return true;
        }

        else if(label.equalsIgnoreCase("ability") && args.length > 0 && sender instanceof Player && !hasStarted){
            String abilityName = args[0].toLowerCase();
            selectClass((Player) sender, abilityName);
            return true;
        }

        else if(label.equalsIgnoreCase("team") && args.length > 0 && sender instanceof Player){
            findTemplateFromPlayer((Player) sender).setTeam(args[0]);
            sender.sendMessage("You have chosen Team: " + args[0]);
            return true;
        }
        sender.sendMessage("Error on command sent | Command Used: " + label);
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of(AUTO_FILL);
    }

    public LinkedList<LunaPlayerClass> getLunaPlayerClassList(){
        return this.getLunaPlayerClassList();
    }
}
