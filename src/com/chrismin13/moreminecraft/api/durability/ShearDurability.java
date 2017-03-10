package com.chrismin13.moreminecraft.api.durability;

import org.bukkit.Material;

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
	 * @return the shearSheep
	 */
	public int getShearSheep() {
		return shearSheep;
	}

	/**
	 * @param shearSheep the shearSheep to set
	 */
	public void setShearSheep(int shearSheep) {
		this.shearSheep = shearSheep;
	}
	
}
