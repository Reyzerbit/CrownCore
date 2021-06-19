package com.reyzerbit.CrownCore.core;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import com.reyzerbit.CrownCore.CrownCore;
import com.reyzerbit.CrownCore.core.structures.CrownCharacter;

import net.md_5.bungee.api.ChatColor;

public class ViewList {

	public static boolean listCharacters(CommandSender sender, String[] args) {
		
		if(args.length == 1) {
			
			if(CrownCore.playerData.containsKey(((Player) sender).getUniqueId())) {
			
				sender.sendMessage(ChatColor.BLUE + "=========" + ChatColor.AQUA + "Your Characters" + ChatColor.BLUE + "=========");
				
				for(CrownCharacter character : CrownCore.playerData.get(((Player) sender).getUniqueId()).getCharacters()) {
					
					sender.spigot().sendMessage(Conversion.convertToClickList(sender.getName(), character.getCharacterID()));
					
				}
				
				sender.sendMessage(ChatColor.BLUE + "================================");
				
				return true;
				
			} else {
				
				sender.sendMessage(ChatColor.RED + "No player information found! Make a character to get started.");
				
			}
			
		} else if((sender.isOp() || sender.hasPermission("rpgcore.list.op")) && args.length == 2) {
			
			if(CrownCore.playerData.containsKey((Bukkit.getPlayer(args[1]).getUniqueId()))) {
			
				if(Bukkit.getPlayer(args[1]) == null) {
					
					sender.sendMessage(ChatColor.RED + "No player found with name " + args[1]);
					return false;
					
				}
				
				sender.sendMessage(ChatColor.BLUE + "=========" + ChatColor.AQUA + args[1] + "'s Characters" + ChatColor.BLUE + "=========");
				
				for(CrownCharacter character : CrownCore.playerData.get((Bukkit.getPlayer(args[1]).getUniqueId())).getCharacters()) {
					
					sender.spigot().sendMessage(Conversion.convertToClickList(args[1], character.getCharacterID()));
					
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
				
		} else if(sender.isOp() || sender.hasPermission("crowncore.list.op")) {
			
			sender.sendMessage(ChatColor.RED + "Usage: /cc list [Player]");
			return false;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /cc list");
			return false;
			
		}
		
		return false;
		
	}

	public static boolean viewCharacter(CommandSender sender, String[] args, String characterID) {
		
		if(args.length == 1) {
			
			Player p = (Player) sender;
			
			CrownCharacter pc;
			
			if(CrownCore.playerData.containsKey(p.getUniqueId())) {
				
				pc = CrownCore.playerData.get(p.getUniqueId()).getActiveCharacter();
				
			} else {
				
				sender.sendMessage(ChatColor.RED + "No player information found! Make a character to get started.");
				return false;
				
			}
			
			if(pc == null) {
				
				sender.sendMessage(ChatColor.RED + "You have no active character!");
				return false;
				
			}
			
			sender.sendMessage(ChatColor.BLUE + "=========" + ChatColor.AQUA + p.getDisplayName() + ChatColor.BLUE + "=========");
			
			sender.spigot().sendMessage(Conversion.convertToClickCommand("Name:", " " + pc.getName(), pc.getCharacterID(), "name"));
			sender.spigot().sendMessage(Conversion.convertToClickCommand("Race:", " " + pc.getRace(), pc.getCharacterID(), "race"));
			sender.spigot().sendMessage(Conversion.convertToClickCommand("Class:", " " + pc.getPClass(), pc.getCharacterID(), "class"));
			sender.spigot().sendMessage(Conversion.convertToClickCommand("Age:", " " + pc.getAge(), pc.getCharacterID(), "age"));
			sender.spigot().sendMessage(Conversion.convertToClickCommand("Height:", " " + pc.getHeight(), pc.getCharacterID(), "height"));
			sender.spigot().sendMessage(Conversion.convertToClickCommand("Bodytype:", " " + pc.getBodytype(), pc.getCharacterID(), "bodytype"));
			sender.spigot().sendMessage(Conversion.convertToClickCommand("Hometown:", " " + pc.getHometown(), pc.getCharacterID(), "hometown"));
			
			String descString = ChatColor.AQUA + "Description: " + ChatColor.LIGHT_PURPLE + pc.getDescription();
			String[] splitDesc;
			
			splitDesc = ChatPaginator.wordWrap(descString, 20 + p.getDisplayName().length());
			
			sender.spigot().sendMessage(Conversion.convertToClickCommand(splitDesc[0].substring(0, 14), splitDesc[0].substring(14, splitDesc[0].length()), pc.getCharacterID(), "description"));
			
			for(int x = 1 ; x < splitDesc.length ; x++) {
				
				sender.sendMessage(splitDesc[x]);
				
			}
			
			String bottomFrame = "=================";
			
			for(int x = 0; x < p.getDisplayName().length(); x++) {
				
				bottomFrame += "=";
				
			}
			
			sender.sendMessage(ChatColor.BLUE + bottomFrame);
			
			return true;
			
		} else if(args.length == 2 || (args.length == 3 && args[0].equalsIgnoreCase("viewspecific"))) {
			
			Player p = Bukkit.getPlayer(args[1]);
			
			CrownCharacter pc = null;
			
			if(p != null) {
				
				if(characterID.equals("") || characterID == null) {
					
					if(CrownCore.playerData.containsKey(p.getUniqueId())) {
						
						pc = CrownCore.playerData.get(p.getUniqueId()).getActiveCharacter();
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "No player information found! Make a character to get started.");
						return false;
						
					}
					
				} else {
					
					if(CrownCore.playerData.containsKey(p.getUniqueId())) {
						
						pc = CrownCore.playerData.get(p.getUniqueId()).getCharacter(characterID);
						
					} else {
						
						sender.sendMessage(ChatColor.RED + "No player information found! Make a character to get started.");
						return false;
						
					}
					
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
	        sender.sendMessage(ChatColor.AQUA + "Name: " + ChatColor.LIGHT_PURPLE + pc.getName());
	        sender.sendMessage(ChatColor.AQUA + "Race: " + ChatColor.LIGHT_PURPLE + pc.getRace());
	        sender.sendMessage(ChatColor.AQUA + "Class: " + ChatColor.LIGHT_PURPLE + pc.getPClass());
	        sender.sendMessage(ChatColor.AQUA + "Age: " + ChatColor.LIGHT_PURPLE + pc.getAge());
	        sender.sendMessage(ChatColor.AQUA + "Height: " + ChatColor.LIGHT_PURPLE + pc.getHeight());
	        sender.sendMessage(ChatColor.AQUA + "Bodytype: " + ChatColor.LIGHT_PURPLE + pc.getBodytype());
	        sender.sendMessage(ChatColor.AQUA + "Hometown: " + ChatColor.LIGHT_PURPLE + pc.getHometown());

	        String descString = ChatColor.AQUA + "Description: " + ChatColor.LIGHT_PURPLE + pc.getDescription();
			String[] splitDesc;
			
			splitDesc = ChatPaginator.wordWrap(descString, 20 + args[1].length());
			
			String bottomFrame = "=================";
			
			sender.sendMessage(splitDesc);
			
			for(int x = 0; x < args[1].length(); x++) {
				
				bottomFrame += "=";
				
			}
			
			sender.sendMessage(ChatColor.BLUE + bottomFrame);
			
			return true;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /cc view [Player]");
			return false;
			
		}
		
	}

}
