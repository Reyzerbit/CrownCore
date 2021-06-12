package com.reyzerbit.RPGCore.core;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.reyzerbit.RPGCore.RPGCore;
import com.reyzerbit.RPGCore.core.events.CreateCharacterEvent;
import com.reyzerbit.RPGCore.core.io.Save;
import com.reyzerbit.RPGCore.core.structures.RPGCharacter;

import net.md_5.bungee.api.ChatColor;

public class Create {

	public static boolean createCharacter(CommandSender sender, String[] args) {
		
		
		//Checks for duplicate ID
		if(RPGCore.playerData.get(((Player) sender).getUniqueId()).getCharacterIDs().contains(args[1])) {
			
			sender.sendMessage(ChatColor.RED + "That ID is already in use for one of your characters!");
			return false;
			
		}
		
		//Checks for banned name
		if(!Checks.checkNames(args[2].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That name is not available on this server!");
			return false;
			
		}
		
		//Checks for banned hometowns
		if(!Checks.checkHometown(args[8].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That hometown is not available on this server!");
			return false;
			
		}
		
		//Checks race
		if(!Checks.checkRace(args[3].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That race is not available on this server!");
			return false;
			
		}
		
		//Checks class
		if(!Checks.checkClass(args[4].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That class is not available on this server!");
			return false;
			
		}
		
		//Checks body types
		if(!Checks.checkBodytype(args[7].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That bodytype is not available on this server!");
			return false;
			
		}
	
		//Checks age
		if(!Checks.checkAge(args[5])) {
				
			sender.sendMessage(ChatColor.RED + "That age is not available on this server!");
			return false;
			
		}
		
		//Checks height
		if(!Checks.checkHeight(args[6])) {
			
			sender.sendMessage(ChatColor.RED + "That height is not available on this server!");
			return false;
			
		}
		
		//Adds new character
		sender.sendMessage(ChatColor.GREEN + "Creating new character with ID " + args[1]);
		
		CreateCharacterEvent createCharacterEvent = new CreateCharacterEvent(((Player) sender).getUniqueId(), args[1]);
		
		if(!createCharacterEvent.isCancelled()) {
			
			RPGCore.playerData.get(((Player) sender).getUniqueId()).addCharacter(new RPGCharacter(args[1], args[2], args[3], args[4], Integer.parseInt(args[5]), Integer.parseInt(args[6]), args[7], args[8]));
			
			Save.save();
			return true;
			
		}
		
		sender.sendMessage(ChatColor.RED + "Event was cancelled!");
		return false;
		
	}

	public static boolean createCharacterOp(CommandSender sender, String[] args) {
		
		
		//Checks for duplicate ID
		if(RPGCore.playerData.get(Bukkit.getPlayer(args[1]).getUniqueId()).getCharacterIDs().contains(args[2])) {
			
			sender.sendMessage(ChatColor.RED + "That ID is already in use for one of " + args[1] + "'s characters!");
			return false;
			
		}
		
		//Checks for banned name
		if(!Checks.checkNames(args[3].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That name is not available on this server!");
			return false;
			
		}
		
		//Checks for banned hometowns
		if(!Checks.checkHometown(args[9].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That hometown is not available on this server!");
			return false;
			
		}
		
		//Checks race
		if(!Checks.checkRace(args[4].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That race is not available on this server!");
			return false;
			
		}
		
		//Checks class
		if(!Checks.checkClass(args[5].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That class is not available on this server!");
			return false;
			
		}
		
		//Checks bodytypes
		if(!Checks.checkBodytype(args[8].toLowerCase())) {
			
			sender.sendMessage(ChatColor.RED + "That bodytype is not available on this server!");
			return false;
			
		}
	
		//Checks age
		if(!Checks.checkAge(args[6])) {
				
			sender.sendMessage(ChatColor.RED + "That age is not available on this server!");
			return false;
			
		}
		
		//Checks height
		if(!Checks.checkHeight(args[7])) {
			
			sender.sendMessage(ChatColor.RED + "That height is not available on this server!");
			return false;
			
		}
		
		Player p = Bukkit.getPlayer(args[1]);
		
		if(p == null) {
			
			sender.sendMessage(ChatColor.RED + "No player found online with name " + args[1]);
			return false;
			
		}
		
		//Adds new character
		sender.sendMessage(ChatColor.GREEN + "Creating new character for player " + args[1] + " with ID " + args[2]);
		
		CreateCharacterEvent createCharacterEvent = new CreateCharacterEvent(p.getUniqueId(), args[2]);
		
		if(!createCharacterEvent.isCancelled()) {
			
			if(p.isOnline()) {
				
				p.sendMessage(ChatColor.GREEN + sender.getName() + " created a new character for you with ID " + args[2]);
				
			}
			
			RPGCore.playerData.get(p.getUniqueId()).addCharacter(new RPGCharacter(args[2], args[3], args[4], args[5], Integer.parseInt(args[6]), Integer.parseInt(args[7]), args[8], args[9]));
			
			Save.save();
			return true;
			
		}
		
		sender.sendMessage(ChatColor.RED + "Event was cancelled!");
		return false;
		
	}

}
