package com.chrismin13.additionsapi.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.enums.ItemType;
import com.chrismin13.additionsapi.events.item.CustomItemShearEntityEvent;
import com.chrismin13.additionsapi.items.CustomItemStack;

public class PlayerShearEntity implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerShearEntity(PlayerShearEntityEvent event) {
		PlayerInventory inv = event.getPlayer().getInventory();
		ItemStack item = inv.getItemInMainHand();
		if (ItemType.getItemType(item) != ItemType.SHEARS) {
			item = inv.getItemInOffHand();
			if (ItemType.getItemType(item) != ItemType.SHEARS)
				return;
		}
		if (AdditionsAPI.isCustomItem(item)) {
			CustomItemShearEntityEvent customEvent = new CustomItemShearEntityEvent(event, new CustomItemStack(item));
			Bukkit.getServer().getPluginManager().callEvent(customEvent);
		}
	}

}
