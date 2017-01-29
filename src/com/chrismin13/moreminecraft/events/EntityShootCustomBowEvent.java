package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.chrismin13.moreminecraft.api.CustomItem;

public class EntityShootCustomBowEvent extends CustomItemEvent implements Cancellable {
	
	private EntityShootBowEvent playerFishEvent;
	
	public EntityShootCustomBowEvent(EntityShootBowEvent event, CustomItem cItem) {
		super(cItem);
		this.playerFishEvent = event;
	}
	
	public EntityShootBowEvent getEntityShootBowEvent() {
		return playerFishEvent;
	}
	
	@Override
	public boolean isCancelled() {
		return playerFishEvent.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		playerFishEvent.setCancelled(true);
	}
	
}
