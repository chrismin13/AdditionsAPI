package com.chrismin13.moreminecraft.utils;

import com.chrismin13.moreminecraft.api.recipes.CustomShapedRecipe;

public class RecipeUtils {

	public static CustomShapedRecipe newWithShape(CustomShapedRecipe recipe, String firstRow, String secondRow, String thirdRow) {
		CustomShapedRecipe newRecipe = new CustomShapedRecipe(recipe);
		newRecipe.setShape(firstRow, secondRow, thirdRow);
		return newRecipe;
	}
	
}
