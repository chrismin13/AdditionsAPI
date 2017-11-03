package com.chrismin13.additionsapi.events;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.utils.Debug;

import us.fihgu.toolbox.resourcepack.ResourcePackManager;

public class AdditionsAPIInitializationEvent extends Event {

	private List<CustomItem> cItems = new ArrayList<CustomItem>();
	private static final HandlerList handlers = new HandlerList();

	public AdditionsAPIInitializationEvent addCustomItem(CustomItem cItem) {
		Debug.sayTrue("Added Custom Item to Processing List: " + cItem.getIdName());
		Debug.saySuper("Properties: Material: " + cItem.getMaterial() + ", DisplayName: " + cItem.getDisplayName()
				+ ", ItemType: " + cItem.getItemType() + ".");
		cItems.add(cItem);
		return this;
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

	public AdditionsAPIInitializationEvent addResourcePack(JavaPlugin plugin, InputStream inputstream) {
		try {
			ResourcePackManager.registerResource(plugin, inputstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	public AdditionsAPIInitializationEvent addResourcePack(JavaPlugin plugin, File file) {
		try {
			addResourcePack(plugin, new FileInputStream(file));
		} catch (FileNotFoundException e) {
			Debug.sayError("Could not add Resource Pack! The file specified was not found in the following path: "
					+ file.getPath());
			e.printStackTrace();
		}
		return this;
	}

	public AdditionsAPIInitializationEvent addResourcePackFromPlugin(JavaPlugin plugin, String filename) {
		addResourcePack(plugin, plugin.getResource(filename));
		return this;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
