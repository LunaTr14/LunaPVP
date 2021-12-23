package me.luna.lunapvp;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.Random;

public class gameTimer implements Runnable{
    main plugin;
    public gameTimer(main plugin){
        this.plugin = plugin;
    }
    public void run(){
        while(plugin.hasGameStarted) {
            try {
                plugin.time = System.currentTimeMillis();
                Thread.sleep((long) 0.001f);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
