package com.reyzerbit.RPGCore.core;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.reyzerbit.RPGCore.RPGCore;
import com.reyzerbit.RPGCore.core.io.Save;
import com.reyzerbit.RPGCore.core.structures.RPGCharacter;

import net.md_5.bungee.api.ChatColor;

public class Delete {

	public static boolean deleteCharacter(CommandSender sender, String[] args) {
		
		if(RPGCore.playerData.get(((Player) sender).getUniqueId()) == null) {
			
			sender.sendMessage(ChatColor.RED + RPGCore.errorMsg + " Delete command failed.");
			return false;
			
		}
	
		//Checks for ID for requested deletion
		if(args.length == 2 && !RPGCore.playerData.get(((Player) sender).getUniqueId()).getCharacterIDs().contains(args[1])) {
					
			sender.sendMessage(ChatColor.RED + "Unable to find character with ID " + args[1]);
			return false;
					
		} else if(args.length == 2) {
			
			for(RPGCharacter character : RPGCore.playerData.get(((Player) sender).getUniqueId()).getCharacters()) {
				
				if(character.getCharacterID().equals(args[1])) {
					
					sender.sendMessage(ChatColor.GREEN + "Deleting character with ID " + args[1]);
					RPGCore.playerData.get(((Player) sender).getUniqueId()).getCharacters().remove(character);
					
					Save.save();
					
					return true;
					
				}
				
			}
			
		} else if((sender.isOp() || sender.hasPermission("rpgcore.delete.op")) && args.length == 3) {
			
			if(Bukkit.getPlayer(args[1]) == null) {
				
				sender.sendMessage(ChatColor.RED + "No player found with name " + args[1]);
				return false;
				
			}
			
			if(RPGCore.playerData.containsKey(Bukkit.getPlayer(args[1]).getUniqueId())) {
				
				for(RPGCharacter character : RPGCore.playerData.get(Bukkit.getPlayer(args[1]).getUniqueId()).getCharacters()) {
					
					if(character.getCharacterID().equals(args[2])) {
						
						sender.sendMessage(ChatColor.GREEN + "Deleting character owned by " + args[1] + " with ID " + args[2]);
						RPGCore.playerData.get(Bukkit.getPlayer(args[1]).getUniqueId()).getCharacters().remove(character);
						
						Save.save();
						
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

}
