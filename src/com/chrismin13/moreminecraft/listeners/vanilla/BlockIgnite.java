package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.moreminecraft.api.items.CustomItemStack;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.events.CustomItemBlockIgniteEvent;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class BlockIgnite implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockIgnite(BlockIgniteEvent event) {
		if (event.getPlayer() == null)
			return;
		PlayerInventory inv = event.getPlayer().getInventory();
		ItemStack item = inv.getItemInMainHand();
		if (ItemType.getItemType(item) != ItemType.FLINT_AND_STEEL) {
			item = inv.getItemInOffHand();
			if (ItemType.getItemType(item) != ItemType.FLINT_AND_STEEL)
				return;
		}
		if (CustomItemUtils.isCustomItem(item)) {
			CustomItemBlockIgniteEvent customEvent = new CustomItemBlockIgniteEvent(event, new CustomItemStack(item));
			Bukkit.getServer().getPluginManager().callEvent(customEvent);
		}
	}
}