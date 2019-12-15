package com.chrismin13.additionsapi.files;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.EquipmentSlot;

import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomLeatherArmor;
import com.chrismin13.additionsapi.utils.EquipmentSlotUtils;
import com.comphenix.attribute.Attributes.Attribute;
import com.comphenix.attribute.Attributes.AttributeType;
import com.comphenix.attribute.Attributes.Operation;

public class CustomItemConfig {

	private boolean enabled;
	private boolean edited;
	private boolean canBeCreated;

	public CustomItemConfig(CustomItem cItem) {
		FileConfiguration config = ConfigFile.getInstance().getConfig();
		String dir = "custom-items." + new String(cItem.getIdName()).replace(":", ".").replace("_", "-");

		if (config.contains(dir) && config.getBoolean(dir + ".change-properties", false)) {
			setEnabled(config.getBoolean(dir + ".enabled"));
			if (config.contains(dir + ".name"))
				cItem.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(dir + ".name")));

			ArrayList<String> lore = new ArrayList<>();
			for (String string : config.getStringList(dir + ".lore"))
				lore.add(ChatColor.translateAlternateColorCodes('&', string));
			cItem.setLore(lore);

			setCanBeCreated(config.getBoolean(dir + ".can-be-created"));

			if (cItem.hasFakeDurability()) {
				cItem.setFakeDurability(config.getInt(dir + ".fake-durability"));
			}

			if (cItem instanceof CustomLeatherArmor && config.contains(dir + ".armor-color")) {
				((CustomLeatherArmor) cItem).setColor(config.getColor(dir + ".armor-color"));
			}

			cItem.clearAttributes();
			if (config.contains(dir + ".attributes")) {
				int i = 1;
				while (config.contains(dir + ".attributes." + i)) {
					String name = config.getString(dir + ".attributes." + i + ".name");
					AttributeType type = AttributeType.fromId(config.getString(dir + ".attributes." + i + ".type"));
					Operation operation = Operation.fromId(config.getInt(dir + ".attributes." + i + ".operation"));
					double amount = config.getDouble(dir + ".attributes." + i + ".amount");
					EquipmentSlot slot = EquipmentSlotUtils
							.valueFromAttribute(config.getString(dir + ".attributes." + i + ".slot"));
					UUID uuid = UUID.fromString(config.getString(dir + ".attributes." + i + ".uuid"));

					cItem.addAttribute(name, type, amount, slot, operation, uuid);
					i++;
				}
			}

		} else {
			config.set(dir + ".change-properties", false);
			config.set(dir + ".enabled", true);
			enabled = true;
			if (cItem.getDisplayName() != null)
				config.set(dir + ".name",
						new String(cItem.getDisplayName()).replaceAll(Character.toString(ChatColor.COLOR_CHAR), "&"));

			ArrayList<String> lore = new ArrayList<>();
			for (String string : cItem.getLore())
				lore.add(new String(string).replaceAll(Character.toString(ChatColor.COLOR_CHAR), "&"));
			config.set(dir + ".lore", lore);

			if (!cItem.getCustomRecipes().isEmpty()) {
				config.set(dir + ".can-be-created", true);
				canBeCreated = true;
			}

			if (cItem.hasFakeDurability()) {
				config.set(dir + ".fake-durability", cItem.getFakeDurability());
			}

			if (config.contains(dir + ".change-fake-durability"))
				config.set(dir + ".change-fake-durability", null);

			if (cItem instanceof CustomLeatherArmor) {
				config.set(dir + ".armor-color", ((CustomLeatherArmor) cItem).getColor());
			}

			if (!cItem.getAttributes().isEmpty()) {
				int i = 1;
				for (Attribute attribute : cItem.getAttributes()) {
					config.set(dir + ".attributes." + i + ".name", attribute.getName());
					config.set(dir + ".attributes." + i + ".type", attribute.getAttributeType().getMinecraftId());
					config.set(dir + ".attributes." + i + ".operation", attribute.getOperation().getId());
					config.set(dir + ".attributes." + i + ".amount", attribute.getAmount());
					config.set(dir + ".attributes." + i + ".slot", attribute.getSlot());
					config.set(dir + ".attributes." + i + ".uuid", attribute.getUUID().toString());
					i++;
				}
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

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

}
