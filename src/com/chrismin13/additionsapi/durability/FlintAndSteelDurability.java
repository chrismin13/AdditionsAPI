package com.chrismin13.additionsapi.durability;

/**
 * This specifies how a Flint and Steel will have its durability reduced when
 * performing certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 0<br>
 * EntityHit defautls to 0<br>
 * Setting a block on fire defaults to 1
 * 
 * @author chrismin13
 */
public class FlintAndSteelDurability extends ItemDurability {

	private int fire = 1;

	/**
	 * @return The durability reduced when setting a block on fire
	 */
	public int getFire() {
		return fire;
	}

	/**
	 * Set the durability reduced when setting a block on fire
	 * 
	 * @param fire
	 */
	public FlintAndSteelDurability setFire(int fire) {
		this.fire = fire;
		return this;
	}

}
