package com.reyzerbit.CrownCore.core.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.reyzerbit.CrownCore.CrownCore;
import com.reyzerbit.CrownCore.core.Checks;
import com.reyzerbit.CrownCore.core.Delete;
import com.reyzerbit.CrownCore.core.Setters;
import com.reyzerbit.CrownCore.core.ViewList;

public class RPGCommandEvent implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!sender.hasPermission("crowncore.cc")) return false;
		
		if(!CrownCore.pluginEnabled) {
			
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
			case "viewspecific":
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
				sender.sendMessage(ChatColor.RED + "Usage: /cc [ list | create | delete | view | setactive | set{value} ]");
				return false;
			}	
			
		} else if(sender.isOp()){
			
			sender.sendMessage(ChatColor.RED + "Usage: /cc [ reload | info | list | create | delete | view | setactive | set{value} ]");
			return false;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "Usage: /cc [ list | create | delete | view | setactive ]");
			return false;
			
		}
		
	}
	
	private boolean setactive(CommandSender sender, String[] args) {

		if(sender instanceof Player && sender.hasPermission("crowncore.setactive")) {
			
			Setters.setAsActive(sender, args);
			return true;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + " You do not have permission to use that command!");
			
		}
		
		return false;
		
	}

	boolean reload(CommandSender sender, String[] args) {
		
		if(sender instanceof Player && (sender.isOp() || sender.hasPermission("crowncore.reload"))) {
			
			CrownCore.reload();
			
			sender.sendMessage(ChatColor.GREEN + "Reload complete!");
			
			return true;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
			
			return false;
			
		}
		
	}
	
	boolean info(CommandSender sender, String[] args) {
		
		if(sender instanceof Player && (sender.isOp() || sender.hasPermission("crowncore.info"))) {
		
			sender.sendMessage(ChatColor.GREEN + "CrownCore by Reyzerbit");
			sender.sendMessage(ChatColor.GREEN + "Version " + Bukkit.getPluginManager().getPlugin("CrownCore").getDescription().getVersion());
			sender.sendMessage(ChatColor.GREEN + "Copyright Reyzerbit 2020 under the GNU GPL License");
			
			return true;
			
		} else {
			
			sender.sendMessage(ChatColor.RED + "You dont have permission to use this command!");
			
			return false;
			
		}
		
	}
	
	boolean create(CommandSender sender, String[] args) {
		
		if(sender instanceof Player && sender.hasPermission("crowncore.create")) {
			
			return Checks.checkCreate(sender, args);
			
		} else {
			
			sender.sendMessage(ChatColor.RED + " You do not have permission to use that command!");
			
		}
		
		return false;
		
	}
	
	boolean delete(CommandSender sender, String[] args) {
		
		if(sender instanceof Player && sender.hasPermission("crowncore.delete")) {
			
			return Delete.deleteCharacter(sender, args);
			
		} else {
			
			sender.sendMessage(ChatColor.RED + " You do not have permission to use that command!");
			
		}
		
		return false;
		
	}
	
	boolean list(CommandSender sender, String[] args) {
		
		if(sender instanceof Player && sender.hasPermission("crowncore.list")) {
			
			return ViewList.listCharacters(sender, args);
			
		} else {
			
			sender.sendMessage(ChatColor.RED + " You do not have permission to use that command!");
			
		}
		
		return false;
		
	}
	
	boolean view(CommandSender sender, String[] args) {
		
		if(sender instanceof Player && sender.hasPermission("crowncore.view")) {
			
			if(args.length == 3 && args[0].equalsIgnoreCase("viewspecific")) {
				
				return ViewList.viewCharacter(sender, args, args[2]);
				
			} else if(args[0].equalsIgnoreCase("viewspecific")) {
				
				sender.sendMessage(ChatColor.RED + "Usage: /cc viewspecific [Player] [CharacterId]");
				return false;
				
			} else {
			
				return ViewList.viewCharacter(sender, args, "");
				
			}
			
		} else {
			
			sender.sendMessage(ChatColor.RED + " You do not have permission to use that command!");
			
		}
		
		return false;
		
	}
	
	boolean setValue(CommandSender sender, String[] args) {
		
		if(sender instanceof Player && sender.hasPermission("crowncore.setvalue")) {
			
			return Setters.setValue(sender, args);
			
		} else {
			
			sender.sendMessage(ChatColor.RED + " You do not have permission to use that command!");
			
		}
		
		return false;
		
	}

}
