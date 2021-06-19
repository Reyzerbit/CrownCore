package com.reyzerbit.CrownCore.core.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class CrownPlayer {
	
	private UUID playerUUID;
	
	private Vector<CrownCharacter> playerCharacters;
	
	private CrownCharacter activeCharacter;
	
	public CrownPlayer(UUID uuid) {
		
		playerUUID = uuid;
		
		playerCharacters = new Vector<CrownCharacter>();
		
	}

	public void addCharacter(CrownCharacter c) {
		
		playerCharacters.add(c);
		
	}
	
	public void setActiveCharacter(String id) {
		
		if(getCharacterIDs().contains(id)) {
			
			for(CrownCharacter c : getCharacters()) {
				
				if(c.getCharacterID().equals(id)) {
					
					setActive(c);
					return;
					
				}
				
			}
			
		}
		
	}
	
	private void setActive(CrownCharacter c) {
		
		activeCharacter = c;
		
	}
	
	public void removeCharacter(CrownCharacter c) {
		
		playerCharacters.remove(c);
		
	}
	
	public UUID getUUID() {
		
		return playerUUID;
		
	}
	
	public Vector<CrownCharacter> getCharacters() {
		
		return playerCharacters;
		
	}
	
	public CrownCharacter getActiveCharacter() {
		
		return activeCharacter;
		
	}
	
	public List<String> getCharacterIDs() {
		
		List<String> tempList = new ArrayList<String>();
		
		for(CrownCharacter c : playerCharacters) {
			
			tempList.add(c.getCharacterID());
			
		}
		
		return tempList;
		
	}
	
	public CrownCharacter getCharacter(String id) {
		
		for(CrownCharacter c : playerCharacters) {
			
			if(c.getCharacterID().toLowerCase().equals(id.toLowerCase())) return c;
			
		}
		
		return null;
		
	}
	
	public boolean hasActiveCharacter() {
		
		if(activeCharacter == null) return false;
		
		return true;
		
	}

}
