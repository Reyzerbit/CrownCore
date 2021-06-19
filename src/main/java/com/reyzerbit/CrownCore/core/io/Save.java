package com.reyzerbit.CrownCore.core.io;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.reyzerbit.CrownCore.CrownCore;
import com.reyzerbit.CrownCore.core.structures.CrownCharacter;
import com.reyzerbit.CrownCore.core.structures.CrownPlayer;

public class Save {

	public static void save() {
		
		for(Map.Entry<UUID, CrownPlayer> entry : CrownCore.playerData.entrySet()) {
			
			File saveFile = new File(CrownCore.playerDataDir, (entry.getKey().toString() + ".yml"));
			
			if(saveFile.exists()) {
				
				saveFile.delete();
				
			}
	
			FileConfiguration saveConfig = YamlConfiguration.loadConfiguration(saveFile);
			
			if( entry.getValue().getActiveCharacter() != null) {
			
				saveConfig.set("active", entry.getValue().getActiveCharacter().getCharacterID());
				
			}
			
			ConfigurationSection charSection = saveConfig.createSection("characters");
			
			for(CrownCharacter rpgChar : entry.getValue().getCharacters()) {
				
				ConfigurationSection tempCharSection = charSection.createSection(rpgChar.getCharacterID());
	
				tempCharSection.set("id", rpgChar.getCharacterID());
				tempCharSection.set("name", rpgChar.getName());
				tempCharSection.set("race", rpgChar.getRace());
				tempCharSection.set("class", rpgChar.getPClass());
				tempCharSection.set("age", rpgChar.getAge());
				tempCharSection.set("height", rpgChar.getHeight());
				tempCharSection.set("bodytype", rpgChar.getBodytype());
				tempCharSection.set("hometown", rpgChar.getHometown());
				tempCharSection.set("description", rpgChar.getDescription());
				
			}
			
			try {
				saveConfig.save(saveFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
	   		
	   	}
		
	}

}
