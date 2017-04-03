package com.chrismin13.moreminecraft.items;

import org.bukkit.Color;

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
}