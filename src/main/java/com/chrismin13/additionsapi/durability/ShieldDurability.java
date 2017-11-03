package com.chrismin13.additionsapi.durability;

/**
 * This specifies how a shield will have its durability reduced when performing
 * certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 0<br>
 * EntityHit defautls to 0<br>
 * The sheild will be damaged as much as the damage that the player blocking
 * received<br>
 * There are extra durability points taken when blocking a hit, default is 1<br>
 * There is a damage multiplier, it defaults to 1F
 * 
 * @author chrismin13
 */
public class ShieldDurability extends ItemDurability {

	private int extraDurabilityTaken = 1;
	private float damageMultiplier = 1F;

	/**
	 * @return The Extra Durability taken upon blocking a hit.
	 */
	public int getExtraDurabilityTaken() {
		return extraDurabilityTaken;
	}

	/**
	 * Set the Extra Durability taken upon blocking a hit.
	 * 
	 * @param extraDurabilityTaken
	 */
	public ShieldDurability setExtraDurabilityTaken(int extraDurabilityTaken) {
		this.extraDurabilityTaken = extraDurabilityTaken;
		return this;
	}

	/**
	 * If the shield is blocking when the player takes damage by another entity,
	 * the amount of durability reduced by the shield will be however much the
	 * damage taken was. This multiplier determines how much the durability will
	 * be decreased or increased.
	 */
	public float getDamageMultiplier() {
		return damageMultiplier;
	}

	/**
	 * If the shield is blocking when the player takes damage by another entity,
	 * the amount of durability reduced by the shield will be however much the
	 * damage taken was. This multiplier determines how much the durability will
	 * be decreased or increased.
	 * 
	 * @param damageMultiplier
	 */
	public ShieldDurability setDamageMultiplier(float damageMultiplier) {
		this.damageMultiplier = damageMultiplier;
		return this;
	}

}
