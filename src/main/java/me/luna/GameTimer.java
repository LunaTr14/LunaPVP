package me.luna;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer {
    private Main plugin = null;
    protected double borderShrinkPause = System.currentTimeMillis() + (Main.BORDER_PAUSE_SECONDS * 1000);
    private double borderShrinkSpeedTime = Main.BORDER_SHRINK_SPEED_SECONDS;
    private double secondsUntilPvP = System.currentTimeMillis() + (Main.TIME_TILL_PVP * 1000);
    private long scoreBoardDelay = System.currentTimeMillis();
    public GameTimer(Main plugin){
        this.plugin = plugin;
    }

    public void startTimer(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if(borderShrinkPause <= System.currentTimeMillis()){

                    long borderSize = (long) plugin.worldBorderHandler.getBorderSize();
                    if(borderSize > 200) {
                        plugin.worldBorderHandler.shrinkBorder(borderSize / 2, (long) borderShrinkSpeedTime);
                    }
                    else if(50 <= borderSize && borderSize <= 200){
                        plugin.worldBorderHandler.shrinkBorder(borderSize - 50, (long) borderShrinkSpeedTime);
                    }
                    borderShrinkPause = System.currentTimeMillis() + (Main.BORDER_PAUSE_SECONDS * 1000);
                }
                if(System.currentTimeMillis() > scoreBoardDelay){
                    for(String username : plugin.playerAbilityHashMap.keySet()) {
                        Player player = plugin.getServer().getPlayer(username);
                        plugin.leaderboardHandler.updateScoreboard(player);
                        if(plugin.playerAbilityHashMap.get(player).hasDelayCompleted()){
                            VisualAudioHandler.playAbilityReady(player);
                        }
                    }
                    scoreBoardDelay = System.currentTimeMillis() + Main.SCOREBOARD_UPDATE_DELAY_MS;
                    }
                if (!plugin.isPvPAllowed && System.currentTimeMillis() >= secondsUntilPvP) {
                    plugin.isPvPAllowed = true;
                }
            }
        }.runTaskTimer(plugin,10,10);
    }
}
