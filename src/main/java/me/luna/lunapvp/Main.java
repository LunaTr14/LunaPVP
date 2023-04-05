package me.luna.lunapvp;

import me.luna.playerClasses.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.luna.playerClasses.UltraDamage;

import java.util.*;

public final class Main extends JavaPlugin {
    private LunaEventHandler eventHandler;
    private GameHandler gameHandler;
    public volatile LinkedList<PlayerInstance> playerList = new LinkedList<PlayerInstance>();
    public static HashMap<String, AbilityTemplate> abilities = new HashMap<>();
    protected boolean hasGameStarted = false;

    private static void initAbilities(){
        abilities.put("miner", new Miner());
        abilities.put("gravity", new Gravity());
        abilities.put("damage", new Damage());
    }
    public void onEnable() {
        initAbilities();
        playerInstanceList = new LinkedList<>();
        eventHandler = new LunaEventHandler(this);
        gameHandler = new GameHandler(this);
        this.getServer().getPluginManager().registerEvents(eventHandler, this);
    }

    private Player getPlayerFromUUID(UUID playerUUID){
        return this.getServer().getPlayer(playerUUID);
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
        instance.setAbility(ability);
        return instance;
    }
    private boolean isPlayerInList(UUID playerUUID){
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

        if(label.equalsIgnoreCase("pvp_class") & args.length > 0){
            if(hasGameStarted){
                sender.sendMessage("Game has started");
                return false;
            }
            if(!isSenderPlayer(sender)){
                sender.sendMessage("Sender is not Player");
                return false;
            }
            if(args.length <= 0) {
                sender.sendMessage("Ability Name not provided");
                return false;

            }
            String abilityName = args[0].toLowerCase();

            if(! doesAbilityExist(abilityName)){
                sender.sendMessage("Ability Does not Exist");
                return false;
            }
            Player player = (Player) sender;
            UUID playerUUID = getPlayerUUID(player);

            if(isPlayerInList(playerUUID)){
                removePlayerFromList(playerUUID);
            }
            PlayerInstance senderInstance = createNewPlayerInstance(playerUUID,abilityName);
            playerList.add(senderInstance);
        }
        else if(label.equalsIgnoreCase("pvp_team")){
            if(hasGameStarted){
                return false;
            }
            if(!isSenderPlayer(sender)) {
                return false;
            }
            if(args.length <= 0){
                return false;
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
        if(command.getName().equalsIgnoreCase("ability") && args.length >= 0){
            if(sender instanceof Player){
                list.add("Gravity");
                list.add("Ghost");
                list.add("Cannon");
                list.add("UltraDamage");
                list.add("Warp");
                list.add("Medusa");
                list.add("Miner");
            }
        }
        return list;
    }
}
