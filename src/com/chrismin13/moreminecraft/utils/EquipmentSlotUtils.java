package com.chrismin13.moreminecraft.utils;

import org.bukkit.inventory.EquipmentSlot;

public class EquipmentSlotUtils {

	public static String toAttributeString(EquipmentSlot slot) {
		if (slot == EquipmentSlot.HAND)
			return "mainhand";
		if (slot == EquipmentSlot.OFF_HAND)
			return "offhand";
		return slot.toString().toLowerCase();
	}

	public static EquipmentSlot valueFromAttribute(String string) {
		switch(string) {
		case "mainhand":
			return EquipmentSlot.HAND;
		case "offhand":
			return EquipmentSlot.OFF_HAND;
		case "head":
			return EquipmentSlot.HEAD;
		case "chest":
			return EquipmentSlot.CHEST;
		case "feet":
			return EquipmentSlot.FEET;
		case "legs":
			return EquipmentSlot.LEGS;
		default:
			return EquipmentSlot.HAND;
		}
	}

}
