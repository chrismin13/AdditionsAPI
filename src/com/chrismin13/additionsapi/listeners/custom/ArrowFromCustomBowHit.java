package com.chrismin13.additionsapi.listeners.custom;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.events.bow.ArrowFromCustomBowHitEvent;
import com.chrismin13.additionsapi.items.CustomItemStack;
import com.chrismin13.additionsapi.utils.BowStackContainer;

public class ArrowFromCustomBowHit implements Listener {
	
	private static HashMap<Entity, BowStackContainer> arrowsShotFromCustomBow = new HashMap<Entity, BowStackContainer>();

	public static void addArrow(Entity entity, CustomItemStack cBowStack, LivingEntity owner) {
		arrowsShotFromCustomBow.put(entity, new BowStackContainer(cBowStack, owner));
	}
	
	public static boolean containsArrow(Entity arrow) {
		return arrowsShotFromCustomBow.containsKey(arrow);
	}

	public static BowStackContainer getBowStackContainer(Entity arrow) {
		return arrowsShotFromCustomBow.get(arrow);
	}

	public static void removeArrow(Entity arrow) {
			arrowsShotFromCustomBow.remove(arrow);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onArrowFromCustomBowHit(ArrowFromCustomBowHitEvent event) {
		Bukkit.getScheduler().runTask(AdditionsAPI.getInstance(), () -> removeArrow(event.getProjectileHitEvent().getEntity()));
	}

}
