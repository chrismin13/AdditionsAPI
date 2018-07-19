package com.chrismin13.additionsapi.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialUtils {

	private static List<Material> instantlyBreakable = Arrays.asList(Material.LILY_PAD, Material.CARROT,
			Material.DEAD_BUSH, Material.FIRE, Material.FLOWER_POT, Material.TALL_GRASS, Material.MELON_STEM,
			Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.NETHER_WART_BLOCK, Material.POTATO,
			Material.PUMPKIN_STEM, Material.COMPARATOR, Material.REPEATER, Material.REDSTONE_TORCH,
			Material.REDSTONE_WIRE, Material.OAK_SAPLING, Material.ACACIA_SAPLING, Material.OAK_SAPLING,
			Material.DARK_OAK_SAPLING, Material.JUNGLE_SAPLING, Material.SPRUCE_SAPLING, Material.BIRCH_SAPLING,
			Material.SLIME_BLOCK, Material.SUGAR_CANE, Material.TNT, Material.TORCH, Material.TRIPWIRE,
			Material.TRIPWIRE_HOOK, Material.BEETROOT_SEEDS, Material.MELON_SEEDS, Material.PUMPKIN_SEEDS,
			Material.WHEAT_SEEDS, Material.WHEAT, Material.BEETROOT, Material.END_ROD);

	public static boolean isInstantlyBreakable(Material material) {
		if (instantlyBreakable.contains(material) || isFlower(material))
			return true;
		return false;
	}

	@SuppressWarnings("deprecation")
	public static boolean isFlower(Material material) {
		int id = material.getId();
		if (id == 37 || id == 38)
			return true;
		return false;
	}

	/**
	 * Retrieve the base damage of the given item.
	 * 
	 * @param stack
	 *            - the stack.
	 * @return The base damage.
	 */
	public static float getBaseDamage(ItemStack stack) {
		return getBaseDamage(stack.getType());
	}

	/**
	 * Retrieve the base damage of the given item.
	 * 
	 * @param material
	 *            - the material that you want the base damage for.
	 * @return The base damage.
	 */
	@SuppressWarnings("deprecation")
	public static float getBaseDamage(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Swords
		case LEGACY_WOOD_SWORD:
		case WOODEN_SWORD:
			return 4F;
		case LEGACY_GOLD_SWORD:
		case GOLDEN_SWORD:
			return 4F;
		case LEGACY_STONE_SWORD:
		case STONE_SWORD:
			return 5F;
		case LEGACY_IRON_SWORD:
		case IRON_SWORD:
			return 6F;
		case LEGACY_DIAMOND_SWORD:
		case DIAMOND_SWORD:
			return 7F;
		// Axe
		case LEGACY_WOOD_AXE:
		case WOODEN_AXE:
			return 7F;
		case LEGACY_GOLD_AXE:
		case GOLDEN_AXE:
			return 7F;
		case LEGACY_STONE_AXE:
		case STONE_AXE:
			return 9F;
		case LEGACY_IRON_AXE:
		case IRON_AXE:
			return 9F;
		case LEGACY_DIAMOND_AXE:
		case DIAMOND_AXE:
			return 9F;
		// Pickaxe
		case LEGACY_WOOD_PICKAXE:
		case WOODEN_PICKAXE:
			return 2F;
		case LEGACY_STONE_PICKAXE:
		case STONE_PICKAXE:
			return 3F;
		case LEGACY_IRON_PICKAXE:
		case IRON_PICKAXE:
			return 4F;
		case LEGACY_GOLD_PICKAXE:
		case GOLDEN_PICKAXE:
			return 2F;
		case LEGACY_DIAMOND_PICKAXE:
		case DIAMOND_PICKAXE:
			return 5F;
		// Spades
		case LEGACY_WOOD_SPADE:
		case WOODEN_SHOVEL:
			return 2.5F;
		case LEGACY_STONE_SPADE:
		case STONE_SHOVEL:
			return 3.5F;
		case LEGACY_IRON_SPADE:
		case IRON_SHOVEL:
			return 4.5F;
		case LEGACY_GOLD_SPADE:
		case GOLDEN_SHOVEL:
			return 2.5F;
		case LEGACY_DIAMOND_SPADE:
		case DIAMOND_SHOVEL:
			return 5.5F;
		// Hoes
		case LEGACY_WOOD_HOE:
		case WOODEN_HOE:
			return 1F;
		case LEGACY_STONE_HOE:
		case STONE_HOE:
			return 1F;
		case LEGACY_IRON_HOE:
		case IRON_HOE:
			return 1F;
		case LEGACY_GOLD_HOE:
		case GOLDEN_HOE:
			return 1F;
		case LEGACY_DIAMOND_HOE:
		case DIAMOND_HOE:
			return 1F;
		default:
			return 1F;

		}
	}

	/**
	 * Retrieve the base damage of the given item.
	 * 
	 * @param stack
	 *            - the stack.
	 * @return The base damage.
	 */
	public static float getBaseSpeed(ItemStack stack) {
		return getBaseSpeed(stack.getType());
	}

	/**
	 * Retrieve the base damage of the given item.
	 * 
	 * @param material
	 *            - the material.
	 * @return The base damage.
	 */
	@SuppressWarnings("deprecation")
	public static float getBaseSpeed(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Swords
		case LEGACY_WOOD_SWORD:
		case WOODEN_SWORD:
		case LEGACY_GOLD_SWORD:
		case GOLDEN_SWORD:
		case LEGACY_STONE_SWORD:
		case STONE_SWORD:
		case LEGACY_IRON_SWORD:
		case IRON_SWORD:
		case LEGACY_DIAMOND_SWORD:
		case DIAMOND_SWORD:
			return 1.6F;
		// Axe
		case LEGACY_WOOD_AXE:
		case WOODEN_AXE:
			return 0.8F;
		case LEGACY_GOLD_AXE:
		case GOLDEN_AXE:
			return 1F;
		case LEGACY_STONE_AXE:
		case STONE_AXE:
			return 0.8F;
		case LEGACY_IRON_AXE:
		case IRON_AXE:
			return 0.9F;
		case LEGACY_DIAMOND_AXE:
		case DIAMOND_AXE:
			return 1F;
		// Pickaxe
		case LEGACY_WOOD_PICKAXE:
		case WOODEN_PICKAXE:
		case LEGACY_STONE_PICKAXE:
		case STONE_PICKAXE:
		case LEGACY_IRON_PICKAXE:
		case IRON_PICKAXE:
		case LEGACY_GOLD_PICKAXE:
		case GOLDEN_PICKAXE:
		case LEGACY_DIAMOND_PICKAXE:
		case DIAMOND_PICKAXE:
			return 1.2F;
		// Spades
		case LEGACY_WOOD_SPADE:
		case WOODEN_SHOVEL:
		case LEGACY_STONE_SPADE:
		case STONE_SHOVEL:
		case LEGACY_IRON_SPADE:
		case IRON_SHOVEL:
		case LEGACY_GOLD_SPADE:
		case GOLDEN_SHOVEL:
		case LEGACY_DIAMOND_SPADE:
		case DIAMOND_SHOVEL:
			return 1F;
		// Hoes
		case LEGACY_WOOD_HOE:
		case WOODEN_HOE:
			return 1F;
		case LEGACY_STONE_HOE:
		case STONE_HOE:
			return 2F;
		case LEGACY_IRON_HOE:
		case IRON_HOE:
			return 3F;
		case LEGACY_GOLD_HOE:
		case GOLDEN_HOE:
			return 1F;
		case LEGACY_DIAMOND_HOE:
		case DIAMOND_HOE:
			return 4F;
		default:
			return 4F;

		}
	}

	/**
	 * Retrieve the base armor of the given armor.
	 * 
	 * @param stack
	 *            - the stack.
	 * @return The base damage.
	 */
	public static float getBaseArmor(ItemStack stack) {
		return getBaseArmor(stack.getType());
	}

	/**
	 * Retrieve the base armor of the given armor.
	 * 
	 * @param material
	 *            - the material.
	 * @return The base damage.
	 */
	@SuppressWarnings("deprecation")
	public static float getBaseArmor(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Leather Armor
		case LEGACY_LEATHER_HELMET:
		case LEATHER_HELMET:
			return 1F;
		case LEGACY_LEATHER_CHESTPLATE:
		case LEATHER_CHESTPLATE:
			return 3F;
		case LEGACY_LEATHER_LEGGINGS:
		case LEATHER_LEGGINGS:
			return 2F;
		case LEGACY_LEATHER_BOOTS:
		case LEATHER_BOOTS:
			return 1F;
		// Chainmail Armor
		case LEGACY_CHAINMAIL_HELMET:
		case CHAINMAIL_HELMET:
			return 2F;
		case LEGACY_CHAINMAIL_CHESTPLATE:
		case CHAINMAIL_CHESTPLATE:
			return 5F;
		case LEGACY_CHAINMAIL_LEGGINGS:
		case CHAINMAIL_LEGGINGS:
			return 4F;
		case LEGACY_CHAINMAIL_BOOTS:
		case CHAINMAIL_BOOTS:
			return 1F;
		// Iron Armor
		case LEGACY_IRON_HELMET:
		case IRON_HELMET:
			return 2F;
		case LEGACY_IRON_CHESTPLATE:
		case IRON_CHESTPLATE:
			return 6F;
		case LEGACY_IRON_LEGGINGS:
		case IRON_LEGGINGS:
			return 5F;
		case LEGACY_IRON_BOOTS:
		case IRON_BOOTS:
			return 2F;
		// Golden Armor
		case LEGACY_GOLD_HELMET:
		case GOLDEN_HELMET:
			return 2F;
		case LEGACY_GOLD_CHESTPLATE:
		case GOLDEN_CHESTPLATE:
			return 5F;
		case LEGACY_GOLD_LEGGINGS:
		case GOLDEN_LEGGINGS:
			return 4F;
		case LEGACY_GOLD_BOOTS:
		case GOLDEN_BOOTS:
			return 1F;
		// Diamond Armor
		case LEGACY_DIAMOND_HELMET:
		case DIAMOND_HELMET:
			return 3F;
		case LEGACY_DIAMOND_CHESTPLATE:
		case DIAMOND_CHESTPLATE:
			return 8F;
		case LEGACY_DIAMOND_LEGGINGS:
		case DIAMOND_LEGGINGS:
			return 6F;
		case LEGACY_DIAMOND_BOOTS:
		case DIAMOND_BOOTS:
			return 3F;
		default:
			return 0F;

		}
	}

	/**
	 * Retrieve the base armor toughness of the given item.
	 * 
	 * @param stack
	 *            - the stack.
	 * @return The base damage.
	 */
	public static float getBaseArmorToughness(ItemStack stack) {
		return getBaseArmorToughness(stack.getType());
	}

	/**
	 * Retrieve the base armor toughness of the given item.
	 * 
	 * @param material
	 *            - the material.
	 * @return The base damage.
	 */
	// TODO: Disable for under 1.9.1
	@SuppressWarnings("deprecation")
	public static float getBaseArmorToughness(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Diamond Armor
		case LEGACY_DIAMOND_HELMET:
		case DIAMOND_HELMET:
			return 2F;
		case LEGACY_DIAMOND_CHESTPLATE:
		case DIAMOND_CHESTPLATE:
			return 2F;
		case LEGACY_DIAMOND_LEGGINGS:
		case DIAMOND_LEGGINGS:
			return 2F;
		case LEGACY_DIAMOND_BOOTS:
		case DIAMOND_BOOTS:
			return 2F;
		default:
			return 0F;

		}
	}

}
