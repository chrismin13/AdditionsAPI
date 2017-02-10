package com.chrismin13.moreminecraft.api.recipes;

import java.util.ArrayList;
import java.util.List;

public class CustomRecipes {

	private List<CustomShapedRecipe> shapedRecipes = new ArrayList<CustomShapedRecipe>();

	// === SHAPED RECIPES === //

	public void addShapedRecipe(CustomShapedRecipe recipe) {
		shapedRecipes.add(recipe);
	}

	public void addAllShapedRecipes(List<CustomShapedRecipe> recipes) {
		for(CustomShapedRecipe recipe : recipes) {
			addShapedRecipe(recipe);
		}
	}

	public CustomShapedRecipe[] getShapedRecipes() {
		CustomShapedRecipe[] recipes = new CustomShapedRecipe[shapedRecipes.size()];
		int index = 0;
		for (CustomShapedRecipe recipe : shapedRecipes) {
			recipes[index] = recipe;
			index++;
		}
		return recipes;
	}
}