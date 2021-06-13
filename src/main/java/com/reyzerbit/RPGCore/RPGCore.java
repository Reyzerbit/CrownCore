package com.reyzerbit.RPGCore;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.reyzerbit.RPGCore.core.Conversion;
import com.reyzerbit.RPGCore.core.events.RPGCommandEvent;
import com.reyzerbit.RPGCore.core.io.Load;
import com.reyzerbit.RPGCore.core.io.Save;
import com.reyzerbit.RPGCore.core.structures.RPGPlayer;

import net.milkbowl.vault.permission.Permission;

public class RPGCore extends JavaPlugin {
	
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
	public static Map<UUID, RPGPlayer> playerData;
	
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
	
	//SQL
	public static String sqlName;
	public static String sqlDatabase;
	public static String sqlUser;
	public static String sqlPassword;
	public static int sqlPort;
	public static Connection sqlConnection;
	
	//Vault API
	public static Permission perms = null;
	
	//Player Data Configs
	public static Map<String, FileConfiguration> playerSavesConfig;
	
	
	/**************************************************************************/
	
	
	// Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	
		//congif.yml
    	config = this.getConfig();
    	configFile = new File(this.getDataFolder(), "config.yml");

    	//Generate files if missing
    	if(!configFile.exists()) {

    		saveResource("config.yml", false);
    		
    	}
    	
    	playerDataDir = new File(this.getDataFolder(), "playerdata");
    	
    	//Saves Init
    	RPGCore.playerSavesConfig = new HashMap<String, FileConfiguration>();
    	playerData = new HashMap<UUID, RPGPlayer>();
    	
    	if(getServer().getPluginManager().getPlugin("Vault") == null) {
    		
    		getServer().getLogger().log(Level.SEVERE, "Unable to detect Vault! Disabling RPG Core.");
    		getServer().getPluginManager().disablePlugin(this);
    		return;
    		
    	}
		
    	setupPermissions();
    	
		reload();
		
    	this.getCommand("rpg").setExecutor(new RPGCommandEvent());
    	
    	//WIP
    	//this.getCommand("rpg").setTabCompleter(new TabCompleterRPG());
    	
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    	Save.save();
    	
    }
    
    // Used to reload the plugin
    @SuppressWarnings("unchecked")
	public static void reload() {
    	
    	Load.load();
    	
    	config = YamlConfiguration.loadConfiguration(configFile);
    	
    	//Enabled
    	pluginEnabled = config.getBoolean("enabled");
    	
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
    	
    	//SQL
    	sqlName = config.getString("hostname");
    	sqlDatabase = config.getString("database");
    	sqlUser = config.getString("username");
    	sqlPassword = config.getString("password");
    	sqlPort = config.getInt("port");
    	
    }
    
    private boolean setupPermissions() {
    	
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
        
    }
    
}
