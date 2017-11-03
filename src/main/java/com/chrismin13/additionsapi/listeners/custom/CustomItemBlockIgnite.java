package com.chrismin13.additionsapi.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.durability.FlintAndSteelDurability;
import com.chrismin13.additionsapi.durability.ItemDurability;
import com.chrismin13.additionsapi.events.item.CustomItemBlockIgniteEvent;
import com.chrismin13.additionsapi.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.permissions.FlintAndSteelPermissions;
import com.chrismin13.additionsapi.utils.PermissionUtils;

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

	@EventHandler(priority = EventPriority.LOWEST)
	public void onCustomItemBlockIgniteLowest(CustomItemBlockIgniteEvent customEvent) {
		if (customEvent.isCancelled())
			return;

		CustomItem cItem = customEvent.getCustomItem();

		if (!(cItem.getPermissions() instanceof FlintAndSteelPermissions))
			return;
		FlintAndSteelPermissions perm = (FlintAndSteelPermissions) cItem.getPermissions();

		BlockIgniteEvent event = customEvent.getBlockIgniteEvent();

		if (!PermissionUtils.allowedAction(event.getPlayer(), perm.getType(), perm.getFire()))
			event.setCancelled(true);
	}
	
}
