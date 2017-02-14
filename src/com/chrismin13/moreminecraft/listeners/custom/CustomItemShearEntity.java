package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.events.CustomItemShearEntityEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;

public class CustomItemShearEntity implements Listener {

	@EventHandler
	public void onCustomItemShearEntity(CustomItemShearEntityEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		CustomItem cItem = customEvent.getCustomItem(); 
		PlayerShearEntityEvent event = customEvent.getPlayerShearEntityEvent();
		Player player = event.getPlayer();
		if (cItem.getItemType() == ItemType.SHEARS) {
			ItemStack item = customEvent.getCustomItemStack().getItemStack();
			Bukkit.getServer().getPluginManager().callEvent(new PlayerCustomItemDamageEvent(player, item, 1, cItem));
		}
	}
}
