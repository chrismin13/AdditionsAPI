package com.chrismin13.moreminecraft.api.durability;

public class ShieldDurability extends ItemDurability {

	private int extraDurabilityTaken = 1;
	private float damageMultiplier = 1F;
	
	/**
	 * @return the extraDurabilityTaken
	 */
	public int getExtraDurabilityTaken() {
		return extraDurabilityTaken;
	}

	/**
	 * @param extraDurabilityTaken the extraDurabilityTaken to set
	 */
	public void setExtraDurabilityTaken(int extraDurabilityTaken) {
		this.extraDurabilityTaken = extraDurabilityTaken;
	}

	public float getDamageMultiplier() {
		return damageMultiplier;
	}

	public void setDamageMultiplier(float damageMultiplier) {
		this.damageMultiplier = damageMultiplier;
	}
	
}
