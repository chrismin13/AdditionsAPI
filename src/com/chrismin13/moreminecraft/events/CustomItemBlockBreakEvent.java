package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;
import com.chrismin13.moreminecraft.api.CustomItem;

public final class CustomItemBlockBreakEvent extends CustomItemEvent implements Cancellable {

	private BlockBreakEvent blockBreakEvent;

	public CustomItemBlockBreakEvent(BlockBreakEvent event, CustomItem cItem) {
		super(cItem);
		this.blockBreakEvent = event;
	}

	public BlockBreakEvent getBlockBreakEvent() {
		return blockBreakEvent;
	}

	@Override
	public void setCancelled(boolean cancel) {
		blockBreakEvent.setCancelled(cancel);
	}

	@Override
	public boolean isCancelled() {
		return blockBreakEvent.isCancelled();
	}

}
