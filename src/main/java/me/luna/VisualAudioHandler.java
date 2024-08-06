package me.luna;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public final class VisualAudioHandler {
    public static void playAbilityActivate(Player p){
        p.playSound(p, Sound.BLOCK_ANVIL_FALL,0.75f,2.0f);
        p.playSound(p, Sound.BLOCK_ANVIL_FALL,0.75f,1.5f);
    }

    public static void playAbilityReady(Player p){
        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BELL,0.75f,1.0f);
        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BELL,0.75f,0.5f);
    }
    public static void playKillEffect(Player p){
        p.playSound(p, Sound.ENTITY_WITHER_SHOOT,0.75f,1.0f);
        p.playSound(p, Sound.ITEM_TRIDENT_THUNDER, 0.75f, 1.0f);
    }
}
