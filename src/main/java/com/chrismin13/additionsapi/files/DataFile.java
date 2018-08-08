package com.chrismin13.additionsapi.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.items.CustomItemStack;
import com.chrismin13.additionsapi.items.StorageCustomItem;
import com.chrismin13.additionsapi.utils.Debug;

/**
 * Offers an easy way to access the data.yml without having to get the plugin's
 * instance every time. Instead, most of the file's values can be received
 * easily without having to know the value names.<br>
 * <b>You must use {@link #getInstance()} to access the file and it's
 * values.</b>
 * 
 * @author chrismin13
 *
 */
public class DataFile {
	private DataFile() {
	}

	private static DataFile instance = new DataFile();

	public static DataFile getInstance() {
		return instance;
	}

	private static JavaPlugin plugin = AdditionsAPI.getInstance();
	private static YamlConfiguration data;
	private static File file;
	private static List<String> items;

	/**
	 * This method is run when the plugin is enabled and when reloading. Not meant
	 * to be used in any other occasion.
	 */
	public DataFile setup() {
		file = new File(plugin.getDataFolder(), "data.yml");
		YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		data.options().copyDefaults(true);
		DataFile.data = data;
		DataFile.items = data.getStringList("custom-items");

		// 1.13 Migration
		if (data.getInt("data-version", 0) < 1) {
			ArrayList<String> items = new ArrayList<String>();
			for (String item : DataFile.items) {
				String[] itemSplit = item.split(";");
				itemSplit[0] = itemSplit[0].replaceAll("SPADE", "SHOVEL").replaceAll("WOOD_", "WOODEN_")
						.replaceAll("GOLD_", "GOLDEN_");

				String finalItem = "";
				for (String string : itemSplit)
					finalItem = finalItem + string + ";";
				finalItem = finalItem.substring(0, finalItem.length() - 1);
				items.add(finalItem);
			}
			DataFile.items = items;
			data.set("data-version", 1);
		}
		saveData();
		return this;
	}

	/**
	 * Add a new {@link StorageCustomItem} to be saved in the data.yml file. <br>
	 * <b>You must also use {@link #saveData()} to save the changes that you
	 * made.</b>
	 * 
	 * @param sItem
	 *            - The {@link StorageCustomItem} to store in the data.yml.
	 */
	public DataFile addStorageCustomItem(StorageCustomItem sItem) {
		String string = sItem.getMaterial() + ";" + sItem.getDurability() + ";" + sItem.getIdName() + ";"
				+ sItem.getTexture();
		items.add(string);
		return this;
	}

	/**
	 * Converts a {@link CustomItemStack} to a {@link StorageCustomItem} to be saved
	 * in the data.yml file. <br>
	 * <b>You must also use {@link #saveData()} to save the changes that you
	 * made.</b>
	 * 
	 * @param cStack
	 *            - The {@link CustomItemStack} to convert and save.
	 */
	public DataFile addCustomItemStack(CustomItemStack cStack) {
		String string = cStack.getCustomItem().getMaterial() + ";" + cStack.getItemStack().getDurability() + ";"
				+ cStack.getCustomItem().getIdName() + ";" + cStack.getTexture();
		items.add(string);
		return this;
	}

	/**
	 * Obtains a {@link StorageCustomItem} with the specified values. If the
	 * {@link StorageCustomItem} does not exist, it will return null.
	 * 
	 * @param idName
	 *            The ID Name of the {@link StorageCustomItem}.
	 * @param texture
	 *            The Texture of the {@link StorageCustomItem}
	 * @return The {@link StorageCustomItem} with the above values, as well as its
	 *         Material and durability.
	 */
	public StorageCustomItem getCustomItem(String idName, String texture) {
		for (String string : items) {
			String[] line = string.split(";");
			if (line[2].equals(idName) && line[3].equals(texture))
				return new StorageCustomItem(Material.valueOf(line[0]), Short.valueOf(line[1]), idName, texture);
		}
		return null;
	}

	/**
	 * Obtains a {@link StorageCustomItem} with the specified values. If the
	 * {@link StorageCustomItem} does not exist, it will return null.
	 * 
	 * @param material
	 *            The Material of the {@link StorageCustomItem}.
	 * @param durability
	 *            The durability of the {@link StorageCustomItem}
	 * @return The {@link StorageCustomItem} with the above values, as well as its
	 *         ID Name and Texture Name.
	 */
	public StorageCustomItem getCustomItem(Material material, short durability) {
		String m = material.toString();
		String d = Short.toString(durability);
		for (String string : items) {
			String[] line = string.split(";");
			if (line[0].equals(m) && line[1].equals(d))
				return new StorageCustomItem(material, durability, line[2], line[3]);
		}
		return null;
	}

	/**
	 * Get a durability value that does not use a texture for the specified
	 * Material. If there are no more durability points left, an error will be
	 * printed.
	 * 
	 * @param material
	 *            The Material for which you want a free durability point.
	 * @return The durability value that is available for the specified Material.
	 */
	public short getFreeDurability(Material material) {
		String m = material.toString();
		List<Short> durabilitiesUsed = new ArrayList<Short>();
		for (String string : items) {
			String[] line = string.split(";");
			if (line[0].equals(m)) {
				durabilitiesUsed.add(Short.valueOf(line[1]));
			}
		}
		Debug.saySuper("Included in durabilities used:");
		for (Short s : durabilitiesUsed)
			Debug.saySuper(s.toString());
		if (durabilitiesUsed.isEmpty()) {
			Debug.saySuper("No Durabilities Used for Material: " + material);
			return material.getMaxDurability();
		} else {
			if (material.getMaxDurability() != 0) {
				short durability = material.getMaxDurability();
				while (durabilitiesUsed.contains(durability))
					durability--;
				Debug.saySuper("Available Durability for Material " + material + ": " + durability);
				if (durability <= 0) {
					Debug.sayError("***********************");
					Debug.sayError("TEXTURE LIMIT REACHED FOR MATERIAL: " + material);
					Debug.sayError("REMOVE ANY CUSTOM ITEMS FROM THE DATA.YML FILE THAT ARE FROM REMOVED PLUGINS!");
					Debug.sayError("***********************");
					return (short) (material.getMaxDurability() + 1);
				} else {
					return durability;
				}
			} else {
				Debug.saySuper("Returning default durability for Material: " + material);
				return material.getMaxDurability();
			}
		}
	}

	/**
	 * @return A {@link YamlConfiguration} of the data.yml file.
	 */
	public YamlConfiguration getData() {
		return data;
	}

	/**
	 * Saves the Data File.
	 */
	public DataFile saveData() {
		data.set("custom-items", items);
		try {
			data.save(file);
		} catch (IOException e) {
			Debug.sayError(
					ChatColor.RED + "Could not save data.yml! Is it in use by another program? Is there enough space?");
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Reloads the Data File, updating any changes.
	 */
	public DataFile reloadData() {
		data = YamlConfiguration.loadConfiguration(file);
		return this;
	}
}
