package com.chrismin13.moreminecraft.api.recipes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

public class CustomShapelessRecipe {

	private List<RecipeIngredient> ingredients = new ArrayList<RecipeIngredient>();

	public CustomShapelessRecipe() {}
	
	public CustomShapelessRecipe(CustomShapelessRecipe recipe) {
		for (RecipeIngredient ingredient : recipe.getIngredients())
			this.addIngredient(ingredient);
	}
	
	public CustomShapelessRecipe addIngredient(Material material) {
		return addIngredient(new RecipeIngredient(material));
	}
	
	public CustomShapelessRecipe addIngredient(RecipeIngredient ingredient) {
		ingredients.add(ingredient); 
		return this;
	}

	public RecipeIngredient[] getIngredients() {
		RecipeIngredient[] ingredients = new RecipeIngredient[9];
		int index = 0;
		for (RecipeIngredient ingredient : this.ingredients) {
			ingredients[index] = ingredient;
			index++;
		}
		return ingredients;
	}

}
