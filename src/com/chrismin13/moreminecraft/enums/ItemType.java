package com.chrismin13.moreminecraft.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.durability.ArmorDurability;
import com.chrismin13.moreminecraft.api.durability.BowDurability;
import com.chrismin13.moreminecraft.api.durability.ElytraDurability;
import com.chrismin13.moreminecraft.api.durability.FishingRodDurability;
import com.chrismin13.moreminecraft.api.durability.FlintAndSteelDurability;
import com.chrismin13.moreminecraft.api.durability.ItemDurability;
import com.chrismin13.moreminecraft.api.durability.ShearDurability;
import com.chrismin13.moreminecraft.api.durability.ShieldDurability;

public enum ItemType {

	BLOCK, TOOL, ARMOR, FOOD, RECORD, BOAT, BOOK, RAW_MATERIAL, BOW, BUCKET, UTILITY, BARDING, POTION, PROJECTILE, ELYTRA, SHIELD, ARROW, SHEARS, FISHING_ROD, FLINT_AND_STEEL;

	public static ItemType getItemType(ItemStack item) {
		return getItemType(item.getType());
	}

	public ItemDurability getItemDurability() {
		switch (this) {
		case BOW:
			return new BowDurability();
		case ELYTRA:
			return new ElytraDurability();
		case FISHING_ROD:
			return new FishingRodDurability();
		case FLINT_AND_STEEL:
			return new FlintAndSteelDurability();
		case SHEARS:
			return new ShearDurability();
		case SHIELD:
			return new ShieldDurability();
		case ARMOR:
			return new ArmorDurability();
		case BARDING:
		default:
			return new ItemDurability();
		}
	}

