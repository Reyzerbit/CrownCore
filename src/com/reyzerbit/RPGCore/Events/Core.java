package com.reyzerbit.RPGCore.Events;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.reyzerbit.RPGCore.Logic;
import com.reyzerbit.RPGCore.Main;

public class Core implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!Main.pluginEnabled) {
			
			sender.sendMessage(ChatColor.RED + "Plugin is disabled!");
			return true;
			
		}
		
		if(args.length > 0) {
			
			switch(args[0]) {
			
			case "reload":
				return reload(sender, args);
			case "info":
				return info(sender, args);
			case "create":
				return create(sender, args);
			case "delete":
				return delete(sender, args);
			case "list":
				return list(sender, args);
			case "view":
				return view(sender, args);
			case "setname":
				return setValue(sender, args);
			case "setrace":
				return setValue(sender, args);
			case "setclass":
				return setValue(sender, args);
			case "setage":
				return setValue(sender, args);
			case "setheight":
				return setValue(sender, args);
			case "setbodytype":
				return setValue(sender, args);
			case "sethometown":
				return setValue(sender, args);
			case "setdescription":
				return setValue(sender, args);
			case "setactive":
				return setactive(sender, args);
			default:
				sender.sendMessage(ChatColor.RED + "Usage: /rpg [ list | create | delete | view | setactive | set{value} ]");
				return false;
			}	
			
		} else if(sender.isOp()){
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg [ reload | info | list | create | delete | view | setactive | set{value} ]");
			return false;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /rpg [ list | create | delete | view | setactive ]");
			return false;
			
		}
		
	}
	
	private boolean setactive(CommandSender sender, String[] args) {

		Logic.setAsActive(sender, args);
		return true;
		
	}

	boolean reload(CommandSender sender, String[] args) {
		
		if(sender instanceof Player && (sender.isOp() || sender.hasPermission("rpgcore.reload"))) {
			
			Main.reload();
			
			sender.sendMessage(ChatColor.GREEN + "Reload complete!");
			
			return true;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
			
			return false;
			
		}
		
	}
	
	boolean info(CommandSender sender, String[] args) {
		
		if(sender instanceof Player && (sender.isOp() || sender.hasPermission("rpgcore.info"))) {
		
			sender.sendMessage(ChatColor.GREEN + "RPGCore by Reyzerbit");
			sender.sendMessage(ChatColor.GREEN + "Version 1.0");
			sender.sendMessage(ChatColor.GREEN + "Copyright Reyzerbit 2020 under the GNU GPL License");
			
			return true;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
			
			return false;
			
		}
		
	}
	
	boolean create(CommandSender sender, String[] args) {
		
		if(sender instanceof Player) {
			
			return Logic.createChecks(sender, args);
			
		}
		
		return false;
		
	}
	
	boolean delete(CommandSender sender, String[] args) {
		
		if(sender instanceof Player) {
			
			return Logic.deleteCharacter(sender, args);
			
		}
		
		return false;
		
	}
	
	boolean list(CommandSender sender, String[] args) {
		
		if(sender instanceof Player) {
			
			return Logic.listCharacters(sender, args);
			
		}
		
		return false;
		
	}
	
	boolean view(CommandSender sender, String[] args) {
		
		if(sender instanceof Player) {
			
			return Logic.viewCharacter(sender, args);
			
		}
		
		return false;
		
	}
	
	boolean setValue(CommandSender sender, String[] args) {
		
		if(sender instanceof Player) {
			
			return Logic.setValue(sender, args);
			
		}
		
		return false;
		
	}

}
