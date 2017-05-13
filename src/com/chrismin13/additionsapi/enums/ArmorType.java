package com.chrismin13.additionsapi.enums;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import com.chrismin13.additionsapi.recipes.CustomShapedRecipe;
import com.chrismin13.additionsapi.recipes.RecipeIngredient;
import com.chrismin13.additionsapi.utils.Debug;
import com.chrismin13.additionsapi.utils.LangFileUtils;

public enum ArmorType {

	HELMET, CHESTPLATE, LEGGINGS, BOOTS;
	
	public String toIngameString() {
		return LangFileUtils.get(this.toString().toLowerCase());
	}
	
	// DONE: Add language file
	public String toIngameLeatherString() {
		return LangFileUtils.get("leather_" + this.toString().toLowerCase());
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

	public Material getChainmailMaterial() {
		switch (this) {
		case HELMET:
			return Material.CHAINMAIL_HELMET;
		case CHESTPLATE:
			return Material.CHAINMAIL_CHESTPLATE;
		case LEGGINGS:
			return Material.CHAINMAIL_LEGGINGS;
		case BOOTS:
			return Material.CHAINMAIL_BOOTS;
		default:
			return null;
		}
	}

	public Material getGoldMaterial() {
		switch (this) {
		case HELMET:
			return Material.GOLD_HELMET;
		case CHESTPLATE:
			return Material.GOLD_CHESTPLATE;
		case LEGGINGS:
			return Material.GOLD_LEGGINGS;
		case BOOTS:
			return Material.GOLD_BOOTS;
		default:
			return null;
		}
	}

	public Material getIronMaterial() {
		switch (this) {
		case HELMET:
			return Material.IRON_HELMET;
		case CHESTPLATE:
			return Material.IRON_CHESTPLATE;
		case LEGGINGS:
			return Material.IRON_LEGGINGS;
		case BOOTS:
			return Material.IRON_BOOTS;
		default:
			return null;
		}
	}

	public Material getDiamondMaterial() {
		switch (this) {
		case HELMET:
			return Material.DIAMOND_HELMET;
		case CHESTPLATE:
			return Material.DIAMOND_CHESTPLATE;
		case LEGGINGS:
			return Material.DIAMOND_LEGGINGS;
		case BOOTS:
			return Material.DIAMOND_BOOTS;
		default:
			return null;
		}
	}

	public EquipmentSlot getEquipmentSlot() {
		switch (this) {
		case HELMET:
			return EquipmentSlot.HEAD;
		case CHESTPLATE:
			return EquipmentSlot.CHEST;
		case LEGGINGS:
			return EquipmentSlot.LEGS;
		case BOOTS:
			return EquipmentSlot.FEET;
		default:
			return null;
		}
	}

	public List<CustomShapedRecipe> getCustomShapedRecipe(Material craftingMaterial) {
		return getCustomShapedRecipe(new RecipeIngredient(craftingMaterial));
	}
	
	public List<CustomShapedRecipe> getCustomShapedRecipe(RecipeIngredient craftingIngredient) {
		List<CustomShapedRecipe> recipes = new ArrayList<CustomShapedRecipe>();

		CustomShapedRecipe itemRecipe = new CustomShapedRecipe();

		itemRecipe.setIngredient('1', craftingIngredient);

		switch (this) {
		case HELMET:
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("111", "101", "000"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("000", "111", "101"));
			break;
		case CHESTPLATE:
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("101", "111", "111"));
			break;
		case LEGGINGS:
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("111", "101", "101"));
			break;
		case BOOTS:
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("000", "101", "101"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("101", "101", "000"));
			break;
		default:
			Debug.sayError("Invalid Armor Type: " + this.toString() + ". Is the plugin up to date?");
			break;
		}

		return recipes;
	}

	public static ArmorType getArmorType(Material material) {
		switch (material) {
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
