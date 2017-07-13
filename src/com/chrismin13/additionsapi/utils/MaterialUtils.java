package com.chrismin13.additionsapi.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialUtils {

	private static List<Material> instantlyBreakable = Arrays.asList(Material.WATER_LILY, Material.CARROT,
			Material.DEAD_BUSH, Material.FIRE, Material.FLOWER_POT, Material.LONG_GRASS, Material.MELON_STEM,
			Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.NETHER_WARTS, Material.POTATO,
			Material.PUMPKIN_STEM, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON,
			Material.DIODE_BLOCK_ON, Material.DIODE_BLOCK_OFF, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON,
			Material.REDSTONE_WIRE, Material.SAPLING, Material.SLIME_BLOCK, Material.SUGAR_CANE_BLOCK, Material.TNT,
			Material.TORCH, Material.TRIPWIRE, Material.TRIPWIRE_HOOK, Material.SEEDS, Material.WHEAT,
			Material.BEETROOT_BLOCK, Material.END_ROD, Material.DOUBLE_PLANT);

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

	private static List<Material> willDamageShears = Arrays.asList(Material.WEB, Material.LEAVES, Material.LEAVES_2,
			Material.WOOL, Material.VINE);

	public static boolean willDamageShears(Material material) {
		if (willDamageShears.contains(material) || isInstantlyBreakable(material))
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
	public static float getBaseDamage(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Swords
		case WOOD_SWORD:
			return 4F;
		case GOLD_SWORD:
			return 4F;
		case STONE_SWORD:
			return 5F;
		case IRON_SWORD:
			return 6F;
		case DIAMOND_SWORD:
			return 7F;
		// Axe
		case WOOD_AXE:
			return 7F;
		case GOLD_AXE:
			return 7F;
		case STONE_AXE:
			return 9F;
		case IRON_AXE:
			return 9F;
		case DIAMOND_AXE:
			return 9F;
		// Pickaxe
		case WOOD_PICKAXE:
			return 2F;
		case STONE_PICKAXE:
			return 3F;
		case IRON_PICKAXE:
			return 4F;
		case GOLD_PICKAXE:
			return 2F;
		case DIAMOND_PICKAXE:
			return 5F;
		// Spades
		case WOOD_SPADE:
			return 2.5F;
		case STONE_SPADE:
			return 3.5F;
		case IRON_SPADE:
			return 4.5F;
		case GOLD_SPADE:
			return 2.5F;
		case DIAMOND_SPADE:
			return 5.5F;
		// Hoes
		case WOOD_HOE:
			return 1F;
		case STONE_HOE:
			return 1F;
		case IRON_HOE:
			return 1F;
		case GOLD_HOE:
			return 1F;
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
	public static float getBaseSpeed(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Swords
		case WOOD_SWORD:
			return 1.6F;
		case GOLD_SWORD:
			return 1.6F;
		case STONE_SWORD:
			return 1.6F;
		case IRON_SWORD:
			return 1.6F;
		case DIAMOND_SWORD:
			return 1.6F;
		// Axe
		case WOOD_AXE:
			return 0.8F;
		case GOLD_AXE:
			return 1F;
		case STONE_AXE:
			return 0.8F;
		case IRON_AXE:
			return 0.9F;
		case DIAMOND_AXE:
			return 1F;
		// Pickaxe
		case WOOD_PICKAXE:
			return 1.2F;
		case STONE_PICKAXE:
			return 1.2F;
		case IRON_PICKAXE:
			return 1.2F;
		case GOLD_PICKAXE:
			return 1.2F;
		case DIAMOND_PICKAXE:
			return 1.2F;
		// Spades
		case WOOD_SPADE:
			return 1F;
		case STONE_SPADE:
			return 1F;
		case IRON_SPADE:
			return 1F;
		case GOLD_SPADE:
			return 1F;
		case DIAMOND_SPADE:
			return 1F;
		// Hoes
		case WOOD_HOE:
			return 1F;
		case STONE_HOE:
			return 2F;
		case IRON_HOE:
			return 3F;
		case GOLD_HOE:
			return 1F;
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
	public static float getBaseArmor(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Leather Armor
		case LEATHER_HELMET:
			return 1F;
		case LEATHER_CHESTPLATE:
			return 3F;
		case LEATHER_LEGGINGS:
			return 2F;
		case LEATHER_BOOTS:
			return 1F;
		// Chainmail Armor
		case CHAINMAIL_HELMET:
			return 2F;
		case CHAINMAIL_CHESTPLATE:
			return 5F;
		case CHAINMAIL_LEGGINGS:
			return 4F;
		case CHAINMAIL_BOOTS:
			return 1F;
		// Iron Armor
		case IRON_HELMET:
			return 2F;
		case IRON_CHESTPLATE:
			return 6F;
		case IRON_LEGGINGS:
			return 5F;
		case IRON_BOOTS:
			return 2F;
		// Golden Armor
		case GOLD_HELMET:
			return 2F;
		case GOLD_CHESTPLATE:
			return 5F;
		case GOLD_LEGGINGS:
			return 4F;
		case GOLD_BOOTS:
			return 1F;
		// Diamond Armor
		case DIAMOND_HELMET:
			return 3F;
		case DIAMOND_CHESTPLATE:
			return 8F;
		case DIAMOND_LEGGINGS:
			return 6F;
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
	public static float getBaseArmorToughness(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Swords
		case DIAMOND_HELMET:
			return 2F;
		case DIAMOND_CHESTPLATE:
			return 2F;
		case DIAMOND_LEGGINGS:
			return 2F;
		case DIAMOND_BOOTS:
			return 2F;
		default:
			return 0F;

		}
	}


}
