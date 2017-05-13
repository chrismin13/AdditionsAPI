package com.chrismin13.additionsapi.listeners.vanilla;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomItemStack;

public class CraftingTable implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void prepareCrafting(PrepareItemCraftEvent event) {
		CraftingInventory inventory = event.getInventory();
		ItemStack[] matrix = inventory.getMatrix();
		if (event.isRepair() && !AdditionsAPI.isCustomItem(inventory.getResult())) {
			/*
			 * Fix for the Bug that occurred when combining two CustomItems as
			 * they are a lower durability version of an item that already
			 * exists in-game
			 */
			for (ItemStack item : matrix) {
				if (AdditionsAPI.isValidCustomItem(item)) {
					CustomItem cItem = new CustomItemStack(item).getCustomItem();
					if (!cItem.isCombinableInCrafting()) {
						event.getInventory().setResult(new ItemStack(Material.AIR));
						break;
					}
				}
			}
			/*
			 * Makes the Fake Durability work like normal durability when
			 * combining items.
			 */
			List<ItemStack> items = new ArrayList<ItemStack>();
			for (ItemStack item : matrix)
				if (item != null && item.getType() != Material.AIR)
					items.add(item);
			if (items.size() == 2) {
				ItemStack[] toCombine = new ItemStack[2];
				int i = 0;
				for (ItemStack item : items) {
					toCombine[i] = item;
					i++;
				}
				if (AdditionsAPI.isCustomItem(toCombine[0]) && AdditionsAPI.isCustomItem(toCombine[1])) {
					CustomItemStack cStack1 = new CustomItemStack(toCombine[0]);
					CustomItemStack cStack2 = new CustomItemStack(toCombine[1]);
					CustomItem cItem1 = cStack1.getCustomItem();
					CustomItem cItem2 = cStack2.getCustomItem();

					if (cItem1.equals(cItem2) && cItem1.hasFakeDurability()) {
						ItemStack newResult = cStack1.getItemStack();
						CustomItemStack newCustomStack = new CustomItemStack(newResult);

						int fakeDur1 = cStack1.getFakeDurability();
						int fakeDur2 = cStack2.getFakeDurability();
						int finalDur = ((fakeDur1 + fakeDur2) * 5 / 100) + fakeDur1 + fakeDur2;

						if (cItem1.getFakeDurability() > finalDur)
							newCustomStack.setFakeDurability(finalDur);
						else
							newCustomStack.setFakeDurability(cItem1.getFakeDurability());

						inventory.setResult(newResult);
					}
				}
			}
		}
	}

	// public static void checkCustomShapedRecipe(CraftingInventory inventory,
	// List<HumanEntity> list) {
	// ItemStack[] matrix = inventory.getMatrix();
	// CustomShapedRecipe recipe = new CustomShapedRecipe(matrix);
	// RecipeIngredient[] ingredients = recipe.getCraftingMatrix();
	// for (CustomItem cItem : AdditionsAPI.getAllCustomItems()) {
	// Debug.saySuper("Checking Custom Item: " + cItem.getDisplayName());
	// for (CustomShapedRecipe recipeToCheck : cItem.getCustomShapedRecipes()) {
	// Debug.saySuper("Checking Recipe: " + recipeToCheck.getShape());
	// RecipeIngredient[] ingredientsToCheck =
	// recipeToCheck.getCraftingMatrix();
	// boolean isRecipe = true;
	// for (int i = 0; i < 9; i++) {
	// if (ingredients[i] != null && ingredientsToCheck[i] != null
	// && !ingredients[i].equals(ingredientsToCheck[i]))
	// isRecipe = false;
	// if ((ingredients[i] == null && ingredientsToCheck[i] != null)
	// || (ingredients[i] != null && ingredientsToCheck[i] == null))
	// isRecipe = false;
	// }
	// if (isRecipe) {
	// Debug.saySuper("IS CRAFTING RECIPE!");
	// for (HumanEntity e : list)
	// ((Player) e).updateInventory();
	// inventory.setResult(AdditionsAPI.getCustomItemStack(cItem.getIdName()).getItemStack());
	// return;
	// }
	// }
	// }
	// }
}
