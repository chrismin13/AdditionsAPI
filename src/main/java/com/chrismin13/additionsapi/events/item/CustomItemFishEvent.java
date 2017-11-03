package com.chrismin13.additionsapi.events.item;

import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerFishEvent;

import com.chrismin13.additionsapi.items.CustomItemStack;

public class CustomItemFishEvent extends CustomItemStackEvent implements Cancellable {

	private PlayerFishEvent playerFishEvent;
	
	public CustomItemFishEvent(PlayerFishEvent event, CustomItemStack cStack) {
		super(cStack);
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
