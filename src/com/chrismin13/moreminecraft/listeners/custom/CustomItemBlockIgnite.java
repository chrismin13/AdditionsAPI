package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.inventory.ItemStack;
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
		if (cItem.getItemType() == ItemType.FLINT_AND_STEEL) {
			ItemStack item = customEvent.getCustomItemStack().getItemStack();
			Bukkit.getServer().getPluginManager().callEvent(new PlayerCustomItemDamageEvent(player, item, 1, cItem));
		}
	}

}
