package com.reyzerbit.CrownCore.core;

import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;

import com.reyzerbit.CrownCore.CrownCore;
import com.reyzerbit.CrownCore.core.structures.CrownCharacter;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Conversion {

	public static TextComponent convertToClickCommand(String clickText, String followingText, String id, String key) {
		
		TextComponent mainText = new TextComponent();
		
		TextComponent clickableMessage = new TextComponent(clickText);
		
		clickableMessage.setColor(ChatColor.AQUA);
		clickableMessage.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/cc set" + key + " " + id + " "));
		clickableMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				new ComponentBuilder(ChatColor.GREEN + "Click to change " + key + "!").create()));
		
		mainText.addExtra(clickableMessage);
		mainText.addExtra(ChatColor.LIGHT_PURPLE + followingText);
		
		return mainText;
		
	}

	public static CrownCore.protectValues convertConfigEnums(String value) {
		
		if(value.toLowerCase().equals("black")) {
			
			return CrownCore.protectValues.BLACK;
			
		} else if(value.toLowerCase().equals("white")) {
			
			return CrownCore.protectValues.WHITE;
			
		} else if(value.toLowerCase().equals("none")) {
			
			return CrownCore.protectValues.NONE;
			
		} else {
			
			CrownCore.getPlugin(CrownCore.class).getLogger().log(Level.SEVERE, "Something is wrong with your config! En error occured while reading protected values (whitelist, blacklist, none).");
			return null;
			
		}
		
	}

	public static void checkNullString(ConfigurationSection cs, String key, CrownCharacter c, String s) {
		
		if(cs.getString(key) != null) {
			
			switch(key) {
				case "id":
					c.setCharacterID(s);
					break;
				case "name" :
					c.setName(s);
					break;
				case "race":
					c.setRace(s);
					break;
				case "class":
					c.setPClass(s);
					break;
				case "bodytype":
					c.setBodytype(s);
					break;
				case "hometown":
					c.setHometown(s);
					break;
				case "description":
					c.setDescription(s);
					break;
					
			}
					
					
		} else {
			
			switch(key) {
			case "id":
				c.setCharacterID("");
				break;
			case "name" :
				c.setName("");
				break;
			case "race":
				c.setRace("");
				break;
			case "class":
				c.setPClass("");
				break;
			case "bodytype":
				c.setBodytype("");
				break;
			case "hometown":
				c.setHometown("");
				break;
			case "description":
				c.setDescription("");
				break;
				
		}
			
		}
		
	}

	public static void checkNullInt(ConfigurationSection cs, String key, CrownCharacter c, int i) {
			
		switch(key) {
			case "age":
				c.setAge(i);
				break;
			case "height" :
				c.setHeight(i);
				break;
		}
		
	}

	public static TextComponent convertToClickList(String target, String id, boolean isActive) {
		
		TextComponent clickableText = new TextComponent(id);
		
		clickableText.setColor(ChatColor.LIGHT_PURPLE);
		clickableText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cc viewspecific " + target + " " + id));
		clickableText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
				new ComponentBuilder(ChatColor.GREEN + "Click to view " + id).create()));
		
		if(isActive) {
			
			TextComponent mainText = new TextComponent();
			
			mainText.addExtra(clickableText);
			mainText.addExtra(ChatColor.LIGHT_PURPLE + " (active)");
			
			return mainText;
			
		} else {
			
			return clickableText;
			
		}
		
	}
	
}
