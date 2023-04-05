package me.luna.lunapvp;

import me.luna.playerClasses.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Main extends JavaPlugin {
    private LunaEventHandler eventHandler;
    private WorldHandler worldHandler;
    public volatile LinkedList<PlayerInstance> playerList = new LinkedList<PlayerInstance>();
    public static HashMap<String, AbilityTemplate> abilities = new HashMap<>();
    public static String WORLD_NAME = "world";
    private boolean hasGameStarted = false;

    private static void initAbilities(){
        abilities.put("miner", new Miner());
        abilities.put("gravity", new Gravity());
        abilities.put("damage", new Damage());
    }
    @Override
    public void onEnable() {
        initAbilities();
        eventHandler = new LunaEventHandler(this);
        worldHandler = new WorldHandler();
        worldHandler.setWorld(this.getServer().getWorld(WORLD_NAME));
        worldHandler.initWorldBorder();
        this.getServer().getPluginManager().registerEvents(eventHandler, this);
    }
    private void resetPlayerStatus(){
        for(PlayerInstance instance : playerList){
            Player p =this.getServer().getPlayer(instance.getPlayerUUID());
            p.setHealth(20);
            p.setSaturation(20);
            p.getInventory().clear();
        }
    }
    private boolean isSenderPlayer(CommandSender sender){
        return sender instanceof Player;
    }

    private UUID getPlayerUUID(Player p){
        return p.getUniqueId();
    }

    private PlayerInstance createNewPlayerInstance(UUID playerUUID, String abilityName) {
        PlayerInstance instance = new PlayerInstance(playerUUID);
        AbilityTemplate ability = abilities.get(abilityName).createNewInstance();
        ability.player = this.getServer().getPlayer(playerUUID);
        instance.setAbility(ability);
        return instance;
    }
    public boolean isPlayerInList(UUID playerUUID){
        for(PlayerInstance instance : playerList){
            if(playerUUID == instance.getPlayerUUID()){
                return true;
            }
        }
        return false;
    }

    private boolean doesAbilityExist(String abilityName){
        return abilities.containsKey(abilityName);
    }

    private void removePlayerFromList(UUID playerUUID){
        for(PlayerInstance instance: playerList){
            if(playerUUID == instance.getPlayerUUID()){
                playerList.remove(instance);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp() & label.equalsIgnoreCase("start")){
            if(hasGameStarted){
                sender.sendMessage("Game has started");

                return true;
            }
            worldHandler.startShrinkBorder(this);
            resetPlayerStatus();

        }
        if(label.equalsIgnoreCase("pvp_ability") & args.length > 0){
            if(hasGameStarted){
                sender.sendMessage("Game has started");
                return true;
            }
            if(!isSenderPlayer(sender)){
                sender.sendMessage("Sender is not Player");
                return true;
            }
            if(args.length <= 0) {
                sender.sendMessage("Ability Name not provided");
                return true;
            }
            String abilityName = args[0].toLowerCase();

            if(!doesAbilityExist(abilityName)){
                sender.sendMessage("Ability Does not Exist");
                return true;
            }
            Player player = (Player) sender;
            UUID playerUUID = getPlayerUUID(player);

            if(isPlayerInList(playerUUID)){
                removePlayerFromList(playerUUID);
            }
            PlayerInstance senderInstance = createNewPlayerInstance(playerUUID,abilityName);
            playerList.add(senderInstance);
            sender.sendMessage("Ability has been chosen: " + args[0]);
        }
        else if(label.equalsIgnoreCase("pvp_team")){
            if(hasGameStarted){
                return true;
            }
            if(!isSenderPlayer(sender)) {
                return true;
            }
            if(args.length <= 0){
                return true;
            }
            Player senderPlayer = (Player) sender;
            UUID uuidPlayer = senderPlayer.getUniqueId();
            String team_id = args[0].toLowerCase();
            if(isPlayerInList(uuidPlayer)){
                removePlayerFromList(uuidPlayer);
            }
            PlayerInstance playerInstance = new PlayerInstance(uuidPlayer);
            playerInstance.setTeam(team_id);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if(command.getName().equalsIgnoreCase("pvp_ability") && args.length >= 0){
            if(sender instanceof Player){
                list.add("Gravity");
                list.add("Damage");
                list.add("Miner");
            }
        }
        return list;
    }
}
