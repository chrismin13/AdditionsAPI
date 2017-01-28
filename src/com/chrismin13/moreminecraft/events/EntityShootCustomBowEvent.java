package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.chrismin13.moreminecraft.api.CustomBow;

public class EntityShootCustomBowEvent extends CustomBowEvent implements Cancellable {
	
	private EntityShootBowEvent playerFishEvent;
	
	public EntityShootCustomBowEvent(EntityShootBowEvent event, CustomBow cBow) {
		super(cBow);
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
