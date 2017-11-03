package com.chrismin13.additionsapi.events.item;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.chrismin13.additionsapi.items.CustomItemStack;

public class PlayerPickupCustomItemEvent extends CustomItemStackEvent implements Cancellable {

	private PlayerPickupItemEvent playerPickupItemEvent;

	public PlayerPickupCustomItemEvent(PlayerPickupItemEvent event, CustomItemStack cStack) {
		super(cStack);
		this.playerPickupItemEvent = event;
	}

	public PlayerPickupItemEvent getPlayerPickupItemEvent() {
		return playerPickupItemEvent;
	}

	public Player getPlayer() {
		return playerPickupItemEvent.getPlayer();
	}

	@Override
	public boolean isCancelled() {
		return playerPickupItemEvent.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		playerPickupItemEvent.setCancelled(true);
	}
}
