package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerFishEvent;

import com.chrismin13.moreminecraft.api.CustomItem;

public class CustomItemFishEvent extends CustomItemEvent implements Cancellable {

	private PlayerFishEvent playerFishEvent;
	
	public CustomItemFishEvent(PlayerFishEvent event, CustomItem cItem) {
		super(cItem);
		this.playerFishEvent = event;
	}
	
	public PlayerFishEvent getPlayerFishEvent() {
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
