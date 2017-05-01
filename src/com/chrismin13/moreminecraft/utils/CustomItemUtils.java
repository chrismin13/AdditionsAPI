package com.chrismin13.moreminecraft.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import com.chrismin13.moreminecraft.items.CustomItem;
import com.chrismin13.moreminecraft.items.CustomItemStack;
import com.chrismin13.moreminecraft.items.StorageCustomItem;
import com.chrismin13.moreminecraft.recipes.CustomRecipe;
import com.comphenix.attribute.NbtFactory;
import com.comphenix.attribute.NbtFactory.NbtCompound;
import com.google.common.collect.ImmutableList;
import com.chrismin13.moreminecraft.events.MoreMinecraftAPIInitializationEvent;
import com.chrismin13.moreminecraft.files.DataFile;

import us.fihgu.toolbox.item.ModelInjector;

public class CustomItemUtils implements Listener {

	// === VARIABLES === //
	
	private static ImmutableList<CustomItemStack> cStacks;
	private static ImmutableList<CustomItem> cItems;
	
	// === INITIALIZATION === //

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInitialization(MoreMinecraftAPIInitializationEvent event) {
		CustomItem[] cItems = event.getCustomItems();
		Debug.say("Recieved in total " + Integer.toString(cItems.length) + " Custom Items!");
		if (cItems.length == 0)
			return;
		List<CustomItemStack> cStacks = new ArrayList<CustomItemStack>();
		for (CustomItem cItem : cItems) {
			Debug.saySuper("Currently Processing: " + cItem.getIdName());
			/*
			 * Adding to data.yml and obtaining values
			 */
			DataFile dataFile = DataFile.getInstance();
			String texture = null;
			String idName = cItem.getIdName();
			if (cItem instanceof ModelInjector) {
				ModelInjector model = (ModelInjector) cItem;
				Map<String, Short> textures = model.getAllTextures();
				for (String string : textures.keySet()) {
					Debug.saySuper("Currently Processing Texure: " + string);
					short freeDurability;
					if (dataFile.getCustomItem(cItem.getIdName(), string) == null) {
						freeDurability = dataFile.getFreeDurability(cItem.getMaterial());
						Debug.saySuper("Set durability of Texture '" + string + "' to: " + freeDurability);
					} else {
						freeDurability = dataFile.getCustomItem(cItem.getIdName(), string).getDurability();
						Debug.saySuper("Set durability of Texture '" + string + "' to: " + freeDurability);
					}
					textures.put(string, freeDurability);
					if (model.getDefaultTexture().equals(string)) {
						Debug.saySuper("Texture " + string + " is default texture.");
						cItem.setDurability(freeDurability);
						texture = string;
					}
					dataFile.addStorageCustomItem(
							new StorageCustomItem(cItem.getMaterial(), freeDurability, idName, string));
				}
			}
			short durability = cItem.getDurability();
			/*
			 * CustomItems and ItemStacks
			 */
			CustomItemStack cStack = new CustomItemStack(cItem, durability, texture);
			ItemStack item = cStack.getItemStack();
			// cItems.put(idName, cItem);
			cStacks.add(cStack);
			for (CustomRecipe cRecipe : cItem.getCustomRecipes()) {
				cRecipe.registerBukkitRecipe(item);
			}
		}
		CustomItemUtils.cStacks = ImmutableList.copyOf(cStacks);
		
		List<CustomItem> cItemsList = new ArrayList<CustomItem>();	
		for (CustomItemStack cStack : cStacks)
			cItemsList.add(cStack.getCustomItem());
		CustomItemUtils.cItems = ImmutableList.copyOf(cItemsList);
		
		DataFile.getInstance().saveData();
	}

	// === STORAGE === //

	public static boolean isValidCustomItem(String idName) {
		try {
			for (CustomItemStack cStack : cStacks)
				if (cStack.getCustomItem().getIdName().equals(idName))
					return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static ImmutableList<CustomItemStack> getAllCustomItemStacks() {
		// TODO: Test if it's immutable
		return cStacks;
	}

	public static ImmutableList<CustomItem> getAllCustomItems() {
		return cItems;
	}
	
	public static void clearAll() {
		cStacks = null;
		cItems = null;
	}

	// === ITEMSTACKS === //

	public static String getIdName(ItemStack item) {
		if (item == null || item.getType().equals(Material.AIR))
			return null;
		ItemStack stack = NbtFactory.getCraftItemStack(item);
		NbtCompound nbt = NbtFactory.fromItemTag(stack);
		return nbt.getString("CustomItem.IdName", null);
	}

	public static boolean isCustomItem(ItemStack item) {
		if (getIdName(item) != null)
			return true;
		return false;
	}

	public static boolean isValidCustomItem(ItemStack item) {
		if (isCustomItem(item))
			if (isValidCustomItem(getIdName(item)))
				return true;
		return false;
	}
}
