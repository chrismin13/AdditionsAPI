package com.chrismin13.additionsapi.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.durability.ShieldDurability;
import com.chrismin13.additionsapi.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.additionsapi.events.shield.CustomShieldPlayerDamageByEntityEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.utils.NumberUtils;

public class CustomShieldEntityDamageByEntity implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCustomShieldEntityDamageByEntity(CustomShieldPlayerDamageByEntityEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		CustomItem cItem = customEvent.getCustomItem();
		EntityDamageByEntityEvent event = customEvent.getEntityDamageByEntityEvent();
		Player player = (Player) event.getEntity();
		if (cItem.getDurabilityMechanics() instanceof ShieldDurability) {
			ItemStack item = customEvent.getCustomItemStack().getItemStack();
			if (!player.isBlocking())
				return;
			ShieldDurability mechanics = (ShieldDurability) cItem.getDurabilityMechanics();
			Bukkit.getServer().getPluginManager()
					.callEvent(new PlayerCustomItemDamageEvent(player, item,
							NumberUtils.safeLongToInt(Math.round(event.getDamage() * mechanics.getDamageMultiplier()))
									+ mechanics.getExtraDurabilityTaken(),
							cItem));
		}
	}

}
