package me.luna;

import org.bukkit.scheduler.BukkitRunnable;

public class GameTimer {
    private Main plugin = null;
    private double borderShrinkPause;
    private long borderShrinkSpeedTime;
    private double secondsPassed = 0;
    private double secondsUntilPvP = 0;
    public GameTimer(Main plugin, double borderShrinkPause, long borderShrinkSpeedTime, double secondsUntilPvP){
        // borderShrinkSpeedTime the amount of seconds for previous Border Size to New Border Size
        // borderShrinkPause the amount of seconds it takes for the border to start another shrink

        this.borderShrinkPause= borderShrinkPause;
        this.borderShrinkSpeedTime = borderShrinkSpeedTime;
        this.plugin = plugin;
        this.secondsUntilPvP = secondsUntilPvP;
    }

    public void startTimer(){
        new BukkitRunnable() {
            @Override
            public void run() {
                if(secondsPassed > borderShrinkPause){
                    borderShrinkPause = borderShrinkPause + borderShrinkPause;
                    long borderSize = (long) plugin.worldBorderHandler.getBorderSize();
                    if(borderSize > 200) {
                        plugin.worldBorderHandler.shrinkBorder(borderSize / 2, borderShrinkSpeedTime);
                    }
                    else if(50 <= borderSize && borderSize <= 200){
                        plugin.worldBorderHandler.shrinkBorder(borderSize - 50, borderShrinkSpeedTime);
                    }
                }
                secondsPassed = secondsPassed + 1;
                if(secondsPassed % 30 == 0){
                    System.gc();
                }
                if (!plugin.isPvPAllowed && secondsUntilPvP % secondsPassed == 0) {
                    plugin.isPvPAllowed = true;
                }
            }
        }.runTaskTimer(plugin,20,20);
    }
}
