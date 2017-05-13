package com.chrismin13.additionsapi.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.enums.ItemType;
import com.chrismin13.additionsapi.events.elytra.CustomElytraPlayerToggleGlideEvent;
import com.chrismin13.additionsapi.items.CustomItemStack;

public class EntityToggleGlide implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityToggleGlide(EntityToggleGlideEvent event) {
		if (event.getEntityType() != EntityType.PLAYER)
			return;
		PlayerInventory inv = ((Player) event.getEntity()).getInventory();
		if (inv.getChestplate() == null)
			return;
		ItemStack elytra = inv.getChestplate();
		if (!AdditionsAPI.isCustomItem(elytra)
				|| new CustomItemStack(elytra).getCustomItem().getItemType() != ItemType.ELYTRA)
			return;
		Bukkit.getServer().getPluginManager()
				.callEvent(new CustomElytraPlayerToggleGlideEvent(event, new CustomItemStack(elytra)));
	}

}
