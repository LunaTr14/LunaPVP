/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: Main Function to start game
 */


package me.luna;

import me.luna.ability.*;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin {

    public volatile HashMap<String,AbilityTemplate> playerAbilityHashMap = new HashMap<>();
    public WorldBorderHandler worldBorderHandler = null;
    public DeathEventHandler deathEventHandler = null;
    public JoinEventHandler joinEventHandler = null;
    public PlayerInteractHandler playerInteractHandler = null;
    public AttackEventHandler attackEventHandler = null;
    public LeaderboardHandler leaderboardHandler = null;
    public GameTimer gameTimer = null;
    public boolean isPvPAllowed = false;
    private boolean hasGameStarted = false;


    protected static double BORDER_PAUSE_SECONDS = 300; // Border has a delay for 5m until next zone
    protected static double BORDER_SHRINK_SPEED_SECONDS = 150; // Border takes 2.5 minutes to reach new size
    protected static double TIME_TILL_PVP = 180; // 3 Minutes until PvP is active
    protected static long SCOREBOARD_UPDATE_DELAY_MS = 100;
    protected static long BORDER_DEFAULT_SIZE = 5000;

    @Override
    public void onEnable() {
        this.leaderboardHandler = new LeaderboardHandler(this);
        this.deathEventHandler = new DeathEventHandler();
        deathEventHandler.registerHandler(this);
        this.joinEventHandler = new JoinEventHandler();
        joinEventHandler.registerHandler(this);
        this.playerInteractHandler = new PlayerInteractHandler();
        playerInteractHandler.registerHandler(this);
        this.attackEventHandler = new AttackEventHandler();
        attackEventHandler.registerHandler(this);

        //Registers all Event Handlers
        this.worldBorderHandler = new WorldBorderHandler(getServer().getWorlds().get(0));
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp() && label.equalsIgnoreCase("game")) {
            if (args[0].equalsIgnoreCase("start") && !hasGameStarted) {
                worldBorderHandler.setCenter(0,0);
                worldBorderHandler.shrinkBorder(BORDER_DEFAULT_SIZE,0);
                Location teleportLocation = worldBorderHandler.getBorderCenter();
                teleportLocation.setY(teleportLocation.getWorld().getHighestBlockYAt(teleportLocation) + 3 );
                for(Player onlinePlayer : this.getServer().getOnlinePlayers()){
                    onlinePlayer.setGameMode(GameMode.SURVIVAL);
                    onlinePlayer.setHealthScale(20);
                    onlinePlayer.setSaturation(20);
                    onlinePlayer.getInventory().clear();
                    onlinePlayer.teleport(teleportLocation);
                    leaderboardHandler.updateScoreboard(onlinePlayer);
                }
                teleportLocation = null;

                gameTimer = new GameTimer(this);
                gameTimer.startTimer();
                this.getServer().broadcastMessage("Game has begun, Ability activation item is stick");
                this.hasGameStarted = true;
            } else if (args[0].equalsIgnoreCase("disable")) {
                this.getPluginLoader().disablePlugin(this);
            }
            else{
                sender.sendMessage("This command does not exist");
            }
        }

        else if(label.equalsIgnoreCase("class") && sender instanceof Player && args.length > 0) {
            String className = args[0];
            if(hasGameStarted){
                sender.sendMessage("Game has started, Unable to select Class");
                return true;
            }
            String playerDisplayName = ((Player) sender).getDisplayName();
            switch (className){
                    case "strength":
                        this.playerAbilityHashMap.put(playerDisplayName,new Strength());
                        sender.sendMessage("Strength has been Selected");
                        break;
                    case "gravity":
                        this.playerAbilityHashMap.put(playerDisplayName,new Gravity());
                        sender.sendMessage("Gravity has been Selected");
                        break;
                    case "paralysis":
                        this.playerAbilityHashMap.put(playerDisplayName,new Paralysis());
                        sender.sendMessage("Paralysis has been Selected");
                        break;
                default:
                    sender.sendMessage("Invalid Class Name");
                }
        }
        else if (label.equalsIgnoreCase("about") && args.length > 0) {
            String className = args[0];
            switch (className) {
                case "strength":
                    sender.sendMessage("Strength multiplies damage of a stick by a factor of " + new Strength().DAMAGE_BOOSTER);
                    break;
                case "gravity":
                    sender.sendMessage("Gravity on Right Click: Teleports player to the sky and applies slow falling");
                    sender.sendMessage("Gravity on Player attack: Teleports attacked player to the sky without slow falling");
                    break;
                case "paralysis":
                    sender.sendMessage("Paralysis on Player Attack: Applies Blindness and Slowness" + new Paralysis().POTION_LENGTH / 20 + " seconds");
                    break;
                default:
                    sender.sendMessage("No class exists with that name");
            }

        }
        System.gc();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        String commandName = command.getName();
        if( sender.isOp()){
            if(commandName.equalsIgnoreCase("game")){
                return List.of(new String[]{"start", "disable"});
            }
        }
        if (commandName.equalsIgnoreCase("class") || commandName.equalsIgnoreCase("about")){
            return List.of(new String[]{"strength","gravity","paralysis"});
        }
        return super.onTabComplete(sender, command, alias, args);
    }
}
