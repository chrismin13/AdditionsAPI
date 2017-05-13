package com.chrismin13.additionsapi.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.durability.BowDurability;
import com.chrismin13.additionsapi.durability.ItemDurability;
import com.chrismin13.additionsapi.events.bow.EntityShootCustomBowEvent;
import com.chrismin13.additionsapi.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.additionsapi.items.CustomBow;
import com.chrismin13.additionsapi.items.CustomItemStack;

public class EntityShootCustomBow implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityShootCustomBow(EntityShootCustomBowEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		CustomBow cBow = customEvent.getCustomBow();
		EntityShootBowEvent event = customEvent.getEntityShootBowEvent();
		ItemStack item = event.getBow();
		ItemDurability mechanics = cBow.getDurabilityMechanics();
		if (event.getEntityType() == EntityType.PLAYER && mechanics instanceof BowDurability) {
			Player player = (Player) event.getEntity();
			BowDurability bowMechanics = (BowDurability) mechanics;
			Bukkit.getServer().getPluginManager().callEvent(new PlayerCustomItemDamageEvent(player, item, bowMechanics.getFireArrow(), cBow));
		}
		ArrowFromCustomBowHit.addArrow(event.getProjectile(), new CustomItemStack(item), event.getEntity());
	}
}
