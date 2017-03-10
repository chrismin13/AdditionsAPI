package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.chrismin13.moreminecraft.api.items.CustomItemStack;

public class EntityShootCustomBowEvent extends CustomItemStackEvent implements Cancellable {
	
	private EntityShootBowEvent playerFishEvent;
	
	public EntityShootCustomBowEvent(EntityShootBowEvent event, CustomItemStack cStack) {
		super(cStack);
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
