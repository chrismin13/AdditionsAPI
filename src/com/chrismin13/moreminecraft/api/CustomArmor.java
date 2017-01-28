package com.chrismin13.moreminecraft.api;

import org.bukkit.Material;

import com.chrismin13.moreminecraft.enums.ArmorType;
import com.chrismin13.moreminecraft.enums.ItemType;

public class CustomArmor extends CustomItem {

	// === VARIABLES === //
	
	private ArmorType armorType;
	
	// === CREATING THE ARMOR === //
	
	public CustomArmor(Material material, int amount, short durability, String customItemIdName, ArmorType armorType) {
		super(material, amount, durability, customItemIdName, ItemType.ARMOR);
		this.armorType = armorType;
	}
	
	// === CONFIGURING THE ARMOR === //
	
	public ArmorType getArmorType() {
		return this.armorType;
	}

}
