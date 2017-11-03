package com.chrismin13.additionsapi.recipes;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.chrismin13.additionsapi.utils.Debug;

public class CustomShapedRecipe extends CustomRecipe implements Cloneable {

	private HashMap<Character, RecipeIngredient> ingredients = new HashMap<Character, RecipeIngredient>();
	private String[] shape;

	public CustomShapedRecipe() {
	}

	@SuppressWarnings("unchecked")
	public CustomShapedRecipe(CustomShapedRecipe recipe) {
		if (recipe.getShape() != null)
		this.shape = recipe.getShape().clone();
		if (recipe.getIngredients() != null)
		this.ingredients = (HashMap<Character, RecipeIngredient>) recipe.getIngredients().clone();
	}

	public CustomShapedRecipe setShape(String... shape) {
		this.shape = shape;
		return this;
	}

	public CustomShapedRecipe setShape(String firstRow, String secondRow, String thirdRow) {
		this.shape = new String[3];
		shape[0] = firstRow;
		shape[1] = secondRow;
		shape[2] = thirdRow;
		return this;
	}
	
	public String[] getShape() {
		return shape;
	}

	public CustomShapedRecipe setIngredient(char character, Material craftingMaterial) {
		return setIngredient(character, new RecipeIngredient(craftingMaterial));
	}

	// public CustomShapedRecipe setIngredient(char character, ItemStack item) {
	// return setIngredient(character, new RecipeIngredient(item));
	// }

	// public CustomShapedRecipe setIngredient(char character, CustomItem cItem)
	// {
	// return setIngredient(character, new RecipeIngredient(cItem));
	// }

	public CustomShapedRecipe setIngredient(char character, RecipeIngredient ingredient) {
		ingredients.put(character, ingredient);
		return this;
	}

	public HashMap<Character, RecipeIngredient> getIngredients() {
		return ingredients;
	}

	public RecipeIngredient[] getCraftingMatrix() {
		if (shape == null)
			new NullPointerException("The shape of a crafting recipe cannot be null!").printStackTrace();

		RecipeIngredient[] ingredients = new RecipeIngredient[9];
		for (char character : this.ingredients.keySet()) {
			int currentSpot = 0;
			for (String string : shape) {
				for (int charToCheck = 0; charToCheck < string.length(); charToCheck++) {
					if (string.charAt(charToCheck) == character) {
						ingredients[currentSpot] = this.ingredients.get(character);
					}
					currentSpot++;
				}
			}
		}
		return ingredients;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ShapedRecipe toBukkitRecipe(ItemStack result) {
		if (shape == null)
			new NullPointerException("The shape of a crafting recipe cannot be null!").printStackTrace();

		ShapedRecipe recipe = new ShapedRecipe(result);

		recipe.shape(getShape());

		HashMap<Character, RecipeIngredient> map = getIngredients();
		for (char key : map.keySet()) {
			recipe.setIngredient(key, map.get(key).getMaterial(), map.get(key).getBlockData());
		}

		return recipe;
	}

	@Override
	public CustomShapedRecipe clone() {
		try {
			return (CustomShapedRecipe) super.clone();
		} catch (CloneNotSupportedException e) {
			Debug.sayTrueError("Cloning not supported!");
			e.printStackTrace();
		}
		return null;
	}
}