package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerShearEntityEvent;

import com.chrismin13.moreminecraft.api.CustomItem;

public class CustomItemShearEntityEvent extends CustomItemEvent implements Cancellable {

	private PlayerShearEntityEvent playerShearEntityEvent;
	
	public CustomItemShearEntityEvent(PlayerShearEntityEvent event, CustomItem cItem) {
		super(cItem);
		this.playerShearEntityEvent = event;
	}
	
	public PlayerShearEntityEvent getPlayerShearEntityEvent() {
		return playerShearEntityEvent;
	}
	
	@Override
	public boolean isCancelled() {
		return playerShearEntityEvent.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		playerShearEntityEvent.setCancelled(true);
	}
}

