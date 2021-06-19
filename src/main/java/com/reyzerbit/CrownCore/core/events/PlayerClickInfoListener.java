package com.reyzerbit.CrownCore.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.reyzerbit.CrownCore.CrownCore;
import com.reyzerbit.CrownCore.core.ViewList;

import net.md_5.bungee.api.ChatColor;

public class PlayerClickInfoListener implements Listener {

	@EventHandler
    public void onPlayerClickPlayer(PlayerInteractEntityEvent event) {

		if(event.getRightClicked() instanceof Player && CrownCore.playerData.containsKey(event.getRightClicked().getUniqueId()) && event.getPlayer().isSneaking() && event.getHand().equals(EquipmentSlot.HAND) && event.getPlayer().hasPermission("crowncore.viewclick")) {
			
			String[] viewCommand = {"view", ((Player)event.getRightClicked()).getDisplayName()};
			
			ViewList.viewCharacter(event.getPlayer(), viewCommand, "");
			
		} else if(event.getRightClicked() instanceof Player) {
			
			event.getPlayer().sendMessage(ChatColor.AQUA + "This player does not have any player data yet!");
			
		}
		
    }
	
}
