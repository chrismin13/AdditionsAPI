package com.chrismin13.additionsapi.durability;

/**
 * This specifies how a bow will have its durability reduced when performing
 * certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 0<br>
 * EntityHit defautls to 0<br>
 * Fire arrow defaults to 1
 * 
 * @author chrismin13
 */
public class BowDurability extends ItemDurability {

	private int fireArrow = 1;

	/**
	 * @return The durability reduced when using the bow to fire an arrow.
	 */
	public int getFireArrow() {
		return fireArrow;
	}

	/**
	 * Set the durability reduced when using the bow to fire an arrow.
	 * 
	 * @param fireArrow
	 */
	public BowDurability setFireArrow(int fireArrow) {
		this.fireArrow = fireArrow;
		return this;
	}

}
