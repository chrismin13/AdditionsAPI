package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerInteractEvent;

import com.chrismin13.moreminecraft.api.CustomItemStack;

public class CustomItemPlayerInteractEvent extends CustomItemStackEvent implements Cancellable {

	private PlayerInteractEvent playerInteractEvent;
	
	public CustomItemPlayerInteractEvent(PlayerInteractEvent event, CustomItemStack cStack) {
		super(cStack);
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
