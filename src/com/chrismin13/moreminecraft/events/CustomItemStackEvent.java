package com.chrismin13.moreminecraft.events;

import com.chrismin13.moreminecraft.api.CustomItemStack;

public class CustomItemStackEvent extends CustomItemEvent {

	private CustomItemStack cStack;
	
	public CustomItemStackEvent(CustomItemStack cStack) {
		super(cStack.getCustomItem());
		this.cStack = cStack;
	}
	
	public CustomItemStack getCustomItemStack() {
		return cStack;
	}	
}
