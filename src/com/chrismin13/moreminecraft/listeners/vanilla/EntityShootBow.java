package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.items.CustomItem;
import com.chrismin13.moreminecraft.api.items.CustomItemStack;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.events.ArrowFromCustomBowHitEvent;
import com.chrismin13.moreminecraft.events.EntityShootCustomBowEvent;
import com.chrismin13.moreminecraft.listeners.custom.ArrowFromCustomBowHit;
import com.chrismin13.moreminecraft.utils.BowStackContainer;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class EntityShootBow implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityShootBow(EntityShootBowEvent event) {
		ItemStack item = event.getBow();
		if (CustomItemUtils.isCustomItem(item)) {
			CustomItem cItem = CustomItemUtils.getCustomItem(item);
			if (cItem.getItemType() == ItemType.BOW) {
				Bukkit.getServer().getPluginManager()
						.callEvent(new EntityShootCustomBowEvent(event, new CustomItemStack(item)));
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onArrowHit(ProjectileHitEvent event) {
		if (event.getEntityType() != EntityType.ARROW && event.getEntityType() != EntityType.TIPPED_ARROW
				&& event.getEntityType() != EntityType.SPECTRAL_ARROW)
			return;
		if (ArrowFromCustomBowHit.containsArrow(event.getEntity())) {
			BowStackContainer container = ArrowFromCustomBowHit.getBowStackContainer(event.getEntity());
			Bukkit.getServer().getPluginManager().callEvent(
					new ArrowFromCustomBowHitEvent(event, container.getOwner(), container.getBowStack()));
		}
	}

}
