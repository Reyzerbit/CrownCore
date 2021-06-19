package com.reyzerbit.CrownCore.core;

import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.reyzerbit.CrownCore.CrownCore;
import com.reyzerbit.CrownCore.core.structures.CrownPlayer;

import net.md_5.bungee.api.ChatColor;

public class Checks {

	//Returns true if player has valid permissions to create character
	public static boolean checkCreate(CommandSender sender, String[] args) {
		
		//If executed correctly by player
		if(args.length == 9 && sender instanceof Player && sender.hasPermission("crowncore.create")) {
			
			if(CrownCore.playerData.containsKey(((Player) sender).getUniqueId())) {
				
				return Create.createCharacter(sender, args);
			
			} else {
				
				CrownCore.playerData.put(((Player) sender).getUniqueId(), new CrownPlayer(((Player) sender).getUniqueId()));
				
				return Create.createCharacter(sender, args);
				
			}
			
		//If executed correctly by op
		} else if(args.length == 10 && (sender.isOp() || sender.hasPermission("crowncore.create.op"))) {
			
			UUID playerID = Bukkit.getPlayer(args[1]).getUniqueId();
			
			if(CrownCore.playerData.containsKey(playerID)) {
				
				return Create.createCharacterOp(sender, args);
				
			} else {
				
				CrownCore.playerData.put(playerID, new CrownPlayer(playerID));
				return Create.createCharacterOp(sender, args);
				
			}
			
		//If executed incorrectly by op
		} else if(sender.isOp() || sender.hasPermission("crowncore.create.op")) {
	
			sender.sendMessage(ChatColor.RED + "Usage: /cc create [Player] [ID] [Name] [Race] [Class] [Age] [Height] [Bodytype] [Hometown]");
			return false;
			
		//If executed incorrectly by player
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /cc create [ID] [Name] [Race] [Class] [Age] [Height] [Bodytype] [Hometown]");
			return false;
				
		}
		
	}

	//Returns true if the name is permitted
	public static boolean checkNames(String name) {
		
		if(CrownCore.names.isEmpty()) return true;
		
		if(CrownCore.protectedNames == CrownCore.protectValues.WHITE) {
			
			if(!CrownCore.names.contains(name)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(CrownCore.protectedNames == CrownCore.protectValues.BLACK) {
			
			if(CrownCore.names.contains(name)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(CrownCore.protectedNames == CrownCore.protectValues.NONE) {
			
			return true;
			
		} else {
			
			CrownCore.getPlugin(CrownCore.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! An error occured while verifying protected names.");
			return false;
			
		}
		
	}

	//Returns true if the race is permitted
	public static boolean checkRace(String race) {
		
		if(CrownCore.races.isEmpty()) return true;
		
		if(CrownCore.protectedRaces == CrownCore.protectValues.WHITE) {
			
			if(!CrownCore.races.contains(race)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(CrownCore.protectedRaces == CrownCore.protectValues.BLACK) {
			
			if(CrownCore.races.contains(race)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(CrownCore.protectedRaces == CrownCore.protectValues.NONE) {
			
			return true;
			
		} else {
			
			CrownCore.getPlugin(CrownCore.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! An error occured while verifying protected races.");
			return false;
			
		}
		
	}

	//Returns true if the home town is permitted
	public static boolean checkHometown(String hometown) {
		
		if(CrownCore.hometowns.isEmpty()) return true;
		
		if(CrownCore.protectedHometowns == CrownCore.protectValues.WHITE) {
			
			if(!CrownCore.hometowns.contains(hometown)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(CrownCore.protectedHometowns == CrownCore.protectValues.BLACK) {
			
			if(CrownCore.hometowns.contains(hometown)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(CrownCore.protectedHometowns == CrownCore.protectValues.NONE) {
			
			return true;
			
		} else {
			
			CrownCore.getPlugin(CrownCore.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! An error occured while verifying protected hometowns.");
			return false;
			
		}
		
	}

	//Returns true if the class is permitted
	public static boolean checkClass(String pClass) {
		
		if(CrownCore.classes.isEmpty()) return true;
		
		if(CrownCore.protectedClasses == CrownCore.protectValues.WHITE) {
			
			if(!CrownCore.classes.contains(pClass)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(CrownCore.protectedClasses == CrownCore.protectValues.BLACK) {
			
			if(CrownCore.classes.contains(pClass)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(CrownCore.protectedClasses == CrownCore.protectValues.NONE) {
			
			return true;
			
		} else {
			
			CrownCore.getPlugin(CrownCore.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! An error occured while verifying protected classes.");
			return false;
			
		}
		
	}

	//Returns true if the body type is permitted
	public static boolean checkBodytype(String bodytype) {
		
		if(CrownCore.bodytypes.isEmpty()) return true;
		
		if(CrownCore.protectedBodyTypes == CrownCore.protectValues.WHITE) {
			
			if(!CrownCore.bodytypes.contains(bodytype)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(CrownCore.protectedBodyTypes == CrownCore.protectValues.BLACK) {
			
			if(CrownCore.bodytypes.contains(bodytype)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(CrownCore.protectedBodyTypes == CrownCore.protectValues.NONE) {
			
			return true;
			
		} else {
			
			CrownCore.getPlugin(CrownCore.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! An error occured while verifying protected bodytypes.");
			return false;
			
		}
		
	}

	//Returns true if the age is permitted
	public static boolean checkAge(String age) {
		
		int ageInt = Integer.parseInt(age);
		
		if(CrownCore.minAge == 0 && CrownCore.maxAge == 0) return true;
			
		if(ageInt < CrownCore.minAge || ageInt > CrownCore.maxAge) {
			
			return false;
			
		}
		
		return true;
		
	}

	//Returns true if the height is permitted
	public static boolean checkHeight(String height) {
		
		int heightInt = Integer.parseInt(height);
		
		if(CrownCore.minHeight == 0 && CrownCore.maxHeight == 0) return true;
			
		if(heightInt < CrownCore.minAge || heightInt > CrownCore.maxAge) {
			
			return false;
			
		}
		
		return true;
		
	}

}
