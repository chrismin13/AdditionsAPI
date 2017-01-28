package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.moreminecraft.events.CustomItemPlayerInteractEvent;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class PlayerInteract implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		ItemStack item;
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
		switch (event.getHand()) {
		case HAND:
			item = inv.getItemInMainHand();
			break;
		case OFF_HAND:
			item = inv.getItemInOffHand();
			break;
		default:
			return;
		}
		if (item.getType() == Material.AIR)
			return;
		if (CustomItemUtils.isCustomItem(item)) {
			CustomItemPlayerInteractEvent customEvent = new CustomItemPlayerInteractEvent(event,
					CustomItemUtils.getCustomItem(item));
			Bukkit.getServer().getPluginManager().callEvent(customEvent);
		}
	}

}
