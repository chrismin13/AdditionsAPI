package com.chrismin13.moreminecraft.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.utils.Debug;

public class MoreMinecraftAPIInitializationEvent extends Event {

	private List<CustomItem> cItems = new ArrayList<CustomItem>();
	private static final HandlerList handlers = new HandlerList();

	public void addCustomItem(CustomItem cItem) {
		Debug.sayTrue("Added Custom Item: " + cItem.getCustomItemIdName());
		Debug.saySuper("Properties: Material: " + cItem.getMaterial() + ", DisplayName: " + cItem.getDisplayName()
				+ ", ItemType: " + cItem.getItemType() + ".");
		cItems.add(cItem);
	}

	public CustomItem[] getCustomItems() {
		CustomItem[] cItems = new CustomItem[this.cItems.size()];
		int index = 0;
		for (CustomItem cItem : this.cItems) {
			cItems[index] = cItem;
			index++;
		}
		return cItems;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
