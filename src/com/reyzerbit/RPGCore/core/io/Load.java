package com.reyzerbit.RPGCore.core.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.reyzerbit.RPGCore.RPGCore;
import com.reyzerbit.RPGCore.core.Conversion;
import com.reyzerbit.RPGCore.core.structures.RPGCharacter;
import com.reyzerbit.RPGCore.core.structures.RPGPlayer;

public class Load {

	public static void load() {
		
		//Clears current data
		if(!RPGCore.playerSavesConfig.isEmpty()) RPGCore.playerSavesConfig.clear();
		if(!RPGCore.playerData.isEmpty()) RPGCore.playerData.clear();
		
		if(!RPGCore.playerDataDir.exists()) {
			
			RPGCore.playerDataDir.mkdirs();
			return;
			
		}
		
		//Warning log
		Logger log = Bukkit.getLogger();
		log.log(Level.WARNING, "RPGCore is loading saved data! Either the server is booting up or someone forced a save data reload, WHICH COULD CAUSE YOU TO LOSE DATA!");
		
		//YAML Filetype filter
		FilenameFilter filter = new FilenameFilter() {
			
	        @Override
	        public boolean accept(File f, String name) {
	        	
	            return name.endsWith(".yml");
	            
	        }
	        
	    };
	    
	    //Gets all yaml files in directory playerDataDir
	   	List<File> playerSaves = Arrays.asList(RPGCore.playerDataDir.listFiles(filter));
	   	
	   	//Puts all FileConfigurations into playerSavesConfig map
	   	playerSaves.forEach(f -> {
				
			if(f.getName().substring(0, f.getName().length() - 4).length() == 36) {
		       		
		       	RPGCore.playerSavesConfig.put(f.getName().substring(0, f.getName().length() - 4), YamlConfiguration.loadConfiguration(f));
	   			
	   		}
			
		});
	
	   	//For each pair in playerSavesConfig map
	   	for(Map.Entry<String, FileConfiguration> entry : RPGCore.playerSavesConfig.entrySet()) {
	
	   		UUID tempUUID = UUID.fromString(entry.getKey());
	   		RPGPlayer tempPlayer = new RPGPlayer(tempUUID);
	   		FileConfiguration tempConfig = entry.getValue();
	   		
	   		Set<String> count = tempConfig.getConfigurationSection("characters").getKeys(false);
	   		
	   		//For each character:
	   		for (String s : count) {
	   			
	   			//Get that character's config section,
	   			ConfigurationSection tempConfigSec = tempConfig.getConfigurationSection("characters." + s);
	   			
	   			//And add new character to tempPlayer based on each character config section's data
	   			RPGCharacter tempCharacter = new RPGCharacter();
	   			
	   			Conversion.checkNullString(tempConfigSec, "id", tempCharacter, tempConfigSec.getString("id"));
	   			Conversion.checkNullString(tempConfigSec, "name", tempCharacter, tempConfigSec.getString("name"));
	   			Conversion.checkNullString(tempConfigSec, "race", tempCharacter, tempConfigSec.getString("race"));
	   			Conversion.checkNullString(tempConfigSec, "class", tempCharacter, tempConfigSec.getString("class"));
	   			Conversion.checkNullString(tempConfigSec, "bodytype", tempCharacter, tempConfigSec.getString("bodytype"));
	   			Conversion.checkNullString(tempConfigSec, "hometown", tempCharacter, tempConfigSec.getString("hometown"));
	   			Conversion.checkNullString(tempConfigSec, "description", tempCharacter, tempConfigSec.getString("description"));
	   			
	   			Conversion.checkNullInt(tempConfigSec, "description", tempCharacter, tempConfigSec.getInt("age"));
	   			Conversion.checkNullInt(tempConfigSec, "description", tempCharacter, tempConfigSec.getInt("height"));
	   			
	   			tempPlayer.addCharacter(tempCharacter);
	
	   		}
	   		
	   		tempPlayer.setActiveCharacter(tempConfig.getString("active"));
	   		
	   		//Add tempUUID tempPlayer to Main.playerData
	   		RPGCore.playerData.put(tempUUID, tempPlayer);
	   		
	   	}
		
	}

}
