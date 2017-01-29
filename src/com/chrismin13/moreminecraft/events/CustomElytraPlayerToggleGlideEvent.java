package com.chrismin13.moreminecraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityToggleGlideEvent;

import com.chrismin13.moreminecraft.api.CustomItem;

public class CustomElytraPlayerToggleGlideEvent extends CustomItemEvent implements Cancellable {

	private EntityToggleGlideEvent entityToggleGlideEvent;
	
	public CustomElytraPlayerToggleGlideEvent(EntityToggleGlideEvent event, CustomItem cItem) {
		super(cItem);
		this.entityToggleGlideEvent = event;
	}
	
	public EntityToggleGlideEvent getEntityToggleGlideEvent() {
		return entityToggleGlideEvent;
	}
	
	public Player getPlayer() {
		return (Player) entityToggleGlideEvent.getEntity();
	}
	
	@Override
	public boolean isCancelled() {
		return entityToggleGlideEvent.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		entityToggleGlideEvent.setCancelled(true);
	}

}
