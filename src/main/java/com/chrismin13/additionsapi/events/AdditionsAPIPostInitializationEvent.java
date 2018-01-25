package com.chrismin13.additionsapi.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.items.CustomItem;
import com.google.common.collect.ImmutableList;

public class AdditionsAPIPostInitializationEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	public ImmutableList<CustomItem> getCustomItems() {		
		return AdditionsAPI.getAllCustomItems();
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}