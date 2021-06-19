package com.reyzerbit.CrownCore.core.events;

import java.util.UUID;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.reyzerbit.CrownCore.core.structures.CrownCharacter;

public class CreateCharacterEvent extends Event implements Cancellable {
	
	private static final HandlerList handlers = new HandlerList();
	
	private final CrownCharacter character;
	private final UUID playerUUID;
	private final String rpgCharacterId;
	
	private boolean isCancelled;
	
	public CreateCharacterEvent(UUID uuid, String id, CrownCharacter character) {
		
		this.character = character;
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

	public CrownCharacter getCharacter() {
		return character;
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
