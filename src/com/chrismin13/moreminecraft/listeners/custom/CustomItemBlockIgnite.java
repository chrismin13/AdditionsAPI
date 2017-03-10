package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.durability.FlintAndSteelDurability;
import com.chrismin13.moreminecraft.api.durability.ItemDurability;
import com.chrismin13.moreminecraft.api.items.CustomItem;
import com.chrismin13.moreminecraft.events.CustomItemBlockIgniteEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;

public class CustomItemBlockIgnite implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCustomItemBlockIgnite(CustomItemBlockIgniteEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		CustomItem cItem = customEvent.getCustomItem();
		BlockIgniteEvent event = customEvent.getBlockIgniteEvent();
		Player player = event.getPlayer();
		ItemDurability mechanics = cItem.getDurabilityMechanics();
		if (mechanics instanceof FlintAndSteelDurability) {
			ItemStack item = customEvent.getCustomItemStack().getItemStack();
			FlintAndSteelDurability fsMechanics = (FlintAndSteelDurability) cItem.getDurabilityMechanics();
			Bukkit.getServer().getPluginManager().callEvent(new PlayerCustomItemDamageEvent(player, item, fsMechanics.getFire(), cItem));
		}
	}

}
