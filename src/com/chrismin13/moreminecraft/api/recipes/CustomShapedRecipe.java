package com.chrismin13.moreminecraft.api.recipes;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.utils.NumberUtils;

public class CustomShapedRecipe {

	private String[] shape = new String[3];
	private Map<Character, ItemStack> ingredients = new HashMap<Character, ItemStack>();
	
	public CustomShapedRecipe() {}
	
	public CustomShapedRecipe(CustomShapedRecipe recipe) {
		this.shape = recipe.getShape();
		this.ingredients = recipe.getIngredientMap();
	}

	public void setShape(String firstRow, String secondRow, String thirdRow) {
		shape[0] = firstRow;
		shape[1] = secondRow;
		shape[2] = thirdRow;
	}

	public void setIngredient(char character, Material material) {
		setIngredient(character, material, (short) 0);
	}

	public void setIngredient(char character, Material material, int durability) {
		Validate.isTrue(NumberUtils.isValidShot(durability),
				"The Durability specified: " + Integer.toString(durability) + " is not a valid short!");
		setIngredient(character, material, (short) durability);
	}

	public void setIngredient(char character, Material material, short durability) {
		ingredients.put(character, new ItemStack(material, durability));
	}

	/**
	 * Get a copy of the ingredients map.
	 *
	 * @return The mapping of character to ingredients.
	 */
	public Map<Character, ItemStack> getIngredientMap() {
		HashMap<Character, ItemStack> result = new HashMap<Character, ItemStack>();
		for (Map.Entry<Character, ItemStack> ingredient : ingredients.entrySet()) {
			if (ingredient.getValue() == null) {
				result.put(ingredient.getKey(), null);
			} else {
				result.put(ingredient.getKey(), ingredient.getValue().clone());
			}
		}
		return result;
	}

	/**
	 * Get the shape.
	 *
	 * @return The recipe's shape.
	 */
	public String[] getShape() {
		return shape.clone();
	}
}
