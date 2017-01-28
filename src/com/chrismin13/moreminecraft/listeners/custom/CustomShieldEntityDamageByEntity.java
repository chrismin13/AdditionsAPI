package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
		PlayerInventory inv = player.getInventory();
		if (cItem.getItemType() == ItemType.SHIELD) {
			ItemStack item;
			if (inv.getItemInMainHand().getType() == Material.SHIELD) {
				item = inv.getItemInMainHand();
			} else if (inv.getItemInOffHand().getType() == Material.SHIELD) {
				item = inv.getItemInOffHand();
			} else {
				return;
			}
			if (!player.isBlocking())
				return;
			Bukkit.getServer().getPluginManager().callEvent(new PlayerCustomItemDamageEvent(player, item,
					NumberUtils.safeLongToInt(Math.round(event.getDamage())) + 1, cItem));
		}
	}

}
