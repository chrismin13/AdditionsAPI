package com.chrismin13.moreminecraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.chrismin13.moreminecraft.api.items.CustomItemStack;

public class EntityDamageByPlayerUsingCustomItemEvent extends CustomItemStackEvent implements Cancellable {

	private EntityDamageByEntityEvent entityDamageByEntityEvent;

	public EntityDamageByPlayerUsingCustomItemEvent(EntityDamageByEntityEvent event, CustomItemStack cStack) {
		super(cStack);
		this.entityDamageByEntityEvent = event;
	}

	public EntityDamageByEntityEvent getEntityDamageByEntityEvent() {
		return entityDamageByEntityEvent;
	}

	public Player getPlayer() {
		return (Player) entityDamageByEntityEvent.getDamager();
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
