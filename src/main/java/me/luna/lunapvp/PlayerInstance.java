package me.luna.lunapvp;

import java.util.UUID;

import me.luna.playerClasses.AbilityTemplate;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInstance {
	private AbilityTemplate ability;
	public boolean isPlayerDead = false;
	private String team = "";
	private long lastActivation = 0;
	private final UUID playerUUID;
	private final int ABILITY_COOLDOWN_SECONDS = 5;
	public PlayerInstance(UUID playerUUID){
		this.playerUUID = playerUUID;
	}

	private boolean hasCooldownPassed(){
		return System.currentTimeMillis() - lastActivation  > ABILITY_COOLDOWN_SECONDS * 1000L;
	}
	public UUID getPlayerUUID() {
		return this.playerUUID;
	}

	public void setAbility(AbilityTemplate abilityClass){
		this.ability = abilityClass;
	}

	public void sendCooldownMessage(Player p){
		p.sendMessage("Cooldown has not Finished\nRemaining: " + (ABILITY_COOLDOWN_SECONDS - ((System.currentTimeMillis() - lastActivation) / 1000)));
	}

	public void playerHitAbility(EntityDamageByEntityEvent event){
		if(isPlayerDead) return;
		if(!hasCooldownPassed()){
			sendCooldownMessage((Player) event.getDamager());
		return;
		}
		ability.playerHitAbility(event);
		lastActivation = System.currentTimeMillis();
	}

	public void rightClickAbility(PlayerInteractEvent event){
		if(isPlayerDead)return;
		if(!hasCooldownPassed()){
			sendCooldownMessage(event.getPlayer());
			return;
		}
		ability.rightClickAbility(event);
		lastActivation = System.currentTimeMillis();
	}

	public void setTeam(String team_id){
		this.team = team_id;
	}

	public String getTeam(){
		return this.team;
	}
}
