package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.events.CustomShieldPlayerDamageByEntityEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;
import com.chrismin13.moreminecraft.utils.NumberUtils;

public class CustomShieldEntityDamageByEntity implements Listener {

	@EventHandler
	public void onCustomShieldEntityDamageByEntity(CustomShieldPlayerDamageByEntityEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		CustomItem cItem = customEvent.getCustomItem();
		EntityDamageByEntityEvent event = customEvent.getEntityDamageByEntityEvent();
		Player player = (Player) event.getEntity();
		if (cItem.getItemType() == ItemType.SHIELD) {
			ItemStack item = customEvent.getCustomItemStack().getItemStack();
			if (!player.isBlocking())
				return;
			Bukkit.getServer().getPluginManager().callEvent(new PlayerCustomItemDamageEvent(player, item,
					NumberUtils.safeLongToInt(Math.round(event.getDamage())) + 1, cItem));
		}
	}

}
