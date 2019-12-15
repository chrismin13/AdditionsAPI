package com.chrismin13.additionsapi.recipes;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public abstract class CustomRecipe {

	@Deprecated
	public abstract Recipe toBukkitRecipe(ItemStack result);
	
	public abstract Recipe toBukkitRecipe(NamespacedKey key, ItemStack result);

	@Deprecated
	public CustomRecipe registerBukkitRecipe(ItemStack result) {
		Recipe recipe = toBukkitRecipe(result);
		Bukkit.addRecipe(recipe);
		return this;
	}
	
	public CustomRecipe registerBukkitRecipe(NamespacedKey key, ItemStack result) {
		Recipe recipe = toBukkitRecipe(key, result);
		Bukkit.addRecipe(recipe);
		return this;
	}
	
}
