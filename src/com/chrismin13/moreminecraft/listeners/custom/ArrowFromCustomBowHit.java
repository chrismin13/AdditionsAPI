package com.chrismin13.moreminecraft.listeners.custom;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.chrismin13.moreminecraft.MoreMinecraft;
import com.chrismin13.moreminecraft.api.items.CustomItemStack;
import com.chrismin13.moreminecraft.events.ArrowFromCustomBowHitEvent;
import com.chrismin13.moreminecraft.utils.BowStackContainer;

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
		if (containsArrow(arrow))
			arrowsShotFromCustomBow.remove(arrow);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onArrowFromCustomBowHit(ArrowFromCustomBowHitEvent event) {
		Bukkit.getScheduler().runTask(MoreMinecraft.getInstance(), () -> removeArrow(event.getProjectileHitEvent().getEntity()));
	}

}
