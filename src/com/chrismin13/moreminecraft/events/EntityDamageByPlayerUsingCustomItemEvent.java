package com.chrismin13.moreminecraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import com.chrismin13.moreminecraft.api.CustomItem;

public class EntityDamageByPlayerUsingCustomItemEvent extends CustomItemEvent implements Cancellable {

	private EntityDamageByEntityEvent entityDamageByEntityEvent;
	
	public EntityDamageByPlayerUsingCustomItemEvent(EntityDamageByEntityEvent event, CustomItem cItem) {
		super(cItem);
		this.entityDamageByEntityEvent = event;
	}
	
	public EntityDamageByEntityEvent getEntityDamageByEntityEvent() {
		return entityDamageByEntityEvent;
	}

	public Player getPlayer() {
		return  (Player) entityDamageByEntityEvent.getDamager();
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
