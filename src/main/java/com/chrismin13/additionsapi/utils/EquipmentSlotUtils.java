package com.chrismin13.additionsapi.utils;

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
	
	public static String valueFromLangFile(EquipmentSlot slot) {
		switch(slot) {
		case CHEST:
			return LangFileUtils.get("when_in_body");
		case FEET:
			return LangFileUtils.get("when_in_feet");
		case HAND:
			return LangFileUtils.get("when_in_main_hand");
		case HEAD:
			return LangFileUtils.get("when_in_head");
		case LEGS:
			return LangFileUtils.get("when_in_legs");
		case OFF_HAND:
			return LangFileUtils.get("when_in_off_hand");
		default:
			return null;
		}
	}

}
