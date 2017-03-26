package com.chrismin13.moreminecraft.events;

import com.chrismin13.moreminecraft.api.items.CustomBow;
import com.chrismin13.moreminecraft.api.items.CustomItemStack;

public class CustomBowStackEvent extends CustomBowEvent {

	private CustomItemStack cStack;
	
	public CustomBowStackEvent(CustomItemStack cBowStack) {
		super(checkStackForBow(cBowStack));
		this.cStack = cBowStack;
	}
	
	public CustomItemStack getCustomBowItemStack() {
		return cStack;
	}
	
	public static CustomBow checkStackForBow(CustomItemStack cStack) {
		if (cStack.getCustomItem() instanceof CustomBow) 
			return (CustomBow) cStack.getCustomItem();
		else
			try {
				throw new Exception("The CustomItemStack specified for the event does not contain a Custom Bow as the Custom Item");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
}
