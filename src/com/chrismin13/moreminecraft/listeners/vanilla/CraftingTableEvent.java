package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import com.chrismin13.moreminecraft.api.items.CustomItem;
import com.chrismin13.moreminecraft.api.recipes.CustomShapedRecipe;
import com.chrismin13.moreminecraft.api.recipes.RecipeIngredient;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;
import com.chrismin13.moreminecraft.utils.Debug;

public class CraftingTableEvent implements Listener {

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

	@EventHandler(priority = EventPriority.MONITOR)
	public void onItemClick(InventoryClickEvent event) {
		Debug.saySuper("Evented");
		if (event.isCancelled())
			return;
		if (event.getClickedInventory() instanceof CraftingInventory) {
			CraftingInventory inventory = (CraftingInventory) event.getInventory();
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
						inventory.setResult(
								CustomItemUtils.getCustomItemStack(cItem.getCustomItemIdName()).getItemStack());
						PrepareItemCraftEvent customEvent = new PrepareItemCraftEvent(inventory, event.getView(),
								false);
						Bukkit.getPluginManager().callEvent(customEvent);
						return;
					}
				}
			}
		}
	}

}
