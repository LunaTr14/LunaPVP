package me.luna.custom.abilities;

import me.luna.controller.Main;
import org.bukkit.entity.Player;

public interface AbilityTemplate {
    long cooldownTime = 0;
    Player player = null;
    String className = "";
    int classID = 0;

    default void passiveAbility(){};
}
