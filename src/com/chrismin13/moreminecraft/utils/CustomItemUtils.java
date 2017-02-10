package com.chrismin13.moreminecraft.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.chrismin13.moreminecraft.MoreMinecraft;
import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.api.recipes.CustomRecipes;
import com.chrismin13.moreminecraft.api.recipes.CustomShapedRecipe;
import com.chrismin13.moreminecraft.events.MoreMinecraftAPIInitializationEvent;
import com.chrismin13.moreminecraft.utils.attributestorage.AttributeStorage;

public class CustomItemUtils implements Listener {

	// === VARIABLES === //

	private static HashMap<String, CustomItem> customItems = new HashMap<String, CustomItem>();
	private static HashMap<String, ItemStack> customItemStacks = new HashMap<String, ItemStack>();

	private static File dataFolder = MoreMinecraft.getInstance().getDataFolder();
	private static String path = dataFolder.getPath().replace("\\", "/");

	// === INITIALIZATION === //

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onInitialization(MoreMinecraftAPIInitializationEvent event) {
		CustomItem[] cItems = event.getCustomItems();
		Debug.say("Recieved in total " + Integer.toString(cItems.length) + " Custom Items!");
		for (CustomItem cItem : cItems) {
			Debug.saySuper("Currently Processing: " + cItem.getCustomItemIdName());
			// CustomItems and ItemStacks
			ItemStack item = cItem.getItemStack();
			String idName = cItem.getCustomItemIdName();
			customItems.put(idName, cItem);
			customItemStacks.put(idName, item);
			// Crafting Recipes
			CustomRecipes recipes = cItem.getCustomRecipes();
			CustomShapedRecipe[] shapedRecipes = recipes.getShapedRecipes();
			for (CustomShapedRecipe shapedRecipe : shapedRecipes) {
				Debug.saySuper("Adding Recipe.");
				ShapedRecipe recipe = new ShapedRecipe(item);

				recipe.shape(shapedRecipe.getShape());
				Map<Character, ItemStack> ingredients = shapedRecipe.getIngredientMap();
				for (char character : ingredients.keySet()) {
					ItemStack ingredient = ingredients.get(character);
					recipe.setIngredient(character, ingredient.getType(), ingredient.getDurability());
				}

				Bukkit.addRecipe(recipe);
			}
		}
	}
/*
 * MUST REMOVE
 */
	public static void main() throws IOException {
		Map<String, String> env = new HashMap<>();
		env.put("create", "true");
		// locate file system by using the syntax
		// defined in java.net.JarURLConnection
		URI uri = URI.create(
				"jar:file:/" + dataFolder.getAbsolutePath().replace("\\", "/").replace(" ", "%20") + "/zipfstest.zip");

		try (FileSystem zipfs = FileSystems.newFileSystem(uri, env)) {
			Path externalTxtFile = Paths.get(path + "/SomeTextFile.txt");
			Path pathInZipfile = zipfs.getPath("/SomeTextFile.txt");
			// copy a file into the zip file
			Files.copy(externalTxtFile, pathInZipfile, StandardCopyOption.REPLACE_EXISTING);
		}
	}

	// === STORAGE === //

	public static boolean isValidCustomItem(String customItemIdName) {
		try {
			if (customItems.get(customItemIdName) != null && customItemStacks.get(customItemIdName) != null)
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static CustomItem getCustomItem(ItemStack item) {
		return customItems.get(getIdName(item));
	}

	public static CustomItem getCustomItem(String idName) {
		return customItems.get(idName);
	}

	public static ItemStack getCustomItemStack(ItemStack item) {
		return customItemStacks.get(getIdName(item));
	}

	public static ItemStack getCustomItemStack(String idName) {
		return customItemStacks.get(idName);
	}

	// === ITEMSTACKS === //

	private static UUID attributeStorageUUID = UUID.fromString("8c16d72b-d950-410c-b7d1-eeed86e734c7");

	public static String getIdName(ItemStack item) {
		if (item.getType() != Material.AIR)
			return AttributeStorage.newTarget(item, attributeStorageUUID).getData(null);
		return null;
	}

	public static boolean isCustomItem(ItemStack item) {
		if (item.getType() != Material.AIR && getIdName(item) != null)
			return true;
		return false;
	}

	public static int getCurrentFakeDurability(ItemStack item) {
		return getCurrentFakeDurability(item.getItemMeta().getLore());
	}

	public static int getCurrentFakeDurability(List<String> lore) {
		for (String string : lore) {
			if (string.startsWith(ChatColor.GRAY + "Durability: ")) {
				// TODO: Add language file
				String durability = string.replaceFirst(ChatColor.GRAY + "Durability: ", "");
				String segments[] = durability.split(" / ");
				return Integer.parseInt(segments[0]);
			}
		}
		return 0;
	}
}
