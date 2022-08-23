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
	private volatile AbilityTemplate abilityClass;
	private boolean isPlayerDead = false;
	private Player player;
	protected String teamID;
	private boolean isPlayerErased = false;

	private boolean isCompressed = false;


	private void setTeamRandom(){
		Random rand = new Random();
		int randomInt = rand.nextInt(999) * rand.nextInt(999) *rand.nextInt(999);
		this.teamID = Integer.toBinaryString(randomInt);
	}
	public PlayerTemplate(){setTeamRandom();}

	public void setAbility(AbilityTemplate ability){
		this.abilityClass = ability;
	}

	public void setPlayer(Player p){
		this.player = p;
	}
	public void setPlayerDead(boolean bool){
		this.isPlayerDead = bool;
	}
	public void setTeam(String teamID){
		this.teamID = teamID;
	}
	public Player getPlayer(){
		return this.player;
	}
	public boolean isPlayerErased(){
		return this.isPlayerErased;
	}
	public boolean isPlayerDead(){
		return this.isPlayerDead;
	}

	public AbilityTemplate getPlayerAbility(){
		return this.abilityClass;
	}
	public void setCompressed(boolean b){
		this.isCompressed = b;
	}

	public boolean isPlayerCompressed(){
		return this.isCompressed;
	}
	public void setErased(boolean b){
		this.isPlayerErased = b;
	}

}
