package com.chrismin13.moreminecraft.utils;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.utils.attributestorage.AttributeStorage;

public class CustomItemUtils {

	private static HashMap<String, CustomItem> customItems = new HashMap<String, CustomItem>();
	private static HashMap<String, ItemStack> customItemStacks = new HashMap<String, ItemStack>();

	// === STORAGE === //

	public static boolean listsContain(String customItemIdName) {
		try {
			if (customItems.get(customItemIdName) != null && customItemStacks.get(customItemIdName) != null)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static void addToLists(CustomItem cItem) {
		String customItemIdName = cItem.getCustomItemIdName();

		if (listsContain(customItemIdName))
			return;

		ItemStack item = cItem.getItemStack();
		customItems.put(customItemIdName, cItem);
		customItemStacks.put(customItemIdName, item);

	}

	public static CustomItem getCustomItem(ItemStack item) {
		return customItems.get(getIdName(item));
	}

	public static CustomItem getCustomItem(String idName) {
		return customItems.get(idName);
	}

	public static ItemStack getCustomItemStack(ItemStack item) {
		return customItemStacks.get(getIdName(item));
	}

	public static ItemStack getCustomItemStack(String idName) {
		return customItemStacks.get(idName);
	}

	// === ITEMSTACKS === //

	private static UUID attributeStorageUUID = UUID.fromString("8c16d72b-d950-410c-b7d1-eeed86e734c7");

	public static String getIdName(ItemStack item) {
		if (item.getType() != Material.AIR)
			return AttributeStorage.newTarget(item, attributeStorageUUID).getData(null);
		return null;
	}

	public static boolean isCustomItem(ItemStack item) {
		if (item.getType() != Material.AIR && getIdName(item) != null)
			return true;
		return false;
	}

	public static int getCurrentFakeDurability(ItemStack item) {
		return getCurrentFakeDurability(item.getItemMeta().getLore());
	}

	public static int getCurrentFakeDurability(List<String> lore) {
		for (String string : lore) {
			if (string.startsWith(ChatColor.GRAY + "Durability: ")) {
				// TODO: Add language file
				String durability = string.replaceFirst(ChatColor.GRAY + "Durability: ", "");
				String segments[] = durability.split(" / ");
				return Integer.parseInt(segments[0]);
			}
		}
		return 0;
	}
}
