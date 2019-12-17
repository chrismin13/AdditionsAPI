package com.chrismin13.additionsapi.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.enums.ItemType;
import com.chrismin13.additionsapi.events.bow.ArrowFromCustomBowHitEvent;
import com.chrismin13.additionsapi.events.bow.EntityShootCustomBowEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomItemStack;
import com.chrismin13.additionsapi.listeners.custom.ArrowFromCustomBowHit;
import com.chrismin13.additionsapi.utils.BowStackContainer;

public class EntityShootBow implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityShootBow(EntityShootBowEvent event) {
		ItemStack item = event.getBow();
		if (AdditionsAPI.isCustomItem(item)) {
			CustomItemStack cStack = new CustomItemStack(item);
			CustomItem cItem = cStack.getCustomItem();
			if (cItem.getItemType() == ItemType.BOW) {
				Bukkit.getServer().getPluginManager().callEvent(new EntityShootCustomBowEvent(event, cStack));
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onArrowHit(ProjectileHitEvent event) {
		if (event.getEntityType() != EntityType.ARROW && event.getEntityType() != EntityType.SPECTRAL_ARROW)
			return;
		if (ArrowFromCustomBowHit.containsArrow(event.getEntity())) {
			BowStackContainer container = ArrowFromCustomBowHit.getBowStackContainer(event.getEntity());
			Bukkit.getServer().getPluginManager()
					.callEvent(new ArrowFromCustomBowHitEvent(event, container.getOwner(), container.getBowStack()));
		}
	}

}
