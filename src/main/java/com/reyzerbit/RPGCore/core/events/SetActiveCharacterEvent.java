package com.reyzerbit.RPGCore.core.events;

import java.util.UUID;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SetActiveCharacterEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	
	private final UUID playerUUID;
	private final String rpgCharacterId;
	
	private boolean isCancelled;
	
	public SetActiveCharacterEvent(UUID uuid, String id) {
		
		playerUUID = uuid;
		rpgCharacterId = id;
		
		isCancelled = false;
		
	}

	@Override
	public HandlerList getHandlers() {
		
		return handlers;
		
	}
	
	public static HandlerList getHandlerList() {
		
        return handlers;
        
    }

	public String getRpgCharacterId() {
		
		return rpgCharacterId;
		
	}

	public UUID getPlayerUUID() {
		
		return playerUUID;
		
	}

	@Override
	public boolean isCancelled() {

		return isCancelled;
		
	}

	@Override
	public void setCancelled(boolean cancel) {

		isCancelled = cancel;
		
	}

}
