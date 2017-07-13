package com.chrismin13.additionsapi.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.events.item.CustomItemFurnaceBurnEvent;
import com.chrismin13.additionsapi.items.CustomItemStack;

public class FurnaceBurn implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onFurnaceBurn(FurnaceBurnEvent event) {
		if (event.isCancelled())
			return;
		ItemStack fuel = event.getFuel();
		if (!AdditionsAPI.isCustomItem(fuel))
			return;
		CustomItemStack cStack = new CustomItemStack(fuel);
		Bukkit.getPluginManager().callEvent(new CustomItemFurnaceBurnEvent(event, cStack));
	}

}
