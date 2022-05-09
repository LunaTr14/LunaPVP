/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: playerObject class to handle Player Actions and Status
 */

package me.luna.lunapvp;

import java.util.Random;
import java.util.UUID;

import me.luna.playerClasses.AbilityTemplate;
import org.bukkit.entity.Player;

public class PlayerTemplate {
	private volatile AbilityTemplate ability;
	private boolean isPlayerDead = false;
	private UUID playerUUID;
	private String teamID;
	private boolean isPlayerErased = false;
	
	protected PlayerTemplate() {
		Random rand = new Random();
		int randomInt = rand.nextInt(999) * rand.nextInt(999) *rand.nextInt(999);
		this.teamID = Integer.toBinaryString(randomInt);
	}
	protected void setPlayerDeathStatus(boolean isPlayerDead) {
		this.isPlayerDead = isPlayerDead;
	}
	protected void updateClassDetails(Player playerSpigotInstance, AbilityTemplate ability) {
		this.ability = ability;
		this.ability.setPlayer(playerSpigotInstance);
		this.playerUUID = playerSpigotInstance.getUniqueId();
	}
	protected boolean isPlayerDead() {
		return this.isPlayerDead;
	}
	protected String getTeamID() {
		return this.teamID;
	}
	protected void setTeamID(String team) {
		this.teamID = team;
	}
	public UUID getPlayer() {
		return this.playerUUID;
	}
	public void setIsErase(boolean b){
		this.isPlayerErased = b;
	}
	public boolean isPlayerErased(){
		return this.isPlayerErased;
	}
	public boolean getIsCompressed(){
		return this.isPlayerErased;
	}
	protected AbilityTemplate getAbility() {
		return ability;
	}
}
