package com.chrismin13.moreminecraft.api.recipes;

import org.bukkit.Material;

public class CustomFurnaceRecipe {

	private RecipeIngredient input;
	private float experience = 0F;

	public CustomFurnaceRecipe(Material input) {
		setInput(new RecipeIngredient(input));
	}
	
	public CustomFurnaceRecipe(Material input, float experience) {
		setInput(new RecipeIngredient(input));
		setExperience(experience);
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
	public CustomFurnaceRecipe setInput(RecipeIngredient input) {
		this.input = input;
		return this;
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
	public CustomFurnaceRecipe setExperience(float experience) {
		this.experience = experience;
		return this;
	}

}
