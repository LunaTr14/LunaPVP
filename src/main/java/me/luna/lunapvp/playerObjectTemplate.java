package me.luna.lunapvp;

import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;

import me.luna.playerClasses.AbilityTemplate;

public class playerObjectTemplate {
	private AbilityTemplate ability;
	private boolean playerDead = false;
	private UUID playerUUID;
	private String teamID = "";
	private final int kills = 0;
	private boolean isErased = false;
	private final boolean isCompressed = false;
	
	protected playerObjectTemplate() {
		Random rand = new Random();
		int randomInt = rand.nextInt(999) * rand.nextInt(999) *rand.nextInt(999);
		this.teamID = Integer.toBinaryString(randomInt);
	}
	protected void setPlayerDeathStatus(boolean isPlayerDead) {
		this.playerDead = isPlayerDead;
	}
	protected void updateClassDetails(Player playerSpigotInstance, AbilityTemplate ability) {
		this.ability = ability;
		this.ability.setPlayer(playerSpigotInstance);
		this.playerUUID = playerSpigotInstance.getUniqueId();
	}
	protected boolean isPlayerDead() {
		return this.playerDead;
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
		this.isErased = b;
	}
	public boolean isPlayerErased(){
		return this.isErased;
	}
	public void setIsCompressed(boolean b){
		this.isErased = b;
	}
	public boolean getIsCompressed(){
		return this.isErased;
	}
	protected AbilityTemplate getAbility() {
		return ability;
	}
}
