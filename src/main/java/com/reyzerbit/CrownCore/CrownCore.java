package com.reyzerbit.CrownCore;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.reyzerbit.CrownCore.core.Conversion;
import com.reyzerbit.CrownCore.core.events.PlayerClickInfoListener;
import com.reyzerbit.CrownCore.core.events.RPGCommandEvent;
import com.reyzerbit.CrownCore.core.events.TabCompleterEvent;
import com.reyzerbit.CrownCore.core.io.Load;
import com.reyzerbit.CrownCore.core.io.Save;
import com.reyzerbit.CrownCore.core.structures.CrownPlayer;

import net.milkbowl.vault.permission.Permission;

public class CrownCore extends JavaPlugin {
	
	// Used to determine if configuration values are white-listed, black-listed, or neither
	public enum protectValues {
		
		WHITE, BLACK, NONE
		
	}
	
	//Configuration
	public static FileConfiguration config;
	public static File configFile;
	
	//ConfigVals
	public static boolean pluginEnabled;
	
	//Data Location
	public static File playerDataDir;
	
	//Data
	public static Map<UUID, CrownPlayer> playerData;
	
	public static List<String> races;
	public static List<String> classes;
	public static List<String> bodytypes;
	public static List<String> names;
	public static List<String> hometowns;
	
	public static protectValues protectedRaces;
	public static protectValues protectedClasses;
	public static protectValues protectedBodyTypes;
	public static protectValues protectedNames;
	public static protectValues protectedHometowns;
	
	public static int minAge;
	public static int maxAge;
	public static int minHeight;
	public static int maxHeight;
	
	//Messages
	public static final String enableMsg = "ENABLED!";
	public static final String disableMsg = "DISABLED!";
	public static final String errorMsg = "An internal error has occured!";
	
	//Vault API
	public static Permission perms = null;
	
	//Player Data Configs
	public static Map<String, FileConfiguration> playerSavesConfig;
	
	
	/**************************************************************************/
	
	// Fired when plugin is first enabled
    @Override
    public void onEnable() {

    	//Generate files if missing
    	saveDefaultConfig();
    	
		//congif.yml
    	configFile = new File(this.getDataFolder(), "config.yml");
    	config = this.getConfig();
    	
    	pluginEnabled = config.getBoolean("enabled");
		
		if(!pluginEnabled) {
			
			getLogger().log(Level.INFO, "[CrownCore] Config enabled value is set to false! Disabling CrownCore!");
			this.setEnabled(false);
			return;
			
		}
    	
    	playerDataDir = new File(this.getDataFolder(), "playerdata");
    	
    	//Saves Init
    	CrownCore.playerSavesConfig = new HashMap<String, FileConfiguration>();
    	playerData = new HashMap<UUID, CrownPlayer>();
    	
    	if(getServer().getPluginManager().getPlugin("Vault") == null) {
    		
    		getServer().getLogger().log(Level.SEVERE, "[CrownCore] Unable to detect Vault! Disabling CrownCore.");
    		getServer().getPluginManager().disablePlugin(this);
    		return;
    		
    	}
		
    	setupPermissions();
    	
		reload();
		
    	this.getCommand("cc").setExecutor(new RPGCommandEvent());
    	
    	getServer().getPluginManager().registerEvents(new PlayerClickInfoListener(), this);
    	
    	//WIP
    	this.getCommand("cc").setTabCompleter(new TabCompleterEvent());
    	
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    	if(pluginEnabled) Save.save();
    	
    }
    
    // Used to reload the plugin
    @SuppressWarnings("unchecked")
	public static void reload() {
    	
    	Load.load();
    	
    	config = YamlConfiguration.loadConfiguration(configFile);
    	
    	//Character Constraints
    	names = (List<String>) config.getList("names");
    	hometowns = (List<String>) config.getList("hometowns");
    	races = (List<String>) config.getList("races");
    	classes = (List<String>) config.getList("classes");
    	bodytypes = (List<String>) config.getList("bodytypes");
    	
    	protectedNames = Conversion.convertConfigEnums(config.getString("protectedNames"));
    	protectedHometowns = Conversion.convertConfigEnums(config.getString("protectedHometowns"));
    	protectedRaces = Conversion.convertConfigEnums(config.getString("protectedRaces"));
    	protectedClasses = Conversion.convertConfigEnums(config.getString("protectedClasses"));
    	protectedBodyTypes = Conversion.convertConfigEnums(config.getString("protectedBodyTypes"));
    	
    	minAge = config.getInt("min_age");
    	maxAge = config.getInt("max_age");
    	minHeight = config.getInt("min_height");
    	maxHeight = config.getInt("max_height");
    	
    }
    
    private boolean setupPermissions() {
    	
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
        
    }
    
}
