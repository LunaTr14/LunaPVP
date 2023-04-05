package me.luna.lunapvp;

import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.luna.playerClasses.AbilityTemplate;

public class PlayerInstance {
	private AbilityTemplate ability;
	public boolean isPlayerDead = false;
	private String team = "";
	private long lastActivation = 0;
	private UUID playerUUID;

	public PlayerInstance(UUID playerUUID){
		this.playerUUID = playerUUID;
	}

	public boolean hasCooldownPassed(){
		return lastActivation - System.currentTimeMillis() > Main.ABILITY_COOLDOWN_SECONDS;
	}
	protected UUID getPlayerUUID() {
		return this.playerUUID;
	}

	public void setAbility(AbilityTemplate abilityClass){
		this.ability = abilityClass.newInstance();
	}

	public void playerHitAbility(Player defender){
		if(!hasCooldownPassed())return;
		ability.playerHitAbility(defender);
		lastActivation = System.currentTimeMillis();
	}

	public void rightClickAbility(){
		if(!hasCooldownPassed())return;
		ability.rightClickAbility();
		lastActivation = System.currentTimeMillis();
	}

	public void setTeam(String team_id){
		this.team = team_id;
	}

	public String getTeam(){
		return this.team;
	}
}
