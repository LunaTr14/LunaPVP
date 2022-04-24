/*
Created By: Luna T
Edited Last: 25/4/2022
Purpose: playerObject class to handle Player Actions and Status
 */

package me.luna.lunapvp;

import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.luna.playerClasses.AbilityTemplate;

public class PlayerTemplate {
	private AbilityTemplate ability;
	private boolean is_player_dead = false;
	private UUID player_uuid;
	private String team_id;
	private boolean is_player_erased = false;
	
	protected PlayerTemplate() {
		Random rand = new Random();
		int randomInt = rand.nextInt(999) * rand.nextInt(999) *rand.nextInt(999);
		this.team_id = Integer.toBinaryString(randomInt);
	}
	protected void setPlayerDeathStatus(boolean isPlayerDead) {
		this.is_player_dead = isPlayerDead;
	}
	protected void updateClassDetails(Player playerSpigotInstance, AbilityTemplate ability) {
		this.ability = ability;
		this.ability.setPlayer(playerSpigotInstance);
		this.player_uuid = playerSpigotInstance.getUniqueId();
	}
	protected boolean isPlayerDead() {
		return this.is_player_dead;
	}
	protected String getTeamID() {
		return this.team_id;
	}
	protected void setTeamID(String team) {
		this.team_id = team;
	}
	public UUID getPlayer() {
		return this.player_uuid;
	}
	public void setIsErase(boolean b){
		this.is_player_erased = b;
	}
	public boolean isPlayerErased(){
		return this.is_player_erased;
	}
	public boolean getIsCompressed(){
		return this.is_player_erased;
	}
	protected AbilityTemplate getAbility() {
		return ability;
	}
}
