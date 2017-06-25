package com.chrismin13.additionsapi.durability;

/**
 * This specifies how a hoe will have its durability reduced when performing
 * certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 0<br>
 * EntityHit defautls to 1<br>
 * Hoe defaults to 1
 * 
 * @author chrismin13
 */
public class HoeDurability extends ItemDurability {

	private int hoe = 1;

	public HoeDurability() {
		super.setEntityHit(1);
	}

	/**
	 * @return The damage that will be reduced when you hoe the ground.
	 */
	public int getHoe() {
		return hoe;
	}

	/**
	 * Set the damage that will be reduced when you hoe the ground.
	 * 
	 * @param hoe
	 */
	public HoeDurability setHoe(int hoe) {
		this.hoe = hoe;
		return this;
	}

}
