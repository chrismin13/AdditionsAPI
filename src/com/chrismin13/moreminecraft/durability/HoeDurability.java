package com.chrismin13.moreminecraft.durability;

public class HoeDurability extends ItemDurability {

	private int hoe = 1;

	public HoeDurability() {
		super.setEntityHit(1);
	}
	
	/**
	 * @return the hoe
	 */
	public int getHoe() {
		return hoe;
	}

	/**
	 * @param hoe the hoe to set
	 */
	public void setHoe(int hoe) {
		this.hoe = hoe;
	}
	
}
