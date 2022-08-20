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
	protected volatile AbilityTemplate abilityClass;
	private boolean isPlayerDead = false;
	private Player player;
	protected String teamID;
	protected boolean isPlayerErased = false;


	private void setTeamRandom(){
		Random rand = new Random();
		int randomInt = rand.nextInt(999) * rand.nextInt(999) *rand.nextInt(999);
		this.teamID = Integer.toBinaryString(randomInt);
	}
	protected PlayerTemplate(){setTeamRandom();}

	protected void setAbility(AbilityTemplate ability){
		this.abilityClass = ability;
	}

	protected void setPlayer(Player p){
		this.player = p;
	}
	protected void setPlayerDead(boolean bool){
		this.isPlayerDead = bool;
	}
	protected void setTeam(String teamID){
		this.teamID = teamID;
	}
	protected Player getPlayer(){
		return this.player;
	}
	protected boolean isPlayerErased(){
		return this.isPlayerErased;
	}
	protected boolean isPlayerDead(){
		return this.isPlayerDead;
	}
}
