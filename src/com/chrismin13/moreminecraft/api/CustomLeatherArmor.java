package com.chrismin13.moreminecraft.api;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.chrismin13.moreminecraft.enums.ArmorType;

public class CustomLeatherArmor extends CustomArmor {

	// === VARIABLES === //

	private Color color;

	// ================= //

	// === CREATING THE ARMOR === //

	public CustomLeatherArmor(ArmorType armorType, int amount, short durability, String customItemIdName, Color color) {
		super(armorType.getLeatherMaterial(), amount, durability, customItemIdName, armorType);
		this.color = color;
	}

	// === COLOR === //

	public Color getColor() {
		return color;
	}

	public ItemStack getItemStack() {
		ItemStack item = super.getItemStack();
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

		meta.setColor(color);

		item.setItemMeta(meta);
		return item;
	}
}
