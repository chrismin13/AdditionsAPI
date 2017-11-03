package com.chrismin13.additionsapi.recipes;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public abstract class CustomRecipe {

	public abstract Recipe toBukkitRecipe(ItemStack result);

	public CustomRecipe registerBukkitRecipe(ItemStack result) {
		Recipe recipe = toBukkitRecipe(result);
		Bukkit.addRecipe(recipe);
		return this;
	}
}
