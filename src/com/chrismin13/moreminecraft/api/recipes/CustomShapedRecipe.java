package com.chrismin13.moreminecraft.api.recipes;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.items.CustomItem;
import com.chrismin13.moreminecraft.utils.Debug;

public class CustomShapedRecipe {

	private HashMap<Character, RecipeIngredient> ingredients = new HashMap<Character, RecipeIngredient>();
	private String[] shape = new String[3];

	public CustomShapedRecipe() {
	}

	public CustomShapedRecipe(CustomShapedRecipe recipe) {
		this.shape = recipe.getShape();
		this.ingredients = recipe.getIngredients();
	}

	public CustomShapedRecipe(ItemStack[] recipeMatrix) {
		int character = 1;
		setShape("123", "456", "789");
		for (ItemStack item : recipeMatrix) {
			if (item != null && item.getType() != Material.AIR)
				this.setIngredient(Integer.toString(character).toCharArray()[0], item);
			character++;
		}
	}

	public CustomShapedRecipe setShape(String firstRow, String secondRow, String thirdRow) {
		shape[0] = firstRow;
		shape[1] = secondRow;
		shape[2] = thirdRow;
		return this;
	}

	public String[] getShape() {
		return shape.clone();
	}

	public CustomShapedRecipe setIngredient(char character, Material craftingMaterial) {
		return setIngredient(character, new ItemStack(craftingMaterial));
	}

	public CustomShapedRecipe setIngredient(char character, ItemStack item) {
		return setIngredient(character, new RecipeIngredient(item));
	}

	public CustomShapedRecipe setIngredient(char character, CustomItem cItem) {
		return setIngredient(character, new RecipeIngredient(cItem));
	}

	public CustomShapedRecipe setIngredient(char character, RecipeIngredient ingredient) {
		ingredients.put(character, ingredient);
		return this;
	}

	public HashMap<Character, RecipeIngredient> getIngredients() {
		return ingredients;
	}

	public RecipeIngredient[] getCraftingMatrix() {
		RecipeIngredient[] ingredients = new RecipeIngredient[9];
		for (String string : shape)
			Debug.sayTrue("Shapes: " + string);
		for (char character : this.ingredients.keySet()) {
			int currentSpot = 0;
			for (String string : shape) {
				for (int charToCheck = 0; charToCheck < 3; charToCheck++) {
					if (string.charAt(charToCheck) == character) {
						ingredients[currentSpot] = this.ingredients.get(character);
					}
					currentSpot++;
				}
			}
		}
		return ingredients;
	}
}