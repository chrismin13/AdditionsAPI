package com.chrismin13.moreminecraft.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import com.chrismin13.moreminecraft.items.CustomItem;
import com.chrismin13.moreminecraft.items.CustomItemStack;
import com.chrismin13.moreminecraft.items.StorageCustomItem;
import com.chrismin13.moreminecraft.recipes.CustomFurnaceRecipe;
import com.chrismin13.moreminecraft.recipes.CustomShapedRecipe;
import com.chrismin13.moreminecraft.recipes.CustomShapelessRecipe;
import com.chrismin13.moreminecraft.recipes.RecipeIngredient;
import com.comphenix.attribute.NbtFactory;
import com.comphenix.attribute.NbtFactory.NbtCompound;
import com.chrismin13.moreminecraft.events.MoreMinecraftAPIInitializationEvent;
import com.chrismin13.moreminecraft.files.DataFile;

import us.fihgu.toolbox.item.ModelInjector;

public class CustomItemUtils implements Listener {

	// === VARIABLES === //
	// private static HashMap<String, CustomItem> customItems = new
	// HashMap<String, CustomItem>();
	private static List<CustomItemStack> customItemStacks = new ArrayList<CustomItemStack>();

	// === INITIALIZATION === //

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInitialization(MoreMinecraftAPIInitializationEvent event) {
		CustomItem[] cItems = event.getCustomItems();
		Debug.say("Recieved in total " + Integer.toString(cItems.length) + " Custom Items!");
		for (CustomItem cItem : cItems) {
			Debug.saySuper("Currently Processing: " + cItem.getIdName());
			/*
			 * Adding to data.yml and obtaining values
			 */
			DataFile dataFile = DataFile.getInstance();
			String texture = null;
			String idName = cItem.getIdName();
			if (cItem instanceof ModelInjector) {
				ModelInjector model = (ModelInjector) cItem;
				Map<String, Short> textures = model.getAllTextures();
				for (String string : textures.keySet()) {
					Debug.saySuper("Currently Processing Texure: " + string);
					short freeDurability;
					if (dataFile.getCustomItem(cItem.getIdName(), string) == null) {
						freeDurability = dataFile.getFreeDurability(cItem.getMaterial());
						Debug.saySuper("Set durability of Texture '" + string + "' to: " + freeDurability);
					} else {
						freeDurability = dataFile.getCustomItem(cItem.getIdName(), string).getDurability();
						Debug.saySuper("Set durability of Texture '" + string + "' to: " + freeDurability);
					}
					textures.put(string, freeDurability);
					if (model.getDefaultTexture().equals(string)) {
						Debug.saySuper("Texture " + string + " is default texture.");
						cItem.setDurability(freeDurability);
						texture = string;
					}
					dataFile.addStorageCustomItem(
							new StorageCustomItem(cItem.getMaterial(), freeDurability, idName, string));
				}
			}
			short durability = cItem.getDurability();
			/*
			 * CustomItems and ItemStacks
			 */
			CustomItemStack cStack = new CustomItemStack(cItem, durability, texture);
			ItemStack item = cStack.getItemStack();
			// customItems.put(idName, cItem);
			customItemStacks.add(cStack);
			/*
			 * Shaped Recipes
			 */
			for (CustomShapedRecipe cRecipe : cItem.getCustomShapedRecipes()) {
				ShapedRecipe recipe = new ShapedRecipe(item);
				Debug.saySuper("Adding shaped recipe");

				recipe.shape(cRecipe.getShape());
				Debug.saySuper("Added shape:");
				for (String s : cRecipe.getShape())
					Debug.saySuper(s);
				HashMap<Character, RecipeIngredient> map = cRecipe.getIngredients();
				for (char key : map.keySet()) {
					Debug.saySuper("Processing Character: " + key);
					Debug.saySuper("Material: " + map.get(key).getMaterial());
					recipe.setIngredient(key, map.get(key).getMaterial());
				}

				Bukkit.addRecipe(recipe);
			}
			/*
			 * Shapeless Recipes
			 */
			for (CustomShapelessRecipe cRecipe : cItem.getCustomShapelessRecipes()) {
				ShapelessRecipe recipe = new ShapelessRecipe(item);

				Debug.saySuper("Adding shapeless recipe");

				for (RecipeIngredient ingredient : cRecipe.getIngredients()) {
					if (ingredient != null) {
						recipe.addIngredient(ingredient.getMaterial());
						Debug.saySuper("Added Material: " + ingredient.getMaterial());
					}
				}

				Bukkit.addRecipe(recipe);
			}
			/*
			 * Furnace Recipes
			 */
			for (CustomFurnaceRecipe cRecipe : cItem.getCustomFurnaceRecipes()) {
				FurnaceRecipe recipe = new FurnaceRecipe(item, cRecipe.getInput().getMaterial());

				recipe.setExperience(cRecipe.getExperience());

				Bukkit.addRecipe(recipe);
			}
		}
		DataFile.getInstance().saveData();
	}

	// === STORAGE === //

	public static boolean isValidCustomItem(String idName) {
		try {
			for (CustomItemStack cStack : customItemStacks)
				if (cStack.getCustomItem().getIdName().equals(idName))
					return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public static CustomItemStack[] getAllCustomItemStacks() {
		CustomItemStack[] cStacks = new CustomItemStack[customItemStacks.size()];
		int i = 0;
		for (CustomItemStack cStack : customItemStacks){
			cStacks[i] = cStack.clone();
			i++;
		}
		return cStacks;
	}
	
	public static CustomItem[] getAllCustomItems() {
		CustomItem[] cItems = new CustomItem[customItemStacks.size()];
		int i = 0;
		for (CustomItemStack cStack : customItemStacks){
			cItems[i] = cStack.getCustomItem();
			i++;
		}
		return cItems;
	}

	// === ITEMSTACKS === //

	public static String getIdName(ItemStack item) {
		if (item.getType() == Material.AIR)
			return null;
		ItemStack stack = NbtFactory.getCraftItemStack(item);
		NbtCompound nbt = NbtFactory.fromItemTag(stack);
		return nbt.getString("CustomItemIdName", null);
	}

	public static boolean isCustomItem(ItemStack item) {
		if (getIdName(item) != null)
			return true;
		return false;
	}
}
