package com.chrismin13.moreminecraft.durability;

public class SpadeDurability extends ItemDurability {

	private int pathTile = 1;

	public SpadeDurability() {
		super.setBlockBreak(1);
		super.setEntityHit(2);
	}
	
	/**
	 * @return the pathTile
	 */
	public int getPathTile() {
		return pathTile;
	}

	/**
	 * @param pathTile
	 *            the pathTile to set
	 */
	public void setPathTile(int pathTile) {
		this.pathTile = pathTile;
	}
	
}
