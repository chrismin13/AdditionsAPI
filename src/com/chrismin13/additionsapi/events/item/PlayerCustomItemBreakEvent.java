package com.chrismin13.additionsapi.events.item;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.items.CustomItem;

public class PlayerCustomItemBreakEvent extends PlayerItemBreakEvent {
	
	private CustomItem cItem;
	
	public PlayerCustomItemBreakEvent(Player player, ItemStack brokenItem, CustomItem cItem) {
		super(player, brokenItem);
		this.cItem = cItem;
	}

	public CustomItem getCustomItem() {
		return cItem;
	}
	
}
