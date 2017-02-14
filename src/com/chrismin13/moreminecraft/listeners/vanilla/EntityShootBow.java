package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.api.CustomItemStack;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.events.EntityShootCustomBowEvent;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class EntityShootBow implements Listener {

	@EventHandler
	public void onEntityShootBow(EntityShootBowEvent event) {
		ItemStack item = event.getBow();
		if (CustomItemUtils.isCustomItem(item)) {
			CustomItem cItem = CustomItemUtils.getCustomItem(item);
			if (cItem.getItemType() == ItemType.BOW) {
				Bukkit.getServer().getPluginManager()
						.callEvent(new EntityShootCustomBowEvent(event, new CustomItemStack(item)));
			}
		}
	}

}
