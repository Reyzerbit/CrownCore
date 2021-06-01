package com.reyzerbit.RPGCore.DataStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class RPGPlayer {
	
	private UUID playerUUID;
	
	private Vector<RPGCharacter> playerCharacters;
	
	private RPGCharacter activeCharacter;
	
	public RPGPlayer(UUID uuid) {
		
		playerUUID = uuid;
		
		playerCharacters = new Vector<RPGCharacter>();
		
	}

	public void addCharacter(RPGCharacter c) {
		
		playerCharacters.add(c);
		
	}
	
	public void setActiveCharacter(String id) {
		
		if(getCharacterIDs().contains(id)) {
			
			for(RPGCharacter c : getCharacters()) {
				
				if(c.getCharacterID().equals(id)) {
					
					setActive(c);
					return;
					
				}
				
			}
			
		}
		
	}
	
	private void setActive(RPGCharacter c) {
		
		activeCharacter = c;
		
	}
	
	public void removeCharacter(RPGCharacter c) {
		
		playerCharacters.remove(c);
		
	}
	
	public UUID getUUID() {
		
		return playerUUID;
		
	}
	
	public Vector<RPGCharacter> getCharacters() {
		
		return playerCharacters;
		
	}
	
	public RPGCharacter getActiveCharacter() {
		
		return activeCharacter;
		
	}
	
	public List<String> getCharacterIDs() {
		
		List<String> tempList = new ArrayList<String>();
		
		for(RPGCharacter c : playerCharacters) {
			
			tempList.add(c.getCharacterID());
			
		}
		
		return tempList;
		
	}

}
