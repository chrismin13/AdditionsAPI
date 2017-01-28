package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.CustomBow;
import com.chrismin13.moreminecraft.events.EntityShootCustomBowEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;

public class EntityShootCustomBow implements Listener {

	@EventHandler
	public void onEntityShootCustomBow(EntityShootCustomBowEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		CustomBow cBow = customEvent.getCustomBow();
		EntityShootBowEvent event = customEvent.getEntityShootBowEvent();
		ItemStack item = event.getBow();
		if (event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();
			Bukkit.getServer().getPluginManager().callEvent(new PlayerCustomItemDamageEvent(player, item, 1, cBow));
		}
	}

}
