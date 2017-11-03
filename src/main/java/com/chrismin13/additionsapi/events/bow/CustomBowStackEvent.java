package com.chrismin13.additionsapi.events.bow;

import com.chrismin13.additionsapi.items.CustomBow;
import com.chrismin13.additionsapi.items.CustomItemStack;
import com.chrismin13.additionsapi.utils.Debug;

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
			Debug.sayError(
					"The CustomItemStack specified for the event does not contain a Custom Bow as the Custom Item");
		return null;
	}
}
