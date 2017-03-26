package us.fihgu.toolbox.item;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * this class provides ItemStack/Item related shortcuts.
 */
public class ItemUtils {

	public static boolean notNullorAir(ItemStack item) {
		return item != null && item.getType() != Material.AIR;
	}

	/**
	 * create a player skull of given player
	 */
	public static ItemStack createSkull(String owner, int quantity) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, quantity, (short) SkullType.PLAYER.ordinal());
		ItemMeta meta = item.getItemMeta();
		((SkullMeta) meta).setOwner(owner);
		item.setItemMeta(meta);

		return item;
	}

	/**
	 * get the slot index of the first occurrence of the given item in given
	 * inventory.<br>
	 * returns -1 if not found. <br>
	 * Note: does not compare amount. <br>
	 */
	public static int getSlot(Inventory inv, ItemStack item) {

		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack temp = inv.getItem(i);
			if (temp != null && temp.isSimilar(item)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * remove all itemstack thats similar to given itemstack.<br>
	 * returns the number of items removed.<br>
	 */
	public static int removeAll(Inventory inv, ItemStack item) {
		int count = 0;

		for (int i = 0; i < inv.getSize(); i++) {
			ItemStack temp = inv.getItem(i);
			if (temp != null && temp.isSimilar(item)) {
				count += temp.getAmount();
				inv.setItem(i, null);
			}
		}

		return count;
	}

	/**
	 * replaces items entire lore to given lore.
	 */
	public static ItemStack setLore(ItemStack item, String[] lores) {
		return setLore(item, Arrays.asList(lores));
	}

	/**
	 * replaces items entire lore to given lore.
	 */
	public static ItemStack setLore(ItemStack item, List<String> lores) {
		ItemMeta meta = item.getItemMeta();
		itemMetaNullCheck(item);
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * replaces items entire lore to given lore.
	 */
	public static ItemStack setLore(ItemStack item, String lore) {
		return setLore(item, new String[] { lore });
	}

	/**
	 * Changes Items display name.
	 */
	public static ItemStack setDisplayName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		itemMetaNullCheck(item);
		if (name != null) {
			meta.setDisplayName(name);
		} else {
			String defaultName = Bukkit.getItemFactory().getItemMeta(item.getType()).getDisplayName();
			if (defaultName != null) {
				meta.setDisplayName(defaultName);
			} else {
				meta.setDisplayName("");
			}
		}

		item.setItemMeta(meta);
		return item;
	}

	/**
	 * adds given lore to the end of other lore.
	 */
	public static ItemStack addLore(ItemStack item, String[] lores) {
		ItemMeta meta = item.getItemMeta();
		itemMetaNullCheck(item);
		List<String> list = meta.getLore();
		if (list == null) {
			list = new LinkedList<String>();
		}
		list.addAll(Arrays.asList(lores));
		meta.setLore(list);
		item.setItemMeta(meta);

		return item;
	}

	/**
	 * adds given lore to the end of other lore.
	 */
	public static ItemStack addLore(ItemStack item, String lore) {
		return addLore(item, new String[] { lore });
	}

	/**
	 * checks if item has meta data, will create one for item if not.
	 */
	private static void itemMetaNullCheck(ItemStack item) {
		if (!item.hasItemMeta()) {
			ItemMeta meta = Bukkit.getItemFactory().getItemMeta(item.getType());
			item.setItemMeta(meta);
		}
	}

	public static String getVisibleName(ItemStack item) {
		if (item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			if (meta.hasDisplayName()) {
				return meta.getDisplayName();
			}
		}

		return item.getType().toString().toLowerCase();
	}
}
