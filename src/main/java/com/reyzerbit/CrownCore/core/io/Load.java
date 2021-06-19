package com.reyzerbit.CrownCore.core.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.reyzerbit.CrownCore.CrownCore;
import com.reyzerbit.CrownCore.core.Conversion;
import com.reyzerbit.CrownCore.core.structures.CrownCharacter;
import com.reyzerbit.CrownCore.core.structures.CrownPlayer;

public class Load {

	public static void load() {
		
		//Clears current data
		if(!CrownCore.playerSavesConfig.isEmpty()) CrownCore.playerSavesConfig.clear();
		if(!CrownCore.playerData.isEmpty()) CrownCore.playerData.clear();
		
		if(!CrownCore.playerDataDir.exists()) {
			
			CrownCore.playerDataDir.mkdirs();
			return;
			
		}
		
		//Warning log
		Bukkit.getLogger().log(Level.WARNING, "[CrownCore] CrownCore is loading saved data! Either the server is booting up or someone forced a save data reload, WHICH COULD CAUSE YOU TO LOSE DATA!");
		
		//YAML Filetype filter
		FilenameFilter filter = new FilenameFilter() {
			
	        @Override
	        public boolean accept(File f, String name) {
	        	
	            return name.endsWith(".yml");
	            
	        }
	        
	    };
	    
	    //Gets all yaml files in directory playerDataDir
	   	List<File> playerSaves = Arrays.asList(CrownCore.playerDataDir.listFiles(filter));
	   	
	   	//Puts all FileConfigurations into playerSavesConfig map
	   	playerSaves.forEach(f -> {
				
			if(f.getName().substring(0, f.getName().length() - 4).length() == 36) {
		       		
		       	CrownCore.playerSavesConfig.put(f.getName().substring(0, f.getName().length() - 4), YamlConfiguration.loadConfiguration(f));
	   			
	   		}
			
		});
	
	   	//For each pair in playerSavesConfig map
	   	for(Map.Entry<String, FileConfiguration> entry : CrownCore.playerSavesConfig.entrySet()) {
	
	   		UUID tempUUID = UUID.fromString(entry.getKey());
	   		CrownPlayer tempPlayer = new CrownPlayer(tempUUID);
	   		FileConfiguration tempConfig = entry.getValue();
	   		
	   		Set<String> count = tempConfig.getConfigurationSection("characters").getKeys(false);
	   		
	   		//For each character:
	   		for (String s : count) {
	   			
	   			//Get that character's config section,
	   			ConfigurationSection tempConfigSec = tempConfig.getConfigurationSection("characters." + s);
	   			
	   			//And add new character to tempPlayer based on each character config section's data
	   			CrownCharacter tempCharacter = new CrownCharacter();
	   			
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
	   		CrownCore.playerData.put(tempUUID, tempPlayer);
	   		
	   	}
		
	}

}
