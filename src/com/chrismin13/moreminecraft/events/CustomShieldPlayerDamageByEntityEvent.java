package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.chrismin13.moreminecraft.api.CustomItem;

public class CustomShieldPlayerDamageByEntityEvent extends CustomItemEvent implements Cancellable {

	private EntityDamageByEntityEvent entityDamageByEntityEvent;
	
	public CustomShieldPlayerDamageByEntityEvent(EntityDamageByEntityEvent event, CustomItem cItem) {
		super(cItem);
		this.entityDamageByEntityEvent = event;
	}
	
	public EntityDamageByEntityEvent getEntityDamageByEntityEvent() {
		return entityDamageByEntityEvent;
	}
	
	@Override
	public boolean isCancelled() {
		return entityDamageByEntityEvent.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		entityDamageByEntityEvent.setCancelled(true);
	}

}
