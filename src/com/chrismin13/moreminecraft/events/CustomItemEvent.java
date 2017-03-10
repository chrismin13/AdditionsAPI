package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.chrismin13.moreminecraft.api.items.CustomItem;

public class CustomItemEvent extends Event {

	private CustomItem cItem;
	private static final HandlerList handlers = new HandlerList();
	
	public CustomItemEvent(CustomItem cItem) {
		this.cItem = cItem;
	}
	
	public CustomItem getCustomItem() {
		return cItem;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	
}
