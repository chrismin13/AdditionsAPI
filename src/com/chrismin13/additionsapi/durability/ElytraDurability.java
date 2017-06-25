package com.chrismin13.additionsapi.durability;

/**
 * This specifies how an elytra will have its durability reduced when performing
 * certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 0<br>
 * EntityHit defautls to 0<br>
 * Flight for 1 second defaults to 1
 * 
 * @author chrismin13
 */
public class ElytraDurability extends ItemDurability {

	private int flightOneSecond = 1;

	/**
	 * @return The durability reduced when flying for 1 second.
	 */
	public int getFlightOneSecond() {
		return flightOneSecond;
	}

	/**
	 * Set the durability reduced when flying for 1 second.
	 * 
	 * @param flightOneSecond
	 */
	public ElytraDurability setFlightOneSecond(int flightOneSecond) {
		this.flightOneSecond = flightOneSecond;
		return this;
	}
	
}
