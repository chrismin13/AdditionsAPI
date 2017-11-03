package com.chrismin13.additionsapi.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.events.item.PlayerDropCustomItemEvent;
import com.chrismin13.additionsapi.items.CustomItemStack;

public class PlayerDropItem implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (event.isCancelled())
			return;

		ItemStack item = event.getItemDrop().getItemStack();

		if (!AdditionsAPI.isCustomItem(item))
			return;
		CustomItemStack cStack = new CustomItemStack(item);

		Bukkit.getPluginManager().callEvent(new PlayerDropCustomItemEvent(event, cStack));
	}

}
