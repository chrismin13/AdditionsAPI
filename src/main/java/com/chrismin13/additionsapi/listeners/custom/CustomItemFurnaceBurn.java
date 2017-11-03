package com.chrismin13.additionsapi.listeners.custom;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.chrismin13.additionsapi.events.item.CustomItemFurnaceBurnEvent;
import com.chrismin13.additionsapi.items.CustomItem;

public class CustomItemFurnaceBurn implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onCustomItemFurnaceBurn(CustomItemFurnaceBurnEvent event) {
		if (event.isCancelled())
			return;
		CustomItem cItem = event.getCustomItem();
		if (cItem.getBurnTime() != 0)
			event.getFurnaceBurnEvent().setBurnTime(cItem.getBurnTime());
	}

}
