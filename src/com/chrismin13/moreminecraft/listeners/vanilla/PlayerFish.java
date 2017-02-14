package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.moreminecraft.api.CustomItemStack;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.events.CustomItemFishEvent;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class PlayerFish implements Listener {

	@EventHandler
	public void onPlayerFishEvent(PlayerFishEvent event) {
		PlayerInventory inv = event.getPlayer().getInventory();
		ItemStack item = inv.getItemInMainHand();
		if (!CustomItemUtils.isCustomItem(item) || CustomItemUtils.getCustomItem(item).getItemType() != ItemType.FISHING_ROD) {
			item = inv.getItemInOffHand();
			if (!CustomItemUtils.isCustomItem(item) || CustomItemUtils.getCustomItem(item).getItemType() != ItemType.FISHING_ROD)
				return;
		}
		Bukkit.getServer().getPluginManager()
				.callEvent(new CustomItemFishEvent(event, new CustomItemStack(item)));
	}

}
