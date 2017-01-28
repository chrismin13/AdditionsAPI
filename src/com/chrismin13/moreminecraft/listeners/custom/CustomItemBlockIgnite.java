package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.events.CustomItemBlockIgniteEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;

public class CustomItemBlockIgnite implements Listener {
	
	@EventHandler
	public void onCustomItemBlockIgnite(CustomItemBlockIgniteEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		CustomItem cItem = customEvent.getCustomItem();
		BlockIgniteEvent event = customEvent.getBlockIgniteEvent();
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
		if (cItem.getItemType() == ItemType.FLINT_AND_STEEL) {
			ItemStack item;
			if (inv.getItemInMainHand().getType() == Material.FLINT_AND_STEEL) {
				item = inv.getItemInMainHand(); 
			} else if (inv.getItemInOffHand().getType() == Material.FLINT_AND_STEEL) {
				item = inv.getItemInOffHand();
			} else {
				return;
			}
			Bukkit.getServer().getPluginManager().callEvent(new PlayerCustomItemDamageEvent(player, item, 1, cItem));
		}
	}

}
