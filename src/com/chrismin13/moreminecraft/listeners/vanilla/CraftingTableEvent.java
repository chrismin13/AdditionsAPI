package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class CraftingTableEvent extends CustomItemUtils implements Listener {

	@EventHandler
	public void prepareCrafting(PrepareItemCraftEvent event) {
		ItemStack[] matrix = event.getInventory().getMatrix();
		/*
		 * Fix for the Bug that occurred when combining two CustomItems as they
		 * are a lower durability version of an item that already exists in-game
		 */
		if (event.isRepair()) {
			for (ItemStack item : matrix) {
				if (isCustomItem(item)) {
					CustomItem cItem = getCustomItem(item);
					if (!cItem.isCombinableInCrafting()) {
						event.getInventory().setResult(new ItemStack(Material.AIR));
						break;
					}
				}
			}
		}
	}

}
