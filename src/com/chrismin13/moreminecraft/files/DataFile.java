package com.chrismin13.moreminecraft.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.moreminecraft.MoreMinecraft;
import com.chrismin13.moreminecraft.api.CustomItemStack;
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

	public void setup() {
		file = new File(plugin.getDataFolder(), "data.yml");
		YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		data.options().copyDefaults(true);
		DataFile.data = data;
		saveData();
	}

	public void addCustomItemStack(CustomItemStack stack) {

	}

	public String getTextureIdName(ItemStack item) {
		// TODO
		return "boo";
	}

	public Material getTextureMaterial(String textureIdName) {
		// TODO
		return Material.STONE;
	}

	public short getTextureDurability(String textureIdName) {
		// TODO
		return (short) 0;
	}

	public YamlConfiguration getData() {
		return data;
	}

	public void saveData() {
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
