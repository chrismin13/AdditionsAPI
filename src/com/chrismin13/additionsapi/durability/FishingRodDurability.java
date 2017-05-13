package com.chrismin13.additionsapi.durability;

public class FishingRodDurability extends ItemDurability {

	private int fishCatch = 1;
	private int blockReel = 2;
	private int itemReel = 3;
	private int entityReel = 5;
	/**
	 * @return the fishCatch
	 */
	public int getFishCatch() {
		return fishCatch;
	}
	/**
	 * @param fishCatch the fishCatch to set
	 */
	public void setFishCatch(int fishCatch) {
		this.fishCatch = fishCatch;
	}
	/**
	 * @return the blockReel
	 */
	public int getBlockReel() {
		return blockReel;
	}
	/**
	 * @param blockReel the blockReel to set
	 */
	public void setBlockReel(int blockReel) {
		this.blockReel = blockReel;
	}
	/**
	 * @return the itemReel
	 */
	public int getItemReel() {
		return itemReel;
	}
	/**
	 * @param itemReel the itemReel to set
	 */
	public void setItemReel(int itemReel) {
		this.itemReel = itemReel;
	}
	/**
	 * @return the entityReel
	 */
	public int getEntityReel() {
		return entityReel;
	}
	/**
	 * @param entityReel the entityReel to set
	 */
	public void setEntityReel(int entityReel) {
		this.entityReel = entityReel;
	}
	
}
