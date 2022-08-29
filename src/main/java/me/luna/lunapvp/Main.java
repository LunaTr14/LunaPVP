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
    volatile private LinkedList<LunaPlayerClass> templateList = new LinkedList<LunaPlayerClass>();
    private static String[] AUTO_FILL = {"Gravity","Ghost","Cannon","UltraDamage","Warp","Medusa","Miner"};
    protected boolean hasStarted = false;
    long initialGameTime = 0;
    public void onEnable() {
        templateList = new LinkedList<>();
        eventHandler = new EventHandler(this);
        gameController = new GameController(this,this.getServer().getWorld("world"));
    }

    private void startGame(){
        this.hasStarted = true;
        this.initialGameTime = System.currentTimeMillis();
        this.getServer().broadcastMessage("Use Wooden sticks to activate ability, \ndependent on your class it may be left click, right click or PlayerHit");
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
    private LunaPlayerClass createLunaPlayerClassObject(Player sender, PvPClass abilityClass){
        LunaPlayerClass LunaPlayerClass = new LunaPlayerClass();
        abilityClass.setPlugin(this);
        LunaPlayerClass.setPlayer(sender.getPlayer());
        LunaPlayerClass.setAbility(abilityClass);
        return LunaPlayerClass;
    }

    public LunaPlayerClass findTemplateFromPlayer(Player p){
        for(LunaPlayerClass template : templateList){
            if(template.getPlayer() == p) return template;
        }
        return null;
    }

    public void sendMessageToPlayer(Player p, String msg){

    }
    private void setPlayerClass(Player sender, PvPClass abilityClass) {
    	try {
            templateList.remove(findTemplateFromPlayer(sender));
    	}
    	catch (Exception e) {
			System.out.println(e);
		}

        LunaPlayerClass LunaPlayerClass = createLunaPlayerClassObject(sender,abilityClass);
    	this.templateList.add(LunaPlayerClass);
        sender.sendMessage("You have picked: " + LunaPlayerClass.getPlayerAbility().getClassName());
    }

    private boolean isStartCommand(CommandSender sender, String label){
        return sender.isOp() && label.equalsIgnoreCase("start") && !hasStarted;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(isStartCommand(sender,label)) this.startGame();
        else if(label.equalsIgnoreCase("ability") && args.length != 0 && sender instanceof Player && !hasStarted){
            String abilityName = args[0].toLowerCase();
            selectClass((Player) sender, abilityName);
            return true;
        }

        else if(label.equalsIgnoreCase("team") && args.length != 0 && sender instanceof Player){
            findTemplateFromPlayer((Player) sender).setTeam(args[0]);
            sender.sendMessage("You have chosen Team: " + args[0]);
            return true;
        }
        sender.sendMessage("Error on command sent | Command " + label);
        return false;
    }


    // Tab Completion for Class Selection
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of(AUTO_FILL);
    }

    public LinkedList<LunaPlayerClass> getLunaPlayerClassList(){
        return this.getLunaPlayerClassList();
    }
}
