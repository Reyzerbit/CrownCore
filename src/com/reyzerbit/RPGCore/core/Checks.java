package com.reyzerbit.RPGCore.core;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.reyzerbit.RPGCore.RPGCore;
import com.reyzerbit.RPGCore.core.structures.RPGPlayer;

import net.md_5.bungee.api.ChatColor;

public class Checks {

	//Returns true if player has valid permissions to create character
	public static boolean checkCreate(CommandSender sender, String[] args) {
		
		//If executed correctly by player
		if(args.length == 9 && sender instanceof Player && sender.hasPermission("rpgcore.create")) {
			
			if(RPGCore.playerData.containsKey(((Player) sender).getUniqueId())) {
				
				return Create.createCharacter(sender, args);
			
			} else {
				
				RPGCore.playerData.put(((Player) sender).getUniqueId(), new RPGPlayer(((Player) sender).getUniqueId()));
				
				return Create.createCharacter(sender, args);
				
			}
			
		//If executed correctly by op
		} else if(args.length == 10 && (sender.isOp() || sender.hasPermission("rpgcore.create.op"))) {
			
			UUID playerID = Bukkit.getPlayer(args[1]).getUniqueId();
			
			if(RPGCore.playerData.containsKey(playerID)) {
				
				return Create.createCharacterOp(sender, args);
				
			} else {
				
				RPGCore.playerData.put(playerID, new RPGPlayer(playerID));
				return Create.createCharacterOp(sender, args);
				
			}
			
		//If executed incorrectly by op
		} else if(sender.isOp() || sender.hasPermission("rpgcore.create.op")) {
	
			sender.sendMessage(ChatColor.RED + "Usage: /rpg create [Player] [ID] [Name] [Race] [Class] [Age] [Height] [Bodytype] [Hometown]");
			return false;
			
		//If executed incorrectly by player
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg create [ID] [Name] [Race] [Class] [Age] [Height] [Bodytype] [Hometown]");
			return false;
				
		}
		
	}

	//Returns true if the name is permitted
	public static boolean checkNames(String name) {
		
		if(RPGCore.names.isEmpty()) return true;
		
		if(RPGCore.protectedNames == RPGCore.protectValues.WHITE) {
			
			if(!RPGCore.names.contains(name)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(RPGCore.protectedNames == RPGCore.protectValues.BLACK) {
			
			if(RPGCore.names.contains(name)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(RPGCore.protectedNames == RPGCore.protectValues.NONE) {
			
			return true;
			
		} else {
			
			RPGCore.getPlugin(RPGCore.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! An error occured while verifying protected names.");
			return false;
			
		}
		
	}

	//Returns true if the race is permitted
	public static boolean checkRace(String race) {
		
		if(RPGCore.races.isEmpty()) return true;
		
		if(RPGCore.protectedRaces == RPGCore.protectValues.WHITE) {
			
			if(!RPGCore.races.contains(race)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(RPGCore.protectedRaces == RPGCore.protectValues.BLACK) {
			
			if(RPGCore.races.contains(race)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(RPGCore.protectedRaces == RPGCore.protectValues.NONE) {
			
			return true;
			
		} else {
			
			RPGCore.getPlugin(RPGCore.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! An error occured while verifying protected races.");
			return false;
			
		}
		
	}

	//Returns true if the home town is permitted
	public static boolean checkHometown(String hometown) {
		
		if(RPGCore.hometowns.isEmpty()) return true;
		
		if(RPGCore.protectedHometowns == RPGCore.protectValues.WHITE) {
			
			if(!RPGCore.hometowns.contains(hometown)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(RPGCore.protectedHometowns == RPGCore.protectValues.BLACK) {
			
			if(RPGCore.hometowns.contains(hometown)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(RPGCore.protectedHometowns == RPGCore.protectValues.NONE) {
			
			return true;
			
		} else {
			
			RPGCore.getPlugin(RPGCore.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! An error occured while verifying protected hometowns.");
			return false;
			
		}
		
	}

	//Returns true if the class is permitted
	public static boolean checkClass(String pClass) {
		
		if(RPGCore.classes.isEmpty()) return true;
		
		if(RPGCore.protectedClasses == RPGCore.protectValues.WHITE) {
			
			if(!RPGCore.classes.contains(pClass)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(RPGCore.protectedClasses == RPGCore.protectValues.BLACK) {
			
			if(RPGCore.classes.contains(pClass)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(RPGCore.protectedClasses == RPGCore.protectValues.NONE) {
			
			return true;
			
		} else {
			
			RPGCore.getPlugin(RPGCore.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! An error occured while verifying protected classes.");
			return false;
			
		}
		
	}

	//Returns true if the body type is permitted
	public static boolean checkBodytype(String bodytype) {
		
		if(RPGCore.bodytypes.isEmpty()) return true;
		
		if(RPGCore.protectedBodyTypes == RPGCore.protectValues.WHITE) {
			
			if(!RPGCore.bodytypes.contains(bodytype)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(RPGCore.protectedBodyTypes == RPGCore.protectValues.BLACK) {
			
			if(RPGCore.bodytypes.contains(bodytype)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(RPGCore.protectedBodyTypes == RPGCore.protectValues.NONE) {
			
			return true;
			
		} else {
			
			RPGCore.getPlugin(RPGCore.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! An error occured while verifying protected bodytypes.");
			return false;
			
		}
		
	}

	//Returns true if the age is permitted
	public static boolean checkAge(String age) {
		
		int ageInt = Integer.parseInt(age);
		
		if(RPGCore.minAge == 0 && RPGCore.maxAge == 0) return true;
			
		if(ageInt < RPGCore.minAge || ageInt > RPGCore.maxAge) {
			
			return false;
			
		}
		
		return true;
		
	}

	//Returns true if the height is permitted
	public static boolean checkHeight(String height) {
		
		int heightInt = Integer.parseInt(height);
		
		if(RPGCore.minHeight == 0 && RPGCore.maxHeight == 0) return true;
			
		if(heightInt < RPGCore.minAge || heightInt > RPGCore.maxAge) {
			
			return false;
			
		}
		
		return true;
		
	}

}
