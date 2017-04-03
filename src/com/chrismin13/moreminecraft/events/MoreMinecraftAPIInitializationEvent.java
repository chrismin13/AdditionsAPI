package com.chrismin13.moreminecraft.events;

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

import com.chrismin13.moreminecraft.items.CustomItem;
import com.chrismin13.moreminecraft.utils.Debug;

import us.fihgu.toolbox.resourcepack.ResourcePackManager;

public class MoreMinecraftAPIInitializationEvent extends Event {

	private List<CustomItem> cItems = new ArrayList<CustomItem>();
	private static final HandlerList handlers = new HandlerList();

	public void addCustomItem(CustomItem cItem) {
		Debug.sayTrue("Added Custom Item to Processing List: " + cItem.getIdName());
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

	public void addResourcePack(JavaPlugin plugin, InputStream inputstream) {
		try {
			ResourcePackManager.registerResource(plugin, inputstream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addResourcePack(JavaPlugin plugin, File file) {
		try {
			addResourcePack(plugin, new FileInputStream(file));
		} catch (FileNotFoundException e) {
			Debug.sayError("Could not add Resource Pack! The file specified was not found in the following path: " + file.getPath());
			e.printStackTrace();
		}
	}

	public void addResourcePackFromPlugin(JavaPlugin plugin, String filename) {
		addResourcePack(plugin, plugin.getResource(filename));
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
