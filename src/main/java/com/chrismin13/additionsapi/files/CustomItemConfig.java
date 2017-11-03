package com.chrismin13.additionsapi.files;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import com.chrismin13.additionsapi.items.CustomItem;

public class CustomItemConfig {

	private boolean enabled;
	private boolean canBeCreated;

	public CustomItemConfig(CustomItem cItem) {
		FileConfiguration config = ConfigFile.getInstance().getConfig();
		String dir = "custom-items." + new String(cItem.getIdName()).replace(":", ".").replace("_", "-");

		if (config.contains(dir)) {
			setEnabled(config.getBoolean(dir + ".enabled"));
			cItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(dir + ".name")));

			ArrayList<String> lore = new ArrayList<>();
			for (String string : config.getStringList(dir + ".lore"))
				lore.add(ChatColor.translateAlternateColorCodes('&', string));
			cItem.setLore(lore);
			
			setCanBeCreated(config.getBoolean(dir + ".can-be-created"));
		} else {
			config.set(dir + ".enabled", true);
			enabled = true;
			config.set(dir + ".name", new String(cItem.getDisplayName()).replaceAll(Character.toString(ChatColor.COLOR_CHAR), "&"));

			ArrayList<String> lore = new ArrayList<>();
			for (String string : cItem.getLore())
				lore.add(new String(string).replaceAll(Character.toString(ChatColor.COLOR_CHAR), "&"));
			config.set(dir + ".lore", lore);

			if (!cItem.getCustomRecipes().isEmpty()) {
				config.set(dir + ".can-be-created", true);
				canBeCreated = true;
			}
		}

	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean canBeCreated() {
		return canBeCreated;
	}

	public void setCanBeCreated(boolean canBeCreated) {
		this.canBeCreated = canBeCreated;
	}

}
