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
import java.util.Collection;
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
import com.chrismin13.moreminecraft.api.items.CustomItem;
import com.chrismin13.moreminecraft.api.items.CustomItemStack;
import com.chrismin13.moreminecraft.api.items.StorageCustomItem;
import com.chrismin13.moreminecraft.api.recipes.CustomRecipes;
import com.chrismin13.moreminecraft.api.recipes.CustomShapedRecipe;
import com.chrismin13.moreminecraft.events.MoreMinecraftAPIInitializationEvent;
import com.chrismin13.moreminecraft.files.DataFile;
import com.chrismin13.moreminecraft.utils.attributestorage.AttributeStorage;

import us.fihgu.toolbox.item.ModelInjector;

public class CustomItemUtils implements Listener {

	// === VARIABLES === //

	private static HashMap<String, CustomItem> customItems = new HashMap<String, CustomItem>();
	private static HashMap<String, CustomItemStack> customItemStacks = new HashMap<String, CustomItemStack>();

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
			/*
			 * Adding to data.yml and obtaining values
			 */
			DataFile dataFile = DataFile.getInstance();
			String texture = null;
			String idName = cItem.getCustomItemIdName();
			if (cItem instanceof ModelInjector) {
				ModelInjector model = (ModelInjector) cItem;
				Map<String, Short> textures = model.getAllTextures();
				for (String string : textures.keySet()) {
					Debug.saySuper("Currently Processing Texure: " + string);
					short freeDurability;
					if (dataFile.getCustomItem(cItem.getCustomItemIdName(), string) == null) {
						freeDurability = dataFile.getFreeDurability(cItem.getMaterial());
						Debug.saySuper("Set durability to: " + freeDurability);
					} else {
						freeDurability = dataFile.getCustomItem(cItem.getCustomItemIdName(), string).getDurability();
						Debug.saySuper("Set durability to: " + freeDurability);
					}
					textures.put(string, freeDurability);
					if (model.getDefaultTexture().equals(string)) {
						Debug.saySuper("Is default texture");
						cItem.setDurability(freeDurability);
						texture = string;
					}
					dataFile.addStorageCustomItem(new StorageCustomItem(cItem.getMaterial(), freeDurability, idName, string));
				}
				Debug.saySuper("Final map:" + textures);
			}
			short durability = cItem.getDurability();
			/*
			 * CustomItems and ItemStacks
			 */
			CustomItemStack cStack = new CustomItemStack(cItem, durability, texture);
			ItemStack item = cStack.getItemStack();
			Debug.saySuper("cStack Display Name: " + cStack.getCustomItem().getCustomItemIdName());
			Debug.saySuper("cStack Durability: " + item.getDurability());
			customItems.put(idName, cItem);
			customItemStacks.put(idName, cStack);
			/*
			 * Crafting Recipes
			 */
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
		DataFile.getInstance().saveData();
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
		// TODO: Clone
		return customItems.get(getIdName(item));
	}

	public static CustomItem getCustomItem(String idName) {
		return customItems.get(idName);
	}

	public static CustomItemStack getCustomItemStack(ItemStack item) {
		return customItemStacks.get(getIdName(item));
	}

	public static CustomItemStack getCustomItemStack(String idName) {
		return customItemStacks.get(idName);
	}

	public static Collection<CustomItem> getAllCustomItems() {
		return customItems.values();
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
