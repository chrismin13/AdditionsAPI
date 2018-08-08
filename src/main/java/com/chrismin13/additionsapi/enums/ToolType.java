package com.chrismin13.additionsapi.enums;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.chrismin13.additionsapi.durability.AxeDurability;
import com.chrismin13.additionsapi.durability.HoeDurability;
import com.chrismin13.additionsapi.durability.ItemDurability;
import com.chrismin13.additionsapi.durability.PickaxeDurability;
import com.chrismin13.additionsapi.durability.SpadeDurability;
import com.chrismin13.additionsapi.durability.SwordDurability;
import com.chrismin13.additionsapi.permissions.HoePermissions;
import com.chrismin13.additionsapi.permissions.ItemPermissions;
import com.chrismin13.additionsapi.permissions.PermissionType;
import com.chrismin13.additionsapi.permissions.SpadePermissions;
import com.chrismin13.additionsapi.recipes.CustomShapedRecipe;
import com.chrismin13.additionsapi.recipes.RecipeIngredient;
import com.chrismin13.additionsapi.utils.Debug;
import com.chrismin13.additionsapi.utils.LangFileUtils;

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

	public ItemPermissions getItemPermissions(String permissionPrefix, PermissionType type) {
		switch (this) {
		case HOE:
			return new HoePermissions(permissionPrefix, type);
		case SPADE:
			return new SpadePermissions(permissionPrefix, type);
		case AXE:
		case PICKAXE:
		case SWORD:
		default:
			return new ItemPermissions(permissionPrefix, type);
		}
	}

	public List<CustomShapedRecipe> getCustomShapedRecipe(Material itemMaterial, Material stickMaterial) {
		return getCustomShapedRecipe(new RecipeIngredient(itemMaterial), new RecipeIngredient(stickMaterial));
	}

	public List<CustomShapedRecipe> getCustomShapedRecipe(RecipeIngredient itemIngredient,
			RecipeIngredient stickIngredient) {
		List<CustomShapedRecipe> recipes = new ArrayList<CustomShapedRecipe>();

		CustomShapedRecipe itemRecipe = new CustomShapedRecipe();

		itemRecipe.setIngredient('1', itemIngredient);
		itemRecipe.setIngredient('2', stickIngredient);

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

	@SuppressWarnings("deprecation")
	public static ToolType getToolType(Material material) {
		switch (material) {
		// Swords
		case WOODEN_SWORD:
			return SWORD;
		case GOLDEN_SWORD:
			return SWORD;
		case STONE_SWORD:
			return SWORD;
		case IRON_SWORD:
			return SWORD;
		case DIAMOND_SWORD:
			return SWORD;
		// Axe
		case WOODEN_AXE:
			return AXE;
		case GOLDEN_AXE:
			return AXE;
		case STONE_AXE:
			return AXE;
		case IRON_AXE:
			return AXE;
		case DIAMOND_AXE:
			return AXE;
		// Pickaxe
		case WOODEN_PICKAXE:
			return PICKAXE;
		case STONE_PICKAXE:
			return PICKAXE;
		case IRON_PICKAXE:
			return PICKAXE;
		case GOLDEN_PICKAXE:
			return PICKAXE;
		case DIAMOND_PICKAXE:
			return PICKAXE;
		// Spades
		case WOODEN_SHOVEL:
			return SPADE;
		case STONE_SHOVEL:
			return SPADE;
		case IRON_SHOVEL:
			return SPADE;
		case GOLDEN_SHOVEL:
			return SPADE;
		case DIAMOND_SHOVEL:
			return SPADE;
		// Hoes
		case WOODEN_HOE:
			return HOE;
		case STONE_HOE:
			return HOE;
		case IRON_HOE:
			return HOE;
		case GOLDEN_HOE:
			return HOE;
		case DIAMOND_HOE:
			return HOE;

		// Legacy
		// Swords
		case LEGACY_WOOD_SWORD:
			return SWORD;
		case LEGACY_GOLD_SWORD:
			return SWORD;
		case LEGACY_STONE_SWORD:
			return SWORD;
		case LEGACY_IRON_SWORD:
			return SWORD;
		case LEGACY_DIAMOND_SWORD:
			return SWORD;
		// Axe
		case LEGACY_WOOD_AXE:
			return AXE;
		case LEGACY_GOLD_AXE:
			return AXE;
		case LEGACY_STONE_AXE:
			return AXE;
		case LEGACY_IRON_AXE:
			return AXE;
		case LEGACY_DIAMOND_AXE:
			return AXE;
		// Pickaxe
		case LEGACY_WOOD_PICKAXE:
			return PICKAXE;
		case LEGACY_STONE_PICKAXE:
			return PICKAXE;
		case LEGACY_IRON_PICKAXE:
			return PICKAXE;
		case LEGACY_GOLD_PICKAXE:
			return PICKAXE;
		case LEGACY_DIAMOND_PICKAXE:
			return PICKAXE;
		// Spades
		case LEGACY_WOOD_SPADE:
			return SPADE;
		case LEGACY_STONE_SPADE:
			return SPADE;
		case LEGACY_IRON_SPADE:
			return SPADE;
		case LEGACY_GOLD_SPADE:
			return SPADE;
		case LEGACY_DIAMOND_SPADE:
			return SPADE;
		// Hoes
		case LEGACY_WOOD_HOE:
			return HOE;
		case LEGACY_STONE_HOE:
			return HOE;
		case LEGACY_IRON_HOE:
			return HOE;
		case LEGACY_GOLD_HOE:
			return HOE;
		case LEGACY_DIAMOND_HOE:
			return HOE;
		default:
			return null;

		}
	}

	public String toIngameString() {
		return LangFileUtils.get(this.toString().toLowerCase());
	}

}
