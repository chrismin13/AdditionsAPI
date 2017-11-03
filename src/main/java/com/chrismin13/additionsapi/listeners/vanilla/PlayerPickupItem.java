package com.chrismin13.additionsapi.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.events.item.PlayerPickupCustomItemEvent;
import com.chrismin13.additionsapi.items.CustomItemStack;

public class PlayerPickupItem implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if (event.isCancelled())
			return;

		ItemStack item = event.getItem().getItemStack();
		if (!AdditionsAPI.isCustomItem(item))
			return;
		CustomItemStack cStack = new CustomItemStack(item);

		Bukkit.getPluginManager().callEvent(new PlayerPickupCustomItemEvent(event, cStack));
	}

}
