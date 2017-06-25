package com.chrismin13.additionsapi.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.utils.Debug;

/**
 * Offers an easy way to access the config.yml without having to get the
 * plugin's instance every time. Instead, most of the config's values can be
 * received easily without having to know the value names.<br>
 * <b>You must use {@link #getInstance()} to access the file and it's
 * values.</b>
 * 
 * @author chrismin13
 *
 */
public class ConfigFile {

	private ConfigFile() {
	}

	private static ConfigFile instance = new ConfigFile();

	public static ConfigFile getInstance() {
		return instance;
	}

	private static JavaPlugin plugin = AdditionsAPI.getInstance();
	private static FileConfiguration config;
	private static File cfile;

	/**
	 * This method is run when the plugin is enabled and when reloading. Not
	 * meant to be used in any other occasion.
	 */
	public ConfigFile setup() {
		config = plugin.getConfig();
		config.options().copyDefaults(true);
		cfile = new File(plugin.getDataFolder(), "config.yml");
		saveConfig();
		return this;
	}

	/**
	 * @return A {@link FileConfiguration} of the Config File.
	 */
	public FileConfiguration getConfig() {
		return config;
	}

	/**
	 * The Debug Type. Defined in the config. Specifies how advanced the Debug
	 * messages will be.
	 * 
	 * @author chrismin13
	 */
	public enum DebugType {
		FALSE, TRUE, SUPER;
	}

	/**
	 * @return Get the {@link DebugType} that is specified in the config.
	 */
	public DebugType getDebug() {
		String debug = config.getString("enable-debug");
		if (debug.equalsIgnoreCase("true"))
			return DebugType.TRUE;
		if (debug.equalsIgnoreCase("super"))
			return DebugType.SUPER;
		return DebugType.FALSE;
	}

	/**
	 * Saves the Config File.
	 */
	public ConfigFile saveConfig() {
		try {
			config.save(cfile);
		} catch (IOException e) {
			Debug.sayError(ChatColor.RED
					+ "Could not save config.yml! Is it in use by another program? Is there enough space?");
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Reloads the Config File, updating any changes.
	 */
	public ConfigFile reloadConfig() {
		config = YamlConfiguration.loadConfiguration(cfile);
		return this;
	}

	/**
	 * @return The Config sub-section for the BossBar.
	 */
	public BossBarConfig getBossBarConfig() {
		return new BossBarConfig(config.getBoolean("bossbar.show"), config.getBoolean("bossbar.vanilla-items"),
				config.getBoolean("bossbar.custom-items"));
	}
}
