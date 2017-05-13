package com.chrismin13.additionsapi.events.bow;

import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.chrismin13.additionsapi.items.CustomItemStack;

public class EntityShootCustomBowEvent extends CustomBowStackEvent implements Cancellable {
	
	private EntityShootBowEvent playerFishEvent;
	
	public EntityShootCustomBowEvent(EntityShootBowEvent event, CustomItemStack cBowStack) {
		super(cBowStack);
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
