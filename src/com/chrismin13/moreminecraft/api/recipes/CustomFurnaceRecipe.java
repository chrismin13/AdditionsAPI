package com.chrismin13.moreminecraft.api.recipes;

import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.items.CustomItem;

public class CustomFurnaceRecipe {

	private RecipeIngredient input;
	private float experience = 0F;

	public CustomFurnaceRecipe(ItemStack itemInput) {
		input = new RecipeIngredient(itemInput);
	}
	
	public CustomFurnaceRecipe(CustomItem cItemInput) {
		input = new RecipeIngredient(cItemInput);
	}
	
	public CustomFurnaceRecipe(CustomFurnaceRecipe recipe) {
		this.input = recipe.getInput();
		this.experience = recipe.getExperience();
	}

	/**
	 * @return the input
	 */
	public RecipeIngredient getInput() {
		return input.clone();
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(RecipeIngredient input) {
		this.input = input;
	}

	/**
	 * @return the experience
	 */
	public float getExperience() {
		return experience;
	}

	/**
	 * @param experience the experience to set
	 */
	public void setExperience(float experience) {
		this.experience = experience;
	}

}
