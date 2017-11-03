package com.chrismin13.additionsapi.listeners.custom;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.chrismin13.additionsapi.events.item.PlayerDropCustomItemEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.permissions.ItemPermissions;
import com.chrismin13.additionsapi.utils.PermissionUtils;

public class PlayerDropCustomItem implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDropCustomItemLowest(PlayerDropCustomItemEvent customEvent) {
		if (customEvent.isCancelled())
			return;

		CustomItem cItem = customEvent.getCustomItem();
		ItemPermissions perm = cItem.getPermissions();

		PlayerDropItemEvent event = customEvent.getPlayerDropItemEvent();
		if (!PermissionUtils.allowedAction(event.getPlayer(), perm.getType(), perm.getDrop()))
			event.setCancelled(true);
	}
	
}
