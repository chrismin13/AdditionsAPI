package com.chrismin13.additionsapi.durability;

/**
 * This specifies how a spade will have its durability reduced when performing
 * certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 1<br>
 * EntityHit defautls to 2<br>
 * PathTile defaults to 1
 * 
 * @author chrismin13
 */
public class SpadeDurability extends ItemDurability {

	private int pathTile = 1;

	public SpadeDurability() {
		super.setBlockBreak(1);
		super.setEntityHit(2);
	}
	
	/**
	 * @return The damage that will be reduced if a path block is created using the Spade.
	 */
	public int getPathTile() {
		return pathTile;
	}

	/**
	 * Set the damage that will be reduced if a path block is created using the Spade.
	 * @param pathTile
	 */
	public SpadeDurability setPathTile(int pathTile) {
		this.pathTile = pathTile;
		return this;
	}
	
}
