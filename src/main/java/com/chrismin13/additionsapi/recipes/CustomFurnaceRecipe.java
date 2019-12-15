package com.chrismin13.additionsapi.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;

public class CustomFurnaceRecipe extends CustomRecipe {

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
		this.input = recipe.getInput().clone();
		this.experience = new Float(recipe.getExperience());
	}

	/**
	 * @return the input
	 */
	public RecipeIngredient getInput() {
		return input;
	}

	/**
	 * @param input
	 *            the input to set
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
	 * @param experience
	 *            the experience to set
	 */
	public CustomFurnaceRecipe setExperience(float experience) {
		this.experience = experience;
		return this;
	}

	@Override
	public FurnaceRecipe toBukkitRecipe(ItemStack result) {
		return toBukkitRecipe(null, result);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FurnaceRecipe toBukkitRecipe(NamespacedKey key, ItemStack result) {
		FurnaceRecipe recipe;
		recipe = new FurnaceRecipe(result, getInput().getMaterial(), getInput().getBlockData());

		recipe.setExperience(getExperience());

		return recipe;
	}
}
