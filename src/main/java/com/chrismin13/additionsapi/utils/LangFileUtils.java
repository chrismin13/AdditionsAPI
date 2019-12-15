package com.chrismin13.additionsapi.utils;

import com.chrismin13.additionsapi.files.LangFile;
import com.comphenix.attribute.Attributes.AttributeType;

public class 	LangFileUtils {

	public static String get(String entryName) {
		return LangFile.getInstance().getEntry("more_minecraft", entryName);
	}

	public static String get(AttributeType type) {
		if (type.equals(AttributeType.ARMOR)) {
			return LangFileUtils.get("attribute_armor");
		} else if (type.equals(AttributeType.ARMOR_TOUGHNESS)) {
			return LangFileUtils.get("attribute_armor_toughness");
		} else if (type.equals(AttributeType.GENERIC_ATTACK_DAMAGE)) {
			return LangFileUtils.get("attribute_generic_attack_damage");
		} else if (type.equals(AttributeType.GENERIC_ATTACK_SPEED)) {
			return LangFileUtils.get("attribute_generic_attack_speed");
		} else if (type.equals(AttributeType.GENERIC_FOLLOW_RANGE)) {
			return LangFileUtils.get("attribute_generic_follow_range");
		} else if (type.equals(AttributeType.GENERIC_KNOCKBACK_RESISTANCE)) {
			return LangFileUtils.get("attribute_generic_knockback_resistance");
		} else if (type.equals(AttributeType.GENERIC_LUCK)) {
			return LangFileUtils.get("attribute_generic_luck");
		} else if (type.equals(AttributeType.GENERIC_MAX_HEALTH)) {
			return LangFileUtils.get("attribute_generic_max_health");
		} else if (type.equals(AttributeType.GENERIC_MOVEMENT_SPEED)) {
			return LangFileUtils.get("attribute_generic_movement_speed");
		} else if (type.equals(AttributeType.HORSE_JUMP_STRENGTH)) {
			return LangFileUtils.get("attribute_horse_jump_strength");
		} else if (type.equals(AttributeType.ZOMBIE_SPAWN_REINFORCEMENTS)) {
			return LangFileUtils.get("attribute_zombie_spawn_reinforcements");
		} else {
			return "Unknown Attribute";
		}
	}

}
