package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.moreminecraft.items.CustomItemStack;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.events.item.CustomItemFishEvent;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class PlayerFish implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerFishEvent(PlayerFishEvent event) {
		PlayerInventory inv = event.getPlayer().getInventory();
		ItemStack item = inv.getItemInMainHand();
		if (ItemType.getItemType(item) != ItemType.FISHING_ROD) {
			item = inv.getItemInOffHand();
			if (ItemType.getItemType(item) != ItemType.FISHING_ROD)
				return;
		}
		if (CustomItemUtils.isCustomItem(item))
			Bukkit.getServer().getPluginManager().callEvent(new CustomItemFishEvent(event, new CustomItemStack(item)));
	}

}
