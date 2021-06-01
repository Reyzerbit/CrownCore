package com.reyzerbit.RPGCore;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
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

import com.reyzerbit.RPGCore.DataStructures.RPGCharacter;
import com.reyzerbit.RPGCore.DataStructures.RPGPlayer;

public class IO {
	
	public static Map<String, FileConfiguration> playerSavesConfig;
	
	public static void save() {
		
		for(Map.Entry<UUID, RPGPlayer> entry : Main.playerData.entrySet()) {
			
			File saveFile = new File(Main.playerDataDir, (entry.getKey().toString() + ".yml"));
			
			if(saveFile.exists()) {
				
				saveFile.delete();
				
			}

			FileConfiguration saveConfig = YamlConfiguration.loadConfiguration(saveFile);
			
			if( entry.getValue().getActiveCharacter() != null) {
			
				saveConfig.set("active", entry.getValue().getActiveCharacter().getCharacterID());
				
			}
			
			ConfigurationSection charSection = saveConfig.createSection("characters");
			
			for(RPGCharacter rpgChar : entry.getValue().getCharacters()) {
				
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
	
	public static void load() {
		
		//Clears current data
		if(!playerSavesConfig.isEmpty()) playerSavesConfig.clear();
		if(!Main.playerData.isEmpty()) Main.playerData.clear();
		
		if(!Main.playerDataDir.exists()) {
			
			Main.playerDataDir.mkdirs();
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
       	List<File> playerSaves = Arrays.asList(Main.playerDataDir.listFiles(filter));
       	
       	//Puts all FileConfigurations into playerSavesConfig map
       	playerSaves.forEach(f -> {
    			
    		if(f.getName().substring(0, f.getName().length() - 4).length() == 36) {
    	       		
    	       	playerSavesConfig.put(f.getName().substring(0, f.getName().length() - 4), YamlConfiguration.loadConfiguration(f));
       			
       		}
			
		});

       	//For each pair in playerSavesConfig map
       	for(Map.Entry<String, FileConfiguration> entry : playerSavesConfig.entrySet()) {

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
       			
       			checkNullString(tempConfigSec, "id", tempCharacter, tempConfigSec.getString("id"));
       			checkNullString(tempConfigSec, "name", tempCharacter, tempConfigSec.getString("name"));
       			checkNullString(tempConfigSec, "race", tempCharacter, tempConfigSec.getString("race"));
       			checkNullString(tempConfigSec, "class", tempCharacter, tempConfigSec.getString("class"));
       			checkNullString(tempConfigSec, "bodytype", tempCharacter, tempConfigSec.getString("bodytype"));
       			checkNullString(tempConfigSec, "hometown", tempCharacter, tempConfigSec.getString("hometown"));
       			checkNullString(tempConfigSec, "description", tempCharacter, tempConfigSec.getString("description"));
       			
       			checkNullInt(tempConfigSec, "description", tempCharacter, tempConfigSec.getInt("age"));
       			checkNullInt(tempConfigSec, "description", tempCharacter, tempConfigSec.getInt("height"));
       			
       			tempPlayer.addCharacter(tempCharacter);

       		}
       		
       		tempPlayer.setActiveCharacter(tempConfig.getString("active"));
       		
       		//Add tempUUID tempPlayer to Main.playerData
       		Main.playerData.put(tempUUID, tempPlayer);
       		
       	}
		
	}
	
	private static void checkNullString(ConfigurationSection cs, String key, RPGCharacter c, String s) {
		
		if(cs.getString(key) != null) {
			
			switch(key) {
				case "id":
					c.setCharacterID(s);
					break;
				case "name" :
					c.setName(s);
					break;
				case "race":
					c.setRace(s);
					break;
				case "class":
					c.setPClass(s);
					break;
				case "bodytype":
					c.setBodytype(s);
					break;
				case "hometown":
					c.setHometown(s);
					break;
				case "description":
					c.setDescription(s);
					break;
					
			}
					
					
		} else {
			
			switch(key) {
			case "id":
				c.setCharacterID("");
				break;
			case "name" :
				c.setName("");
				break;
			case "race":
				c.setRace("");
				break;
			case "class":
				c.setPClass("");
				break;
			case "bodytype":
				c.setBodytype("");
				break;
			case "hometown":
				c.setHometown("");
				break;
			case "description":
				c.setDescription("");
				break;
				
		}
			
		}
		
	}
	
	private static void checkNullInt(ConfigurationSection cs, String key, RPGCharacter c, int i) {
			
		switch(key) {
			case "age":
				c.setAge(i);
				break;
			case "height" :
				c.setHeight(i);
				break;
		}
		
	}

}
