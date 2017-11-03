package com.chrismin13.additionsapi.events.item;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.chrismin13.additionsapi.items.CustomItemStack;

public class PlayerDropCustomItemEvent extends CustomItemStackEvent implements Cancellable {

	private PlayerDropItemEvent playerDropItemEvent;

	public PlayerDropCustomItemEvent(PlayerDropItemEvent event, CustomItemStack cStack) {
		super(cStack);
		this.playerDropItemEvent = event;
	}

	public PlayerDropItemEvent getPlayerDropItemEvent() {
		return playerDropItemEvent;
	}

	public Player getPlayer() {
		return playerDropItemEvent.getPlayer();
	}

	@Override
	public boolean isCancelled() {
		return playerDropItemEvent.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		playerDropItemEvent.setCancelled(true);
	}
}
