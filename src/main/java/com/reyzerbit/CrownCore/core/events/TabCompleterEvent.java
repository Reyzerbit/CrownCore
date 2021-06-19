package com.reyzerbit.CrownCore.core.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompleterEvent implements TabCompleter {
	
	private static final String[] commands = {"create", "delete", "list", "setactive", "view", "viewspecific", "reload", "info", 
			"setname", "setrace", "setclass", "setage", "setheight", "setbodytype", "sethometown", "setdescription"};

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
        final List<String> completions = new ArrayList<>();
        
        if(args.length == 1) {
        	
        	for(String a : commands) {
        		
        		if(a.toLowerCase().startsWith(args[0].toLowerCase())) {
        			
        			completions.add(a);
        			
        		}
        		
        	}
    		
    		return completions;
        	
        }
        
        return completions;
        
	}

}
