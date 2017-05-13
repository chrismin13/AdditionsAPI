package com.chrismin13.additionsapi.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.events.item.EntityDamageByPlayerUsingCustomItemEvent;
import com.chrismin13.additionsapi.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.additionsapi.items.CustomItem;

public class EntityDamageByPlayerUsingCustomItem implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamageByCustomItem(EntityDamageByPlayerUsingCustomItemEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		CustomItem cItem = event.getCustomItem();
		ItemStack item = event.getCustomItemStack().getItemStack();
		PlayerCustomItemDamageEvent damageEvent = new PlayerCustomItemDamageEvent(player, item,
				cItem.getDurabilityMechanics().getEntityHit(), cItem);
		if (damageEvent.getDamage() != 0)
			Bukkit.getServer().getPluginManager().callEvent(damageEvent);
	}

}