	public static ItemType getItemType(Material material) {
		if (material.isBlock())
			return BLOCK;
		if (material.isEdible())
			return FOOD;
		if (material.isRecord())
			return RECORD;
		switch (material) {
		case BLAZE_POWDER:
			return RAW_MATERIAL;
		case BLAZE_ROD:
			return RAW_MATERIAL;
		case BOAT:
			return BOAT;
		case BOAT_ACACIA:
			return BOAT;
		case BOAT_BIRCH:
			return BOAT;
		case BOAT_DARK_OAK:
			return BOAT;
		case BOAT_JUNGLE:
			return BOAT;
		case BOAT_SPRUCE:
			return BOAT;
		case BONE:
			return RAW_MATERIAL;
		case BOOK:
			return BOOK;
		case BOOK_AND_QUILL:
			return BOOK;
		case BOW:
			return BOW;
		case BOWL:
			return RAW_MATERIAL;
		case BROWN_MUSHROOM:
			return RAW_MATERIAL;
		case BUCKET:
			return BUCKET;
		case CARROT_STICK:
			return UTILITY;
		case CHAINMAIL_BOOTS:
			return ARMOR;
		case CHAINMAIL_CHESTPLATE:
			return ARMOR;
		case CHAINMAIL_HELMET:
			return ARMOR;
		case CHAINMAIL_LEGGINGS:
			return ARMOR;
		case CLAY_BALL:
			return RAW_MATERIAL;
		case COAL:
			return RAW_MATERIAL;
		case COMPASS:
			return UTILITY;
		case DIAMOND:
			return RAW_MATERIAL;
		case DIAMOND_AXE:
			return TOOL;
		case DIAMOND_BARDING:
			return BARDING;
		case DIAMOND_BOOTS:
			return ARMOR;
		case DIAMOND_CHESTPLATE:
			return ARMOR;
		case DIAMOND_HELMET:
			return ARMOR;
		case DIAMOND_HOE:
			return TOOL;
		case DIAMOND_LEGGINGS:
			return ARMOR;
		case DIAMOND_PICKAXE:
			return TOOL;
		case DIAMOND_SPADE:
			return TOOL;
		case DIAMOND_SWORD:
			return TOOL;
		case DRAGONS_BREATH:
			return POTION;
		case EGG:
			return PROJECTILE;
		case ELYTRA:
			return ELYTRA;
		case EMERALD:
			return RAW_MATERIAL;
		case EMPTY_MAP:
			return UTILITY;
		case ENCHANTED_BOOK:
			return BOOK;
		case ENDER_PEARL:
			return UTILITY;
		case EYE_OF_ENDER:
			return UTILITY;
		case FEATHER:
			return RAW_MATERIAL;
		case FIREBALL:
			return UTILITY;
		case FIREWORK:
			return UTILITY;
		case FIREWORK_CHARGE:
			return RAW_MATERIAL;
		case FISHING_ROD:
			return FISHING_ROD;
		case FLINT:
			return RAW_MATERIAL;
		case FLINT_AND_STEEL:
			return FLINT_AND_STEEL;
		case GHAST_TEAR:
			return RAW_MATERIAL;
		case GLASS_BOTTLE:
			return UTILITY;
		case GLOWSTONE_DUST:
			return RAW_MATERIAL;
		case GOLD_AXE:
			return TOOL;
		case GOLD_BARDING:
			return BARDING;
		case GOLD_BOOTS:
			return ARMOR;
		case GOLD_CHESTPLATE:
			return ARMOR;
		case GOLD_HELMET:
			return ARMOR;
		case GOLD_HOE:
			return TOOL;
		case GOLD_INGOT:
			return RAW_MATERIAL;
		case GOLD_LEGGINGS:
			return ARMOR;
		case GOLD_NUGGET:
			return RAW_MATERIAL;
		case GOLD_PICKAXE:
			return TOOL;
		case GOLD_SPADE:
			return TOOL;
		case GOLD_SWORD:
			return TOOL;
		case INK_SACK:
			return RAW_MATERIAL;
		case IRON_AXE:
			return TOOL;
		case IRON_BARDING:
			return BARDING;
		case IRON_BOOTS:
			return ARMOR;
		case IRON_CHESTPLATE:
			return ARMOR;
		case IRON_HELMET:
			return ARMOR;
		case IRON_HOE:
			return TOOL;
		case IRON_INGOT:
			return RAW_MATERIAL;
		case IRON_LEGGINGS:
			return ARMOR;
		case IRON_PICKAXE:
			return TOOL;
		case IRON_SPADE:
			return TOOL;
		case IRON_SWORD:
			return TOOL;
		case LAVA_BUCKET:
			return BUCKET;
		case LEASH:
			return UTILITY;
		case LEATHER:
			return RAW_MATERIAL;
		case LEATHER_BOOTS:
			return ARMOR;
		case LEATHER_CHESTPLATE:
			return ARMOR;
		case LEATHER_HELMET:
			return ARMOR;
		case LEATHER_LEGGINGS:
			return ARMOR;
		case LINGERING_POTION:
			return POTION;
		case MAGMA_CREAM:
			return RAW_MATERIAL;
		case MAP:
			return UTILITY;
		case MILK_BUCKET:
			return BUCKET;
		case MONSTER_EGGS:
			return UTILITY;
		case NAME_TAG:
			return UTILITY;
		case NETHER_STAR:
			return RAW_MATERIAL;
		case PAPER:
			return RAW_MATERIAL;
		case POTION:
			return POTION;
		case PRISMARINE_CRYSTALS:
			return RAW_MATERIAL;
		case PRISMARINE_SHARD:
			return RAW_MATERIAL;
		case QUARTZ:
			return RAW_MATERIAL;
		case REDSTONE:
			return RAW_MATERIAL;
		case SADDLE:
			return UTILITY;
		case SHEARS:
			return SHEARS;
		case SHIELD:
			return SHIELD;
		case SLIME_BALL:
			return RAW_MATERIAL;
		case SNOW_BALL:
			return PROJECTILE;
		case SPECTRAL_ARROW:
			return ARROW;
		case SPIDER_EYE:
			return RAW_MATERIAL;
		case SPLASH_POTION:
			return POTION;
		case STICK:
			return RAW_MATERIAL;
		case STONE_AXE:
			return TOOL;
		case STONE_HOE:
			return TOOL;
		case STONE_PICKAXE:
			return TOOL;
		case STONE_SPADE:
			return TOOL;
		case STONE_SWORD:
			return TOOL;
		case TIPPED_ARROW:
			return ARROW;
		case WATCH:
			return UTILITY;
		case WATER_BUCKET:
			return BUCKET;
		case WOOD_AXE:
			return TOOL;
		case WOOD_HOE:
			return TOOL;
		case WOOD_PICKAXE:
			return TOOL;
		case WOOD_SPADE:
			return TOOL;
		case WOOD_SWORD:
			return TOOL;
		case WRITTEN_BOOK:
			return BOOK;
		default:
			break;
		}

		return null;
	}

}
