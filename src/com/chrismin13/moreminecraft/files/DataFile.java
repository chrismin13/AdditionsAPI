package com.chrismin13.moreminecraft.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.moreminecraft.MoreMinecraft;
import com.chrismin13.moreminecraft.api.CustomItemStack;
import com.chrismin13.moreminecraft.api.StorageCustomItem;
import com.chrismin13.moreminecraft.utils.Debug;

public class DataFile {
	private DataFile() {
	}

	static DataFile instance = new DataFile();

	public static DataFile getInstance() {
		return instance;
	}

	private static JavaPlugin plugin = MoreMinecraft.getInstance();
	private static YamlConfiguration data;
	private static File file;
	private static List<String> items;

	public void setup() {
		file = new File(plugin.getDataFolder(), "data.yml");
		YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		data.options().copyDefaults(true);
		DataFile.data = data;
		DataFile.items = data.getStringList("custom-items");
		saveData();
	}

	public void addStorageCustomItem(StorageCustomItem sItem) {
		String string = sItem.getMaterial() + ";" + sItem.getDurability() + ";" + sItem.getIdName() + ";"
				+ sItem.getTexture();
		if (!items.contains(string))
			items.add(string);
	}

	public void addCustomItemStack(CustomItemStack cStack) {
		String string = cStack.getCustomItem().getMaterial() + ";" + cStack.getItemStack().getDurability() + ";"
				+ cStack.getCustomItem().getCustomItemIdName() + ";" + cStack.getTexture();
		if (!items.contains(string))
			items.add(string);
	}

	public StorageCustomItem getCustomItem(String idName, String texture) {
		for (String string : items) {
			String[] line = string.split(";");
			if (line[2].equals(idName) && line[3].equals(texture))
				return new StorageCustomItem(Material.valueOf(line[0]), Short.valueOf(line[1]), idName, texture);
		}
		return null;
	}

	public StorageCustomItem getCustomItem(Material material, short durability) {
		String m = material.toString();
		String d = Short.toString(durability);
		for (String string : items) {
			String[] line = string.split(";");
			if (line[0].equals(m)&& line[1].equals(d))
				return new StorageCustomItem(material, durability, line[2], line[3]);
		}
		return null;
	}

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
					Debug.sayError("TEXTURE LIMIT REACHED FOR MATERIAL: " + material);
					Debug.sayError("REMOVE ANY UNUSED CUSTOM ITEMS FROM THE DATA.YML FILE!");
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

	public YamlConfiguration getData() {
		return data;
	}

	public void saveData() {
		data.set("custom-items", items);
		try {
			data.save(file);
		} catch (IOException e) {
			Debug.sayError(ChatColor.RED + "Could not save data.yml!");
			e.printStackTrace();
		}
	}

	public void reloadData() {
		data = YamlConfiguration.loadConfiguration(file);
	}
}
