package com.chrismin13.moreminecraft.api.recipes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.items.CustomItem;

public class CustomShapelessRecipe {

	private List<RecipeIngredient> ingredients = new ArrayList<RecipeIngredient>();

	public CustomShapelessRecipe() {}
	
	public CustomShapelessRecipe(CustomShapelessRecipe recipe) {
		for (RecipeIngredient ingredient : recipe.getIngredients())
			this.addIngredient(ingredient);
	}

	public CustomShapelessRecipe addIngredient(ItemStack item) {
		return addIngredient(new RecipeIngredient(item));
	}
	
	public CustomShapelessRecipe addIngredient(CustomItem cItem) {
		return addIngredient(new RecipeIngredient(cItem));
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
