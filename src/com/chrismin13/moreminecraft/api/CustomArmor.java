package com.chrismin13.moreminecraft.api;

import org.bukkit.Material;
import com.chrismin13.moreminecraft.enums.ArmorType;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.AttributeType;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.Operation;

public class CustomArmor extends CustomItem {

	// === VARIABLES === //
	
	private ArmorType armorType;
	
	// === CREATING THE ARMOR === //
	
	public CustomArmor(Material material, int amount, short durability, String customItemIdName, ArmorType armorType) {
		super(material, amount, durability, customItemIdName, ItemType.ARMOR);
		this.armorType = armorType;
		super.setCustomTexture(false);
	}
	
	// === CONFIGURING THE ARMOR === //
	
	public ArmorType getArmorType() {
		return this.armorType;
	}
	
	public void addAttribute(AttributeType type, Double amount, Operation operation) {
		super.addAttribute(type, amount, armorType.getEquipmentSlot(), operation);
	}

}
