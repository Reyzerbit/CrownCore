package com.reyzerbit.CrownCore.core.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.reyzerbit.CrownCore.CrownCore;

public class TabCompleterEvent implements TabCompleter {
	
	private static final String[] commands = {"create", "delete", "list", "setactive", "view", "viewspecific", "reload", "info", 
			"setname", "setrace", "setclass", "setage", "setheight", "setbodytype", "sethometown", "setdescription"};
	
	private static final String[] commands1 = {"delete", "setactive", "setname", "setrace", "setclass", "setage", "setheight", "setbodytype", "sethometown", "setdescription"};
	private static final List<String> commands1List = Arrays.asList(commands1);

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
        final List<String> completions = new ArrayList<>();
        
        if(args.length == 1) {
        	
        	for(String a : commands) {
        		
        		if(a.toLowerCase().startsWith(args[0].toLowerCase())) {
        			
        			completions.add(a);
        			
        		}
        		
        	}
        	
        } else if(args.length == 2 && commands1List.contains(args[0].toLowerCase()) ){
        	
        	for(String a : CrownCore.playerData.get(((Player)sender).getUniqueId()).getCharacterIDs()) {
        		
        		if(a.toLowerCase().startsWith(args[1].toLowerCase())) {
        			
        			completions.add(a);
        			
        		}
        		
        	}
        	
        }
        
        return completions;
        
	}

}
