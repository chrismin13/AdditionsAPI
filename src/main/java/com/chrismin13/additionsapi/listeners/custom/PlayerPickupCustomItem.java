package com.chrismin13.additionsapi.listeners.custom;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.chrismin13.additionsapi.events.item.PlayerPickupCustomItemEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.permissions.ItemPermissions;
import com.chrismin13.additionsapi.utils.PermissionUtils;

public class PlayerPickupCustomItem implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerPickupCustomItemLowest(PlayerPickupCustomItemEvent customEvent) {
		if (customEvent.isCancelled())
			return;

		CustomItem cItem = customEvent.getCustomItem();
		ItemPermissions perm = cItem.getPermissions();

		PlayerPickupItemEvent event = customEvent.getPlayerPickupItemEvent();
		if (!PermissionUtils.allowedAction(event.getPlayer(), perm.getType(), perm.getPickup()))
			event.setCancelled(true);
	}
	
}
