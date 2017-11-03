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
		super.addSpecialBlock(Material.WEB, 1);
		super.addSpecialBlock(Material.LEAVES, 1);
		super.addSpecialBlock(Material.LEAVES_2, 1);
		super.addSpecialBlock(Material.WOOL, 1);
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
