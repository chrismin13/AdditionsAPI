package com.chrismin13.moreminecraft.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;

import com.chrismin13.moreminecraft.api.items.CustomItemStack;

public final class CustomItemBlockBreakEvent extends CustomItemStackEvent implements Cancellable {

	private BlockBreakEvent blockBreakEvent;

	public CustomItemBlockBreakEvent(BlockBreakEvent event, CustomItemStack cStack) {
		super(cStack);
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
