package com.chrismin13.moreminecraft.enums;

import org.bukkit.Material;

public enum ArmorType {
	
	HELMET, CHESTPLATE, LEGGINGS, BOOTS;

	// TODO: Add language file
	public String toLeatherArmorString() {
		switch (this) {
		case BOOTS:
			return "Boots";
		case CHESTPLATE:
			return "Tunic";
		case HELMET:
			return "Cap";
		case LEGGINGS:
			return "Pants";
		default:
			return null;
		}
	}

	public Material getLeatherMaterial() {
		switch (this) {
		case HELMET:
			return Material.LEATHER_HELMET;
		case CHESTPLATE:
			return Material.LEATHER_CHESTPLATE;
		case LEGGINGS:
			return Material.LEATHER_LEGGINGS;
		case BOOTS:
			return Material.LEATHER_BOOTS;
		default:
			return null;
		}
	}

	public static ArmorType getArmorType(Material material) {
		switch(material) {
		// Leather
		case LEATHER_HELMET:
			return HELMET;
		case LEATHER_CHESTPLATE:
			return CHESTPLATE;
		case LEATHER_LEGGINGS:
			return LEGGINGS;
		case LEATHER_BOOTS:
			return BOOTS;
		// Chain
		case CHAINMAIL_HELMET:
			return HELMET;
		case CHAINMAIL_CHESTPLATE:
			return CHESTPLATE;
		case CHAINMAIL_LEGGINGS:
			return LEGGINGS;
		case CHAINMAIL_BOOTS:
			return BOOTS;
		// Iron
		case IRON_HELMET:
			return HELMET;
		case IRON_CHESTPLATE:
			return CHESTPLATE;
		case IRON_LEGGINGS:
			return LEGGINGS;
		case IRON_BOOTS:
			return BOOTS;
		// Gold
		case GOLD_HELMET:
			return HELMET;
		case GOLD_CHESTPLATE:
			return CHESTPLATE;
		case GOLD_LEGGINGS:
			return LEGGINGS;
		case GOLD_BOOTS:
			return BOOTS;
		// Diamond
		case DIAMOND_HELMET:
			return HELMET;
		case DIAMOND_CHESTPLATE:
			return CHESTPLATE;
		case DIAMOND_LEGGINGS:
			return LEGGINGS;
		case DIAMOND_BOOTS:
			return BOOTS;
		default:
			return null;
		}
	}
	
}
