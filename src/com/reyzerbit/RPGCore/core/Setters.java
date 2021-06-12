package com.reyzerbit.RPGCore.core;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.reyzerbit.RPGCore.RPGCore;
import com.reyzerbit.RPGCore.core.events.SetActiveCharacterEvent;
import com.reyzerbit.RPGCore.core.io.Save;
import com.reyzerbit.RPGCore.core.structures.RPGCharacter;
import com.reyzerbit.RPGCore.core.structures.RPGPlayer;

import net.md_5.bungee.api.ChatColor;

public class Setters {

	public static void setAsActive(CommandSender sender, String[] args) {
		
		if(args.length == 2) {
			
			Player p = (Player) sender;
			
			SetActiveCharacterEvent setActiveEvent = new SetActiveCharacterEvent(p.getUniqueId(), args[1]);
			
			Bukkit.getPluginManager().callEvent(setActiveEvent);
			
			if(!setActiveEvent.isCancelled()) {
				
				sender.sendMessage(ChatColor.GREEN + "Setting your active character to " + args[1]);
				
				RPGPlayer rpgP = RPGCore.playerData.get(p.getUniqueId());
				
				String oldGroupID = null;
				
				if(rpgP.hasActiveCharacter()) oldGroupID = RPGCore.config.getString(rpgP.getActiveCharacter().getPClass() + "group");
				
				rpgP.setActiveCharacter(args[1]);
				
				setClassGroup(p, RPGCore.config.getString(rpgP.getActiveCharacter().getPClass() + "group"), oldGroupID);
				
				Save.save();
				
			}
			
		} else if(args.length == 3 && (sender.hasPermission("rpgcore.setactive.op") || sender.isOp())) {
			
			Player p = Bukkit.getPlayer(args[1]);
			
			if(p == null) {
				
				sender.sendMessage(ChatColor.RED + "No player found online with name " + args[1]);
				return;
				
			}
			
			SetActiveCharacterEvent setActiveEvent = new SetActiveCharacterEvent(p.getUniqueId(), args[2]);
			
			Bukkit.getPluginManager().callEvent(setActiveEvent);
			
			if(!setActiveEvent.isCancelled()) {
				
				sender.sendMessage(ChatColor.GREEN + "Setting " + args[1] + "'s active character to " + args[2]);
				
				RPGPlayer rpgP = RPGCore.playerData.get(p.getUniqueId());
				
				String oldGroupID = null;
				
				if(rpgP.hasActiveCharacter()) oldGroupID = RPGCore.config.getString(rpgP.getActiveCharacter().getPClass() + "group");
				
				rpgP.setActiveCharacter(args[1]);
				
				setClassGroup(p, RPGCore.config.getString(rpgP.getActiveCharacter().getPClass() + "group"), oldGroupID);
				
				p.sendMessage(ChatColor.GREEN + sender.getName() + " set your active character to " + args[2]);
				
				Save.save();
				
			}
			
		} else if (sender.hasPermission("rpgcore.setactive.op") || sender.isOp()) { 
			
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg setactive [Player] [Character ID]");
		
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg setactive [Character ID]");
			
		}
		
	}
	
	public static boolean setValue(CommandSender sender, String[] args) {
		
		if(args.length == 3) {
			
			Player p = (Player) sender;
			
			RPGPlayer tempPlayer = RPGCore.playerData.get(p.getUniqueId());
			
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
					if(Checks.checkNames(args[2])) tempCharacter.setName(args[2]);
					break;
				case "race":
					if(Checks.checkRace(args[2])) tempCharacter.setRace(args[2]);
					break;
				case "class":
					if(Checks.checkClass(args[2])) tempCharacter.setPClass(args[2]);
					break;
				case "bodytype":
					if(Checks.checkBodytype(args[2])) tempCharacter.setBodytype(args[2]);
					break;
				case "hometown":
					if(Checks.checkHometown(args[2])) tempCharacter.setHometown(args[2]);
					break;
				case "age":
					if(Checks.checkAge(args[2])) tempCharacter.setAge(Integer.parseInt(args[2]));
					break;
				case "height" :
					if(Checks.checkHeight(args[2])) tempCharacter.setHeight(Integer.parseInt(args[2]));
					break;
				
			}
			
			sender.sendMessage(ChatColor.GREEN + "Setting " + args[1] + "'s " + args[0].substring(3).toLowerCase() + " to " + args[2] + ".");
			
			Save.save();
			
			return true;
			
		} else if(args.length > 3 && args[0].toLowerCase().equals("setdescription")){
			
			Player p = (Player) sender;
			
			RPGPlayer tempPlayer = RPGCore.playerData.get(p.getUniqueId());
			
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
			Save.save();
			return true;
			
		} else if(args.length == 4 && (sender.isOp() || sender.hasPermission("rpgcore.setvalue.op"))){
			
			Player p = Bukkit.getPlayer(args[1]);
			
			if(p == null) {
				
				sender.sendMessage(ChatColor.RED + "No player found with the name " + args[1] + "!");
				return false;
				
			}
			
			RPGPlayer tempPlayer = RPGCore.playerData.get(p.getUniqueId());
			
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
					if(Checks.checkNames(args[3])) tempCharacter.setName(args[3]);
					break;
				case "race":
					if(Checks.checkRace(args[3])) tempCharacter.setRace(args[3]);
					break;
				case "class":
					if(Checks.checkClass(args[3])) tempCharacter.setPClass(args[3]);
					break;
				case "bodytype":
					if(Checks.checkBodytype(args[3])) tempCharacter.setBodytype(args[3]);
					break;
				case "hometown":
					if(Checks.checkHometown(args[3])) tempCharacter.setHometown(args[3]);
					break;
				case "age":
					if(Checks.checkAge(args[3])) tempCharacter.setAge(Integer.parseInt(args[3]));
					break;
				case "height" :
					if(Checks.checkHeight(args[3])) tempCharacter.setHeight(Integer.parseInt(args[3]));
					break;
				
			}
			
			sender.sendMessage(ChatColor.GREEN + "Setting " + args[1] + "'s " + args[0].substring(3).toLowerCase() + " to " + args[2] + ".");
			
			Save.save();
			
			return true;
			
		} else if(args.length > 4 && args[0].toLowerCase().equals("setdescription") && (sender.isOp() || sender.hasPermission("rpgcore.setvalue.op"))){
			
			Player p = Bukkit.getPlayer(args[1]);
			
			RPGPlayer tempPlayer = RPGCore.playerData.get(p.getUniqueId());
			
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
			Save.save();
			return true;
			
		} else if(sender.isOp() || sender.hasPermission("rpgcore.setvalue.op")){
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg set[value] [Player] [Character ID] [value]");
			return false;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg set[value] [Character ID] [value]");
			return false;
			
		}
		
	}
	
	public static void setClassGroup(Player p, String group, String oldGroup) {
		
		if(group == null || group.equals("")) return;
		
		if(!oldGroup.equals("") || oldGroup != null) {
			
			RPGCore.perms.playerRemoveGroup(p, oldGroup);
			
		}
		
		RPGCore.perms.playerAddGroup(p, group);
		
	}
	
}
