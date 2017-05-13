package com.chrismin13.additionsapi.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.utils.Debug;

public class LangFile {
	private LangFile() {
	}

	static LangFile instance = new LangFile();

	public static LangFile getInstance() {
		return instance;
	}

	private static JavaPlugin plugin = AdditionsAPI.getInstance();
	private static YamlConfiguration data;
	private static File file;

	public void setup() {
		file = new File(plugin.getDataFolder(), "lang.yml");
		YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		data.options().copyDefaults(true);
		LangFile.data = data;
		saveData();
	}
	
	public LangFile addEntry(String pluginName, String entryName, String value) {
		if(data.getString(pluginName + "." + entryName) == null)
			data.set(pluginName + "." + entryName, value);
		return this;
	}
	
	public String getEntry(String pluginName, String entryName) {
		return data.getString(pluginName + "." + entryName);
	}
	
	public YamlConfiguration getData() {
		return data;
	}

	public void saveData() {
		try {
			data.save(file);
		} catch (IOException e) {
			Debug.sayError(ChatColor.RED + "Could not save lang.yml! Is it in use by another program? Is there enough space?");
			e.printStackTrace();
		}
	}

	public void reloadData() {
		data = YamlConfiguration.loadConfiguration(file);
	}
}
