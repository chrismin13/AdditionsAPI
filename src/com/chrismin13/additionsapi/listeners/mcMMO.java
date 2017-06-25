package com.chrismin13.additionsapi.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomItemStack;
import com.gmail.nossr50.events.items.ItemDurabilityChangeEvent;
import com.gmail.nossr50.events.items.ItemDurabilityCheckEvent;
import com.gmail.nossr50.events.items.ItemMaxDurabilityCheckEvent;

public class mcMMO implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDurabilityChange(ItemDurabilityChangeEvent event) {
		if (event.isCancelled())
			return;
		ItemStack item = event.getItemStack();
		short finalDur = event.getFinalDurability();
		if (AdditionsAPI.isCustomItem(item)) {
			CustomItemStack cStack = new CustomItemStack(item);
			event.setCancelled(true);
			cStack.setFakeDurability(cStack.getCustomItem().getFakeDurability() - finalDur);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onDurabilityCheck(ItemDurabilityCheckEvent event) {
		ItemStack item = event.getItemStack();
		if (AdditionsAPI.isCustomItem(item)) {
			CustomItemStack cStack = new CustomItemStack(item);
			if (cStack.getFakeDurability() > Short.MAX_VALUE || cStack.getFakeDurability() < Short.MIN_VALUE)
				event.setDurability((short) 0);
			else
				event.setDurability((short) cStack.getFakeDurability());
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onDurabilityCheck(ItemMaxDurabilityCheckEvent event) {
		ItemStack item = event.getItemStack();
		if (AdditionsAPI.isCustomItem(item)) {
			CustomItem cItem = new CustomItemStack(item).getCustomItem();
			if (cItem.getFakeDurability() > Short.MAX_VALUE || cItem.getFakeDurability() < Short.MIN_VALUE)
				event.setDurability((short) 0);
			else
				event.setDurability((short) cItem.getFakeDurability());
		}
	}

}
