package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.durability.ShearDurability;
import com.chrismin13.moreminecraft.events.item.CustomItemShearEntityEvent;
import com.chrismin13.moreminecraft.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.moreminecraft.items.CustomItem;

public class CustomItemShearEntity implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCustomItemShearEntity(CustomItemShearEntityEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		CustomItem cItem = customEvent.getCustomItem();
		PlayerShearEntityEvent event = customEvent.getPlayerShearEntityEvent();
		Player player = event.getPlayer();
		if (cItem.getDurabilityMechanics() instanceof ShearDurability) {
			ItemStack item = customEvent.getCustomItemStack().getItemStack();
			ShearDurability mechanics = (ShearDurability) cItem.getDurabilityMechanics();
			Bukkit.getServer().getPluginManager()
					.callEvent(new PlayerCustomItemDamageEvent(player, item, mechanics.getShearSheep(), cItem));
		}
	}
}
