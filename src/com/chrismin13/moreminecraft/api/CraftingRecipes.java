package com.chrismin13.moreminecraft.api;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import com.chrismin13.moreminecraft.enums.ArmorType;
import com.chrismin13.moreminecraft.enums.ToolType;

public class CraftingRecipes {

	// === TOOLS === //

	@SuppressWarnings("deprecation")
	public static void addTool(ToolType type, ItemStack item, Material itemMaterial, int durabilityItemMaterial,
			Material stickMaterial, int durabilityStickMaterial) {
		ShapedRecipe itemRecipe = new ShapedRecipe(item);

		// Required to prevent an error with the two lines of code below
		itemRecipe.shape("100", "100", "200");

		itemRecipe.setIngredient('1', itemMaterial, durabilityItemMaterial);
		itemRecipe.setIngredient('2', stickMaterial, durabilityStickMaterial);

		switch (type) {
		case SWORD:
			itemRecipe.shape("100", "100", "200");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("010", "010", "020");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("001", "001", "002");
			Bukkit.getServer().addRecipe(itemRecipe);
			break;
		case AXE:
			itemRecipe.shape("110", "210", "200");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("110", "120", "020");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("011", "021", "020");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("011", "012", "002");
			Bukkit.getServer().addRecipe(itemRecipe);
			break;
		case PICKAXE:
			itemRecipe.shape("111", "020", "020");
			Bukkit.getServer().addRecipe(itemRecipe);
			break;
		case SPADE:
			itemRecipe.shape("100", "200", "200");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("010", "020", "020");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("001", "002", "002");
			Bukkit.getServer().addRecipe(itemRecipe);
			break;
		case HOE:
			itemRecipe.shape("110", "200", "200");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("110", "020", "020");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("011", "020", "020");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("011", "002", "002");
			Bukkit.getServer().addRecipe(itemRecipe);
			break;
		default:
			Bukkit.getServer().getLogger().log(Level.WARNING, "Invalid Item: " + type.toString());
			break;
		}
	}

	public static void addTool(ToolType type, ItemStack item, Material itemMaterial, Material stickMaterial) {
		addTool(type, item, itemMaterial, 0, stickMaterial, 0);
	}

	public static void addTool(ToolType type, CustomItem cItem, Material itemMaterial, Material stickMaterial) {
		addTool(type, cItem.getItemStack(), itemMaterial, stickMaterial);
	}

	public static void addTool(ToolType type, ItemStack item, Material itemMaterial) {
		addTool(type, item, itemMaterial, Material.STICK);
	}

	public static void addTool(ToolType type, CustomItem cItem, Material itemMaterial) {
		addTool(type, cItem.getItemStack(), itemMaterial, Material.STICK);
	}
	
	public static void addTool(ToolType type, CustomItem cItem, Material itemMaterial, int itemDurability) {
		addTool(type, cItem.getItemStack(), itemMaterial, itemDurability, Material.STICK, 0);
	}

	// === ARMOR === //

	@SuppressWarnings("deprecation")
	public static void addArmor(ArmorType type, ItemStack item, Material material, int materialDurability) {
		ShapedRecipe itemRecipe = new ShapedRecipe(item);

		// Required to prevent an error with the two lines of code below
		itemRecipe.shape("111", "101", "000");

		itemRecipe.setIngredient('1', material, materialDurability);

		switch (type) {
		case HELMET:
			itemRecipe.shape("111", "101", "000");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("000", "111", "101");
			Bukkit.getServer().addRecipe(itemRecipe);
			break;
		case CHESTPLATE:
			itemRecipe.shape("101", "111", "111");
			Bukkit.getServer().addRecipe(itemRecipe);
			break;
		case LEGGINGS:
			itemRecipe.shape("111", "101", "101");
			Bukkit.getServer().addRecipe(itemRecipe);
			break;
		case BOOTS:
			itemRecipe.shape("000", "101", "101");
			Bukkit.getServer().addRecipe(itemRecipe);
			itemRecipe.shape("101", "101", "000");
			Bukkit.getServer().addRecipe(itemRecipe);
			break;
		default:
			Bukkit.getServer().getLogger().log(Level.WARNING, "Invalid Armor: " + type.toString());
			break;
		}
	}

	public static void addArmor(ArmorType type, ItemStack item, Material material) {
		addArmor(type, item, material, 0);
	}

	public static void addArmor(ArmorType type, CustomArmor cArmor, Material material) {
		addArmor(type, cArmor.getItemStack(), material);
	}

	public static void addArmor(CustomArmor cArmor, Material material) {
		addArmor(cArmor.getArmorType(), cArmor.getItemStack(), material);
	}

	public static void addArmor(CustomArmor cArmor, Material material, int durability) {
		addArmor(cArmor.getArmorType(), cArmor.getItemStack(), material, durability);
	}
	
}
