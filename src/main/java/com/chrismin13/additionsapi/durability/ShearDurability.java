package com.chrismin13.additionsapi.durability;

import org.bukkit.Material;

/**
 * This specifies how shears will have their durability reduced when performing
 * certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 0<br>
 * EntityHit defautls to 0<br>
 * Instant block break defaults to 1<br>
 * Special blocks include: Web, Leaves, Leaves_2, Wool and Vine. They all
 * default to 1<br>
 * Shearing sheep defaults to 1
 * 
 * @author chrismin13
 */
public class ShearDurability extends ItemDurability {

	private int shearSheep = 1;

	public ShearDurability() {
		super.setInstantBlockBreak(1);
		super.addSpecialBlock(Material.COBWEB, 1);
		super.addSpecialBlock(Material.ACACIA_LEAVES, 1);
		super.addSpecialBlock(Material.BIRCH_LEAVES, 1);
		super.addSpecialBlock(Material.JUNGLE_LEAVES, 1);
		super.addSpecialBlock(Material.OAK_LEAVES, 1);
		super.addSpecialBlock(Material.SPRUCE_LEAVES, 1);
		super.addSpecialBlock(Material.BLACK_WOOL, 1);
		super.addSpecialBlock(Material.BLUE_WOOL, 1);
		super.addSpecialBlock(Material.BROWN_WOOL, 1);
		super.addSpecialBlock(Material.CYAN_WOOL, 1);
		super.addSpecialBlock(Material.GRAY_WOOL, 1);
		super.addSpecialBlock(Material.GREEN_WOOL, 1);
		super.addSpecialBlock(Material.LIME_WOOL, 1);
		super.addSpecialBlock(Material.MAGENTA_WOOL, 1);
		super.addSpecialBlock(Material.ORANGE_WOOL, 1);
		super.addSpecialBlock(Material.PINK_WOOL, 1);
		super.addSpecialBlock(Material.PURPLE_WOOL, 1);
		super.addSpecialBlock(Material.RED_WOOL, 1);
		super.addSpecialBlock(Material.WHITE_WOOL, 1);
		super.addSpecialBlock(Material.YELLOW_WOOL, 1);
		super.addSpecialBlock(Material.LIGHT_BLUE_WOOL, 1);
		super.addSpecialBlock(Material.LIGHT_GRAY_WOOL, 1);
		super.addSpecialBlock(Material.VINE, 1);
	}

	/**
	 * @return The durability reduced when shearing sheep.
	 */
	public int getShearSheep() {
		return shearSheep;
	}

	/**
	 * Set the durability reduced when shearing sheep.
	 * 
	 * @param shearSheep
	 */
	public ShearDurability setShearSheep(int shearSheep) {
		this.shearSheep = shearSheep;
		return this;
	}

}
