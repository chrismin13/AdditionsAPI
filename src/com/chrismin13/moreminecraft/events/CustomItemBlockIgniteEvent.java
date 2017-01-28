package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockIgniteEvent;

import com.chrismin13.moreminecraft.api.CustomItem;

public class CustomItemBlockIgniteEvent extends CustomItemEvent implements Cancellable {

	private BlockIgniteEvent blockIgniteEvent;
	
	public CustomItemBlockIgniteEvent(BlockIgniteEvent event, CustomItem cItem) {
		super(cItem);
		this.blockIgniteEvent = event;
	}
	
	public BlockIgniteEvent getBlockIgniteEvent() {
		return blockIgniteEvent;
	}
	
	@Override
	public boolean isCancelled() {
		return blockIgniteEvent.isCancelled();
	}

	@Override
	public void setCancelled(boolean cancel) {
		blockIgniteEvent.setCancelled(true);
	}

}
