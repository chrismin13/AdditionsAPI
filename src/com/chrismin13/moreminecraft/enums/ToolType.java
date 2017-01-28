package com.chrismin13.moreminecraft.enums;

import org.bukkit.Material;

public enum ToolType {

	SWORD, AXE, PICKAXE, SPADE, HOE;

	public static ToolType getToolType(Material material) {
		switch (material) {
		// Swords
		case WOOD_SWORD:
			return SWORD;
		case GOLD_SWORD:
			return SWORD;
		case STONE_SWORD:
			return SWORD;
		case IRON_SWORD:
			return SWORD;
		case DIAMOND_SWORD:
			return SWORD;
		// Axe
		case WOOD_AXE:
			return AXE;
		case GOLD_AXE:
			return AXE;
		case STONE_AXE:
			return AXE;
		case IRON_AXE:
			return AXE;
		case DIAMOND_AXE:
			return AXE;
		// Pickaxe
		case WOOD_PICKAXE:
			return PICKAXE;
		case STONE_PICKAXE:
			return PICKAXE;
		case IRON_PICKAXE:
			return PICKAXE;
		case GOLD_PICKAXE:
			return PICKAXE;
		case DIAMOND_PICKAXE:
			return PICKAXE;
		// Spades
		case WOOD_SPADE:
			return SPADE;
		case STONE_SPADE:
			return SPADE;
		case IRON_SPADE:
			return SPADE;
		case GOLD_SPADE:
			return SPADE;
		case DIAMOND_SPADE:
			return SPADE;
		// Hoes
		case WOOD_HOE:
			return HOE;
		case STONE_HOE:
			return HOE;
		case IRON_HOE:
			return HOE;
		case GOLD_HOE:
			return HOE;
		case DIAMOND_HOE:
			return HOE;
		default:
			return null;

		}
	}

}
