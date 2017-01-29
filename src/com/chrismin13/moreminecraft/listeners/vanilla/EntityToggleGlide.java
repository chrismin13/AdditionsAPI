package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.moreminecraft.events.CustomElytraPlayerToggleGlideEvent;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class EntityToggleGlide implements Listener {

	@EventHandler
	public void onEntityToggleGlide(EntityToggleGlideEvent event) {
		if (event.getEntityType() != EntityType.PLAYER)
			return;
		PlayerInventory inv = ((Player) event.getEntity()).getInventory();
		if (inv.getChestplate() == null)
			return;
		ItemStack elytra = inv.getChestplate();
		if (elytra.getType() != Material.ELYTRA || !CustomItemUtils.isCustomItem(elytra))
			return;
		Bukkit.getServer().getPluginManager()
				.callEvent(new CustomElytraPlayerToggleGlideEvent(event, CustomItemUtils.getCustomItem(elytra)));
	}

}
