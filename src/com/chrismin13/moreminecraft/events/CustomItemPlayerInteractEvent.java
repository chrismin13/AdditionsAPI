package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerInteractEvent;

import com.chrismin13.moreminecraft.api.CustomItem;

public class CustomItemPlayerInteractEvent extends CustomItemEvent implements Cancellable {

	private PlayerInteractEvent playerInteractEvent;
	
	public CustomItemPlayerInteractEvent(PlayerInteractEvent event, CustomItem cItem) {
		super(cItem);
		this.playerInteractEvent = event;
	}

	public PlayerInteractEvent getPlayerInteractEvent() {
		return playerInteractEvent;
	}
	
	@Override
	public boolean isCancelled() {
		return playerInteractEvent.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		playerInteractEvent.setCancelled(cancel);
	}

}
