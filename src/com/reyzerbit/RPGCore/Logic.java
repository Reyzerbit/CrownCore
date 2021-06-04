package com.reyzerbit.RPGCore;

import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import com.reyzerbit.RPGCore.DataStructures.RPGCharacter;
import com.reyzerbit.RPGCore.DataStructures.RPGPlayer;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

public class Logic {

	public static boolean createChecks(CommandSender sender, String[] args) {
		
		//If executed correctly by player
		if(args.length == 9 && sender instanceof Player && sender.hasPermission("rpgcore.create")) {
			
			if(Main.playerData.containsKey(((Player) sender).getUniqueId())) {
				
				return createCharacter(sender, args);
			
			} else {
				
				Main.playerData.put(((Player) sender).getUniqueId(), new RPGPlayer(((Player) sender).getUniqueId()));
				
				return createCharacter(sender, args);
				
			}
			
		//If executed correctly by op
		} else if(args.length == 10 && (sender.isOp() || sender.hasPermission("rpgcore.create.op"))) {
			
			UUID playerID = Bukkit.getPlayer(args[1]).getUniqueId();
			
			if(Main.playerData.containsKey(playerID)) {
				
				return createCharacterOp(sender, args);
				
			} else {
				
				Main.playerData.put(playerID, new RPGPlayer(playerID));
				return createCharacterOp(sender, args);
				
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
	
	private static boolean createCharacter(CommandSender sender, String[] args) {
		
		
		//Checks for duplicate ID
		if(Main.playerData.get(((Player) sender).getUniqueId()).getCharacterIDs().contains(args[1])) {
			
			sender.sendMessage(ChatColor.RED + "That ID is already in use for one of your characters!");
			return false;
			
		}
		
		//Checks for banned name
		if(!checkNames(args[2].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That name is not available on this server!");
			return false;
			
		}
		
		//Checks for banned hometowns
		if(!checkHometown(args[8].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That hometown is not available on this server!");
			return false;
			
		}
		
		//Checks race
		if(!checkRace(args[3].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That race is not available on this server!");
			return false;
			
		}
		
		//Checks class
		if(!checkClass(args[4].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That class is not available on this server!");
			return false;
			
		}
		
		//Checks bodytypes
		if(!checkBodytype(args[7].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That bodytype is not available on this server!");
			return false;
			
		}
	
		//Checks age
		if(!checkAge(args[5])) {
				
			sender.sendMessage(ChatColor.RED + "That age is not available on this server!");
			return false;
			
		}
		
		//Checks height
		if(!checkHeight(args[6])) {
			
			sender.sendMessage(ChatColor.RED + "That height is not available on this server!");
			return false;
			
		}
		
		//Adds new character
		sender.sendMessage(ChatColor.GREEN + "Creating new character with ID " + args[1]);
		Main.playerData.get(((Player) sender).getUniqueId()).addCharacter(new RPGCharacter(args[1], args[2], args[3], args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]), args[7], args[8]));
		
		IO.save();
		return true;
		
	}
	
	private static boolean createCharacterOp(CommandSender sender, String[] args) {
		
		
		//Checks for duplicate ID
		if(Main.playerData.get(((Player) sender).getUniqueId()).getCharacterIDs().contains(args[2])) {
			
			sender.sendMessage(ChatColor.RED + "That ID is already in use for one of your characters!");
			return false;
			
		}
		
		//Checks for banned name
		if(!checkNames(args[3].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That name is not available on this server!");
			return false;
			
		}
		
		//Checks for banned hometowns
		if(!checkHometown(args[9].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That hometown is not available on this server!");
			return false;
			
		}
		
		//Checks race
		if(!checkRace(args[4].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That race is not available on this server!");
			return false;
			
		}
		
		//Checks class
		if(!checkClass(args[5].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That class is not available on this server!");
			return false;
			
		}
		
		//Checks bodytypes
		if(!checkBodytype(args[8].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That bodytype is not available on this server!");
			return false;
			
		}
	
		//Checks age
		if(!checkAge(args[6])) {
				
			sender.sendMessage(ChatColor.RED + "That age is not available on this server!");
			return false;
			
		}
		
		//Checks height
		if(!checkHeight(args[7])) {
			
			sender.sendMessage(ChatColor.RED + "That height is not available on this server!");
			return false;
			
		}
		
		//Adds new character
		
		sender.sendMessage(ChatColor.GREEN + "Creating new character for player " + args[1] + " with ID " + args[2]);
		Main.playerData.get((Bukkit.getPlayer(args[1])).getUniqueId()).addCharacter(new RPGCharacter(args[2], args[3], args[4], args[5], Integer.parseInt(args[6]), Integer.parseInt(args[7]), args[8], args[9]));
		
		IO.save();
		return true;
		
	}

	public static boolean deleteCharacter(CommandSender sender, String[] args) {
		
		if(Main.playerData.get(((Player) sender).getUniqueId()) == null) {
			
			sender.sendMessage(ChatColor.RED + Main.errorMsg + " Delete command failed.");
			return false;
			
		}

		//Checks for ID for requested deletion
		if(args.length == 2 && !Main.playerData.get(((Player) sender).getUniqueId()).getCharacterIDs().contains(args[1])) {
					
			sender.sendMessage(ChatColor.RED + "Unable to find character with ID " + args[1]);
			return false;
					
		} else if(args.length == 2) {
			
			for(RPGCharacter character : Main.playerData.get(((Player) sender).getUniqueId()).getCharacters()) {
				
				if(character.getCharacterID().equals(args[1])) {
					
					sender.sendMessage(ChatColor.GREEN + "Deleting character with ID " + args[1]);
					Main.playerData.get(((Player) sender).getUniqueId()).getCharacters().remove(character);
					
					IO.save();
					
					return true;
					
				}
				
			}
			
		} else if((sender.isOp() || sender.hasPermission("rpgcore.delete.op")) && args.length == 3) {
			
			if(Bukkit.getPlayer(args[1]) == null) {
				
				sender.sendMessage(ChatColor.RED + "No player found with name " + args[1]);
				return false;
				
			}
			
			if(Main.playerData.containsKey(Bukkit.getPlayer(args[1]).getUniqueId())) {
				
				for(RPGCharacter character : Main.playerData.get(Bukkit.getPlayer(args[1]).getUniqueId()).getCharacters()) {
					
					if(character.getCharacterID().equals(args[2])) {
						
						sender.sendMessage(ChatColor.GREEN + "Deleting character owned by " + args[1] + " with ID " + args[2]);
						Main.playerData.get(Bukkit.getPlayer(args[1]).getUniqueId()).getCharacters().remove(character);
						
						IO.save();
						
						return true;
						
					}
					
				}
				
			} else {
				
				sender.sendMessage(ChatColor.RED + "The player " + args[1] + " has no characters saved!");
				return false;
				
			}
			
		} else if(sender.isOp() || sender.hasPermission("rpgcore.delete.op")) {
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg delete [Player] [CharacterID]");
			return false;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg delete [CharacterID]");
			return false;
			
		}

		sender.sendMessage(ChatColor.RED + "An internal error has occured! Check console logs.");
		Bukkit.getLogger().log(Level.SEVERE, "Something went wrong with character deletion! Send a copy of this log to the developer for resolution!");
			
		return false;
		
	}
	
	public static boolean listCharacters(CommandSender sender, String[] args) {
		
		if(args.length == 1) {
			
			if(Main.playerData.containsKey(((Player) sender).getUniqueId())) {
			
				sender.sendMessage(ChatColor.BLUE + "=========" + ChatColor.AQUA + "Your Characters" + ChatColor.BLUE + "=========");
				
				for(RPGCharacter character : Main.playerData.get(((Player) sender).getUniqueId()).getCharacters()) {
					
					sender.sendMessage(ChatColor.LIGHT_PURPLE + character.getCharacterID());
					
				}
				
				sender.sendMessage(ChatColor.BLUE + "================================");
				
				return true;
				
			} else {
				
				sender.sendMessage(ChatColor.RED + "No player information found! Make a character to get started.");
				
			}
			
		} else if((sender.isOp() || sender.hasPermission("rpgcore.list.op")) && args.length == 2) {
			
			if(Main.playerData.containsKey((Bukkit.getPlayer(args[1]).getUniqueId()))) {
			
				if(Bukkit.getPlayer(args[1]) == null) {
					
					sender.sendMessage(ChatColor.RED + "No player found with name " + args[1]);
					return false;
					
				}
				
				sender.sendMessage(ChatColor.BLUE + "=========" + args[1] + "'s Characters=========");
				
				for(RPGCharacter character : Main.playerData.get((Bukkit.getPlayer(args[1]).getUniqueId())).getCharacters()) {
					
					sender.sendMessage(ChatColor.AQUA + character.getCharacterID());
					
				}
				
				String bottomFrame = "===============================";
				
				for(int x = 0; x < args[1].length(); x++) {
					
					bottomFrame += "=";
					
				}
				
				sender.sendMessage(ChatColor.BLUE + bottomFrame);
				
				return true;
			
			} else {
				
				sender.sendMessage(ChatColor.RED + "No player information found for player " + args[1]);
				return false;
				
			}
				
		} else if(sender.isOp() || sender.hasPermission("rpgcore.list.op")) {
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg list [Player]");
			return false;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg list");
			return false;
			
		}
		
		return false;
		
	}
	
	public static boolean viewCharacter(CommandSender sender, String[] args) {
		
		if(args.length == 2) {
			
			Player p = Bukkit.getPlayer(args[1]);
			
			RPGCharacter pc = null;
			
			if(p != null) {
				
				if(Main.playerData.containsKey(p.getUniqueId())) {
					
					pc = Main.playerData.get(p.getUniqueId()).getActiveCharacter();
					
				}
				
			} else {
				
				sender.sendMessage(ChatColor.RED + "No player found online with name " + args[1]);
				return false;
				
			}
			
			if(pc == null) {
				
				sender.sendMessage(ChatColor.RED + "Player has no active character!");
				return false;
				
			}
			
			sender.sendMessage(ChatColor.BLUE + "=========" + ChatColor.AQUA + args[1] + ChatColor.BLUE + "=========");
			
			sender.spigot().sendMessage(convertToClickCommand("Name:", " " + pc.getName(), pc.getCharacterID(), "name").create());
			sender.spigot().sendMessage(convertToClickCommand("Race:", " " + pc.getRace(), pc.getCharacterID(), "race").create());
			sender.spigot().sendMessage(convertToClickCommand("Class:", " " + pc.getPClass(), pc.getCharacterID(), "class").create());
			sender.spigot().sendMessage(convertToClickCommand("Age:", " " + pc.getAge(), pc.getCharacterID(), "age").create());
			sender.spigot().sendMessage(convertToClickCommand("Height:", " " + pc.getHeight(), pc.getCharacterID(), "height").create());
			sender.spigot().sendMessage(convertToClickCommand("Bodytype:", " " + pc.getBodytype(), pc.getCharacterID(), "bodytype").create());
			sender.spigot().sendMessage(convertToClickCommand("Hometown:", " " + pc.getHometown(), pc.getCharacterID(), "hometown").create());
			
			String descString = ChatColor.AQUA + "Description: " + ChatColor.LIGHT_PURPLE + pc.getDescription();
			String[] splitDesc;
			
			splitDesc = ChatPaginator.wordWrap(descString, 20 + args[1].length());
			
			sender.spigot().sendMessage(convertToClickCommand(splitDesc[0], "", pc.getCharacterID(), "description").create());
			
			for(int x = 1 ; x < splitDesc.length ; x++) {
				
				sender.sendMessage(splitDesc[x]);
				
			}
			
			String bottomFrame = "=================";
			
			for(int x = 0; x < args[1].length(); x++) {
				
				bottomFrame += "=";
				
			}
			
			sender.sendMessage(ChatColor.BLUE + bottomFrame);
			
			return true;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg view [Player]");
			return false;
			
		}
		
	}
	
	public static void setAsActive(CommandSender sender, String[] args) {
		
		if(args.length == 2) {
			
			Player p = (Player) sender;
			
			RPGPlayer rpgP = Main.playerData.get(p.getUniqueId());
			
			rpgP.setActiveCharacter(args[1]);
			
			sender.sendMessage(ChatColor.GREEN + "Setting your active character to " + args[1]);
			
			setClassGroup(p, Main.config.getString(rpgP.getActiveCharacter().getPClass() + "group"));
			
			IO.save();
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg setactive [Character ID]");
			
		}
		
	}
	
	//Returns true if the name is permitted
	private static boolean checkNames(String name) {
		
		if(Main.names.isEmpty()) return true;
		
		if(Main.protectedNames == Main.protectValues.WHITE) {
			
			if(!Main.names.contains(name)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(Main.protectedNames == Main.protectValues.BLACK) {
			
			if(Main.names.contains(name)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(Main.protectedNames == Main.protectValues.NONE) {
			
			return true;
			
		} else {
			
			Main.getPlugin(Main.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! An error occured while verifying protected names.");
			return false;
			
		}
		
	}
	
	//Returns true if the race is permitted
	private static boolean checkRace(String race) {
		
		if(Main.races.isEmpty()) return true;
		
		if(Main.protectedRaces == Main.protectValues.WHITE) {
			
			if(!Main.races.contains(race)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(Main.protectedRaces == Main.protectValues.BLACK) {
			
			if(Main.races.contains(race)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(Main.protectedRaces == Main.protectValues.NONE) {
			
			return true;
			
		} else {
			
			Main.getPlugin(Main.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! En error occured while verifying protected races.");
			return false;
			
		}
		
	}
	
	//Returns true if the hometown is permitted
	private static boolean checkHometown(String hometown) {
		
		if(Main.hometowns.isEmpty()) return true;
		
		if(Main.protectedHometowns == Main.protectValues.WHITE) {
			
			if(!Main.hometowns.contains(hometown)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(Main.protectedHometowns == Main.protectValues.BLACK) {
			
			if(Main.hometowns.contains(hometown)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(Main.protectedHometowns == Main.protectValues.NONE) {
			
			return true;
			
		} else {
			
			Main.getPlugin(Main.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! En error occured while verifying protected hometowns.");
			return false;
			
		}
		
	}
	
	//Returns true if the class is permitted
	private static boolean checkClass(String pClass) {
		
		if(Main.classes.isEmpty()) return true;
		
		if(Main.protectedClasses == Main.protectValues.WHITE) {
			
			if(!Main.classes.contains(pClass)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(Main.protectedClasses == Main.protectValues.BLACK) {
			
			if(Main.classes.contains(pClass)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(Main.protectedClasses == Main.protectValues.NONE) {
			
			return true;
			
		} else {
			
			Main.getPlugin(Main.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! En error occured while verifying protected classes.");
			return false;
			
		}
		
	}
	
	//Returns true if the bodytype is permitted
	private static boolean checkBodytype(String bodytype) {
		
		if(Main.bodytypes.isEmpty()) return true;
		
		if(Main.protectedBodyTypes == Main.protectValues.WHITE) {
			
			if(!Main.bodytypes.contains(bodytype)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(Main.protectedBodyTypes == Main.protectValues.BLACK) {
			
			if(Main.bodytypes.contains(bodytype)) {
				
				return false;
				
			} else {
				
				return true;
				
			}
			
		} else if(Main.protectedBodyTypes == Main.protectValues.NONE) {
			
			return true;
			
		} else {
			
			Main.getPlugin(Main.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! En error occured while verifying protected bodytypes.");
			return false;
			
		}
		
	}
	
	private static boolean checkAge(String age) {
		
		int ageInt = Integer.parseInt(age);
		
		if(Main.minAge == 0 && Main.maxAge == 0) return true;
			
		if(ageInt < Main.minAge || ageInt > Main.maxAge) {
			
			return false;
			
		}
		
		return true;
		
	}
	
	private static boolean checkHeight(String height) {
		
		int heightInt = Integer.parseInt(height);
		
		if(Main.minHeight == 0 && Main.maxHeight == 0) return true;
			
		if(heightInt < Main.minAge || heightInt > Main.maxAge) {
			
			return false;
			
		}
		
		return true;
		
	}
		
	//Returns true if the bodytype is permitted
	public static boolean setValue(CommandSender sender, String[] args) {
		
		if(args.length == 3) {
			
			Player p = (Player) sender;
			
			RPGPlayer tempPlayer = Main.playerData.get(p.getUniqueId());
			
			if(tempPlayer == null) {
				
				sender.sendMessage(ChatColor.RED + "No player data exists for you! Create a character first!");
				return false;
				
			}
			
			RPGCharacter tempCharacter = tempPlayer.getCharacter(args[1]);
			
			if(tempCharacter == null) {
				
				sender.sendMessage(ChatColor.RED + "No character was found with id " + args[1]);
				return false;
				
			}
			
			switch(args[0].substring(3).toLowerCase()) {
				case "name" :
					if(checkNames(args[2])) tempCharacter.setName(args[2]);
					break;
				case "race":
					if(checkRace(args[2])) tempCharacter.setRace(args[2]);
					break;
				case "class":
					if(checkClass(args[2])) tempCharacter.setPClass(args[2]);
					break;
				case "bodytype":
					if(checkBodytype(args[2])) tempCharacter.setBodytype(args[2]);
					break;
				case "hometown":
					if(checkHometown(args[2])) tempCharacter.setHometown(args[2]);
					break;
				case "age":
					if(checkAge(args[2])) tempCharacter.setAge(Integer.parseInt(args[2]));
					break;
				case "height" :
					if(checkHeight(args[2])) tempCharacter.setHeight(Integer.parseInt(args[2]));
					break;
				
			}
			
			sender.sendMessage(ChatColor.GREEN + "Setting " + args[1] + "'s " + args[0].substring(3).toLowerCase() + " to " + args[2] + ".");
			
			IO.save();
			
			return true;
			
		} else if(args.length > 3 && args[0].toLowerCase().equals("setdescription")){
			
			Player p = (Player) sender;
			
			RPGPlayer tempPlayer = Main.playerData.get(p.getUniqueId());
			
			if(tempPlayer == null) {
				
				sender.sendMessage(ChatColor.RED + "No player data exists for you! Create a character first!");
				return false;
				
			}
			
			RPGCharacter tempCharacter = tempPlayer.getCharacter(args[1]);
			
			if(tempCharacter == null) {
				
				sender.sendMessage(ChatColor.RED + "No character was found with id " + args[1]);
				return false;
				
			}
			
			tempCharacter.setDescription(String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
			sender.sendMessage(ChatColor.GREEN + "Set " + args[1] + "'s description.");
			IO.save();
			return true;
			
		} else if(args.length == 4 && (sender.isOp() || sender.hasPermission("rpgcore.setvalue.op"))){
			
			Player p = Bukkit.getPlayer(args[1]);
			
			if(p == null) {
				
				sender.sendMessage(ChatColor.RED + "No player found with the name " + args[1] + "!");
				return false;
				
			}
			
			RPGPlayer tempPlayer = Main.playerData.get(p.getUniqueId());
			
			if(tempPlayer == null) {
				
				sender.sendMessage(ChatColor.RED + "No player data exists for " + args[1]);
				return false;
				
			}
			
			RPGCharacter tempCharacter = tempPlayer.getCharacter(args[1]);
			
			if(tempCharacter == null) {
				
				sender.sendMessage(ChatColor.RED + "No character was found with id " + args[1] + "!");
				return false;
				
			}
			
			switch(args[0].substring(3).toLowerCase()) {
				case "name" :
					if(checkNames(args[3])) tempCharacter.setName(args[3]);
					break;
				case "race":
					if(checkRace(args[3])) tempCharacter.setRace(args[3]);
					break;
				case "class":
					if(checkClass(args[3])) tempCharacter.setPClass(args[3]);
					break;
				case "bodytype":
					if(checkBodytype(args[3])) tempCharacter.setBodytype(args[3]);
					break;
				case "hometown":
					if(checkHometown(args[3])) tempCharacter.setHometown(args[3]);
					break;
				case "age":
					if(checkAge(args[3])) tempCharacter.setAge(Integer.parseInt(args[3]));
					break;
				case "height" :
					if(checkHeight(args[3])) tempCharacter.setHeight(Integer.parseInt(args[3]));
					break;
				
			}
			
			sender.sendMessage(ChatColor.GREEN + "Setting " + args[1] + "'s " + args[0].substring(3).toLowerCase() + " to " + args[2] + ".");
			
			IO.save();
			
			return true;
			
		} else if(args.length > 4 && args[0].toLowerCase().equals("setdescription") && (sender.isOp() || sender.hasPermission("rpgcore.setvalue.op"))){
			
			Player p = Bukkit.getPlayer(args[1]);
			
			RPGPlayer tempPlayer = Main.playerData.get(p.getUniqueId());
			
			if(tempPlayer == null) {
				
				sender.sendMessage(ChatColor.RED + "No player data exists for " + args[1]);
				return false;
				
			}
			
			RPGCharacter tempCharacter = tempPlayer.getCharacter(args[1]);
			
			if(tempCharacter == null) {
				
				sender.sendMessage(ChatColor.RED + "No character was found with id " + args[1]);
				return false;
				
			}
			
			tempCharacter.setDescription(String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
			sender.sendMessage(ChatColor.GREEN + "Set " + args[1] + "'s description.");
			IO.save();
			return true;
			
		} else if(sender.isOp() || sender.hasPermission("rpgcore.setvalue.op")){
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg set[value] [Player] [Character ID] [value]");
			return false;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg set[value] [Character ID] [value]");
			return false;
			
		}
		
	}
	
	private static ComponentBuilder convertToClickCommand(String clickText, String followingText, String id, String key) {
		
		ComponentBuilder text = new ComponentBuilder(clickText);
		
		ComponentBuilder hoverText = new ComponentBuilder(ChatColor.GREEN + "Click to change " + key + "!");
		
		HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText.create());
		
		text.color(ChatColor.AQUA).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/rpg set" + key + " " + id + " ")).event(hoverEvent).append(followingText).color(ChatColor.LIGHT_PURPLE);
		
		return text;
		
	}
	
	public static void setClassGroup(Player p, String group) {
		
		if(group == null || group.equals("")) return;
		
		Main.perms.playerAddGroup(p, group);
		
	}
	
}
