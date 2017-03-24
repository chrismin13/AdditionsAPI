package com.chrismin13.moreminecraft.enums;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.chrismin13.moreminecraft.api.durability.AxeDurability;
import com.chrismin13.moreminecraft.api.durability.HoeDurability;
import com.chrismin13.moreminecraft.api.durability.ItemDurability;
import com.chrismin13.moreminecraft.api.durability.PickaxeDurability;
import com.chrismin13.moreminecraft.api.durability.SpadeDurability;
import com.chrismin13.moreminecraft.api.durability.SwordDurability;
import com.chrismin13.moreminecraft.api.recipes.CustomShapedRecipe;
import com.chrismin13.moreminecraft.utils.Debug;

public enum ToolType {

	SWORD, AXE, PICKAXE, SPADE, HOE;

	public ItemDurability getDurabilityMechanics() {
		switch (this) {
		case AXE:
			return new AxeDurability();
		case HOE:
			return new HoeDurability();
		case PICKAXE:
			return new PickaxeDurability();
		case SPADE:
			return new SpadeDurability();
		case SWORD:
			return new SwordDurability();
		default:
			return new ItemDurability();
		}
	}

	public List<CustomShapedRecipe> getCustomShapedRecipe(Material itemMaterial, Material stickMaterial) {
		List<CustomShapedRecipe> recipes = new ArrayList<CustomShapedRecipe>();

		CustomShapedRecipe itemRecipe = new CustomShapedRecipe();

		itemRecipe.setIngredient('1', itemMaterial);
		itemRecipe.setIngredient('2', stickMaterial);

		switch (this) {
		case SWORD:
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("100", "100", "200"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("010", "010", "020"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("001", "001", "002"));
			break;
		case AXE:
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("110", "210", "200"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("110", "120", "020"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("011", "021", "020"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("011", "012", "002"));

			break;
		case PICKAXE:
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("111", "020", "020"));
			break;
		case SPADE:
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("100", "200", "200"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("010", "020", "020"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("001", "002", "002"));
			break;
		case HOE:
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("110", "200", "200"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("110", "020", "020"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("011", "020", "020"));
			recipes.add(new CustomShapedRecipe(itemRecipe).setShape("011", "002", "002"));
			break;
		default:
			Debug.sayError("Invalid Tool Type: " + this.toString() + ". Is the plugin up to date?");
			break;
		}

		return recipes;
	}

	public static ToolType getToolType(Material material) {
		switch (material) {
		// Swords
		case WOOD_SWORD:
			return SWORD;
		case GOLD_SWORD:
			return SWORD;
		case STONE_SWORD:
			return SWORD;
		case IRON_SWORD:
			return SWORD;
		case DIAMOND_SWORD:
			return SWORD;
		// Axe
		case WOOD_AXE:
			return AXE;
		case GOLD_AXE:
			return AXE;
		case STONE_AXE:
			return AXE;
		case IRON_AXE:
			return AXE;
		case DIAMOND_AXE:
			return AXE;
		// Pickaxe
		case WOOD_PICKAXE:
			return PICKAXE;
		case STONE_PICKAXE:
			return PICKAXE;
		case IRON_PICKAXE:
			return PICKAXE;
		case GOLD_PICKAXE:
			return PICKAXE;
		case DIAMOND_PICKAXE:
			return PICKAXE;
		// Spades
		case WOOD_SPADE:
			return SPADE;
		case STONE_SPADE:
			return SPADE;
		case IRON_SPADE:
			return SPADE;
		case GOLD_SPADE:
			return SPADE;
		case DIAMOND_SPADE:
			return SPADE;
		// Hoes
		case WOOD_HOE:
			return HOE;
		case STONE_HOE:
			return HOE;
		case IRON_HOE:
			return HOE;
		case GOLD_HOE:
			return HOE;
		case DIAMOND_HOE:
			return HOE;
		default:
			return null;

		}
	}

}
