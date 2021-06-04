package com.reyzerbit.RPGCore.Events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class TabCompleterRPG implements TabCompleter {
	
	private static final String[] commands = {"create", "delete", "list", "setactive", "view", "reload", "info", 
			"setname", "setrace", "setclass", "setage", "setheight", "setbodytype", "sethometown", "setdescription"};

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		
        final List<String> completions = new ArrayList<>();
        
        StringUtil.copyPartialMatches(args[1], Arrays.asList(commands), completions);
        
        Collections.sort(completions);
        
        return completions;
        
	}

}
