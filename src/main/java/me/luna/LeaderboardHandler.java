package me.luna;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class LeaderboardHandler {
    private ScoreboardManager scoreboardHandler = null;
    private Main plugin = null;
    public LeaderboardHandler(Main plugin){
        this.scoreboardHandler = plugin.getServer().getScoreboardManager();
        this.plugin = plugin;
    }

    private void updateWorldBorder(Objective obj){
        obj.getScore("World Border Radius: " + Math.round(plugin.getServer().getWorlds().get(0).getWorldBorder().getSize() / 2)).setScore(1);
    }

    private void updateDelay(Objective obj, Player p){
        if(!plugin.playerAbilityHashMap.containsKey(p.getDisplayName())){
            obj.getScore("Ability Cooldown: 0").setScore(2);
            return;
        }
        double coolDownTime = plugin.playerAbilityHashMap.get(p.getDisplayName()).nextActivation - System.currentTimeMillis();
        if(coolDownTime > 0){
            obj.getScore("Ability Cooldown: " +  String.format("%.2f",(float) (coolDownTime / 1000) + 1) + "s").setScore(2);
        }
    }

    private void updateTimeToShrink(Objective obj){
        if(plugin.gameTimer == null){
            return;
        }
        obj.getScore("Time till shrink: " + String.format("%.2f",(float) (plugin.gameTimer.borderShrinkPause - System.currentTimeMillis()) / 1000)).setScore(5);
    }

    public void updateScoreboard(Player p){
        p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
        Scoreboard newScoreboard = getNewScoreboard();
        Objective objective = createNewObjective(newScoreboard, "--STATS--");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        updateWorldBorder(objective);
        updateDelay(objective, p);
        updateTimeToShrink(objective);
        p.setScoreboard(newScoreboard);
    }

    private Objective createNewObjective(Scoreboard board, String title){
        return board.registerNewObjective(title,"dummy",title);
    }

    private Scoreboard getNewScoreboard(){
        Scoreboard newScoreboard = scoreboardHandler.getNewScoreboard();
        Objective objective = newScoreboard.registerNewObjective("test1","dummy","test2");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        return newScoreboard;
    }

}
