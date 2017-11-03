package com.chrismin13.additionsapi.durability;

/**
 * TODO: STILL NOT DONE. All values are placeholders for when it finally gets
 * implemented. Testing it in Vanilla had wierd results, seems to be bugged atm.
 * Upon boosting, nothing happened. Who even uses Carrots on a Stick anyway?
 * (unless we're talking 2b2t)<br>
 * This specifies how a Carrot on a Stick will have its durability reduced when
 * performing certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 0<br>
 * EntityHit defautls to 0<br>
 * Boost defaults to 1
 * 
 * @author chrismin13
 */
public class CarrotStickDurability extends ItemDurability {

	private int boost = 7;

	/**
	 * @return The durability reduced when the player right clicks to boost.
	 */
	public int getBoost() {
		return boost;
	}

	/**
	 * Set the durability reduced when the player right clicks to boost.
	 * 
	 * @param boost
	 */
	public CarrotStickDurability setBoost(int boost) {
		this.boost = boost;
		return this;
	}

}
