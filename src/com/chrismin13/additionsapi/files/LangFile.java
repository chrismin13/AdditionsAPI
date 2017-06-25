package com.chrismin13.additionsapi.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.utils.Debug;

/**
 * Offers an easy way to access the lang.yml without having to get the plugin's
 * instance every time. Instead, most of the file's values can be received
 * easily without having to know the value names.<br>
 * <b>You must use {@link #getInstance()} to access the file and it's
 * values.</b>
 * 
 * @author chrismin13
 *
 */
public class LangFile {
	private LangFile() {
	}

	private static LangFile instance = new LangFile();

	public static LangFile getInstance() {
		return instance;
	}

	private static JavaPlugin plugin = AdditionsAPI.getInstance();
	private static YamlConfiguration data;
	private static File file;

	/**
	 * This method is run when the plugin is enabled and when reloading. Not
	 * meant to be used in any other occasion.
	 */
	public LangFile setup() {
		file = new File(plugin.getDataFolder(), "lang.yml");
		YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		data.options().copyDefaults(true);
		LangFile.data = data;
		saveLang();
		return this;
	}

	/**
	 * Add a lang.yml entry.
	 * 
	 * @param pluginName
	 *            The name of your plugin. This is similar to the ID Name first
	 *            part for {@link CustomItem}s. For example: "additons_api" or
	 *            "vanilla_additions".
	 * @param entryName
	 *            The name of the entry.
	 * @param value
	 *            The default value of the entry. This will not be overriden if
	 *            there is a value already in place in the file.
	 */
	public LangFile addEntry(String pluginName, String entryName, String value) {
		if (data.getString(pluginName + "." + entryName) == null)
			data.set(pluginName + "." + entryName, value);
		return this;
	}

	/**
	 * Get an entry's value from the lang.yml file.
	 * 
	 * @param pluginName
	 *            The name of your plugin. This is similar to the ID Name first
	 *            part for {@link CustomItem}s. For example: "additons_api" or
	 *            "vanilla_additions".
	 * @param entryName
	 *            The name of the entry.
	 */
	public String getEntry(String pluginName, String entryName) {
		return data.getString(pluginName + "." + entryName);
	}

	/**
	 * @return A {@link YamlConfiguration} of the lang.yml file.
	 */
	public YamlConfiguration getLang() {
		return data;
	}

	/**
	 * Saves the Lang File.
	 */
	public LangFile saveLang() {
		try {
			data.save(file);
		} catch (IOException e) {
			Debug.sayError(
					ChatColor.RED + "Could not save lang.yml! Is it in use by another program? Is there enough space?");
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Reloads the Lang File, updating any changes.
	 */
	public LangFile reloadLang() {
		data = YamlConfiguration.loadConfiguration(file);
		return this;
	}
}
