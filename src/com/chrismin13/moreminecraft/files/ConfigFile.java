package com.chrismin13.moreminecraft.files;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.moreminecraft.MoreMinecraft;

public class ConfigFile {

	private ConfigFile() {
	}

	static ConfigFile instance = new ConfigFile();

	public static ConfigFile getInstance() {
		return instance;
	}

	private static JavaPlugin plugin = MoreMinecraft.getInstance();
	private static FileConfiguration config;
	private static File cfile;

	public void setup() {
		config = plugin.getConfig();
		config.options().copyDefaults(true);
		cfile = new File(plugin.getDataFolder(), "config.yml");
		saveConfig();
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public enum DebugType {
		FALSE, TRUE, SUPER;
	}

	public static DebugType getDebug() {
		String debug = config.getString("enable-debug");
		if (debug.equalsIgnoreCase("true"))
			return DebugType.TRUE;
		if (debug.equalsIgnoreCase("super"))
			return DebugType.SUPER;
		return DebugType.FALSE;
	}

	public void saveConfig() {
		try {
			config.save(cfile);
		} catch (IOException e) {
			Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save config.yml!");
		}
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(cfile);
	}

	public PluginDescriptionFile getDesc() {
		return plugin.getDescription();
	}
}
