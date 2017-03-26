package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.chrismin13.moreminecraft.api.items.CustomBow;

public class CustomBowEvent extends Event {

	private CustomBow cBow;
	private static final HandlerList handlers = new HandlerList();
	
	public CustomBowEvent(CustomBow cBow) {
		this.cBow = cBow;
	}
	
	public CustomBow getCustomBow() {
		return cBow;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	
}
