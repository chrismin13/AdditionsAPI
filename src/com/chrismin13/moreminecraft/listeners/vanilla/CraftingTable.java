package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import com.chrismin13.moreminecraft.api.items.CustomItem;
import com.chrismin13.moreminecraft.api.recipes.CustomShapedRecipe;
import com.chrismin13.moreminecraft.api.recipes.RecipeIngredient;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;
import com.chrismin13.moreminecraft.utils.Debug;

public class CraftingTable implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void prepareCrafting(PrepareItemCraftEvent event) {
		CraftingInventory inventory = event.getInventory();
		ItemStack[] matrix = inventory.getMatrix();
		/*
		 * Fix for the Bug that occurred when combining two CustomItems as they
		 * are a lower durability version of an item that already exists in-game
		 */
		if (event.isRepair()) {
			for (ItemStack item : matrix) {
				if (CustomItemUtils.isCustomItem(item)) {
					CustomItem cItem = CustomItemUtils.getCustomItem(item);
					if (!cItem.isCombinableInCrafting()) {
						event.getInventory().setResult(new ItemStack(Material.AIR));
						break;
					}
				}
			}
		}
	}

	public static void checkCustomShapedRecipe(CraftingInventory inventory, List<HumanEntity> list) {
		ItemStack[] matrix = inventory.getMatrix();
		CustomShapedRecipe recipe = new CustomShapedRecipe(matrix);
		RecipeIngredient[] ingredients = recipe.getCraftingMatrix();
		for (CustomItem cItem : CustomItemUtils.getAllCustomItems()) {
			Debug.saySuper("Checking Custom Item: " + cItem.getDisplayName());
			for (CustomShapedRecipe recipeToCheck : cItem.getCustomShapedRecipes()) {
				Debug.saySuper("Checking Recipe: " + recipeToCheck.getShape());
				RecipeIngredient[] ingredientsToCheck = recipeToCheck.getCraftingMatrix();
				boolean isRecipe = true;
				for (int i = 0; i < 9; i++) {
					if (ingredients[i] != null && ingredientsToCheck[i] != null
							&& !ingredients[i].equals(ingredientsToCheck[i]))
						isRecipe = false;
					if ((ingredients[i] == null && ingredientsToCheck[i] != null)
							|| (ingredients[i] != null && ingredientsToCheck[i] == null))
						isRecipe = false;
				}
				if (isRecipe) {
					Debug.saySuper("IS CRAFTING RECIPE!");
					for (HumanEntity e : list)
						((Player) e).updateInventory();
					inventory.setResult(CustomItemUtils.getCustomItemStack(cItem.getCustomItemIdName()).getItemStack());
					return;
				}
			}
		}
	}
}
