package com.chrismin13.additionsapi.events.item;

import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.FurnaceBurnEvent;

import com.chrismin13.additionsapi.items.CustomItemStack;

public class CustomItemFurnaceBurnEvent extends CustomItemStackEvent implements Cancellable {

	private FurnaceBurnEvent event;

	public CustomItemFurnaceBurnEvent(FurnaceBurnEvent event, CustomItemStack cStack) {
		super(cStack);
		this.setFurnaceBurnEvent(event);
	}

	/**
	 * @return the event
	 */
	public FurnaceBurnEvent getFurnaceBurnEvent() {
		return event;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setFurnaceBurnEvent(FurnaceBurnEvent event) {
		this.event = event;
	}

	@Override
	public boolean isCancelled() {
		return event.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		event.setCancelled(cancel);
	}

}
