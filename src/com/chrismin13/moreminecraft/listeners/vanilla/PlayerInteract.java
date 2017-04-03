package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.moreminecraft.events.item.CustomItemPlayerInteractEvent;
import com.chrismin13.moreminecraft.items.CustomItemStack;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class PlayerInteract implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteract(PlayerInteractEvent event) {
		ItemStack item;
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
		if (event.getHand() != null) {
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
			if (item.getType() == Material.AIR || item == null)
				return;
			if (CustomItemUtils.isCustomItem(item)) {
				CustomItemPlayerInteractEvent customEvent = new CustomItemPlayerInteractEvent(event,
						new CustomItemStack(item));
				Bukkit.getServer().getPluginManager().callEvent(customEvent);
			}
		}
	}

}
