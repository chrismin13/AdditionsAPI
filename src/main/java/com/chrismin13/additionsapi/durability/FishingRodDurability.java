package com.chrismin13.additionsapi.durability;

/**
 * This specifies how a Fishing Rod will have its durability reduced when
 * performing certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 0<br>
 * EntityHit defautls to 0<br>
 * Fish Catch defaults to 1<br>
 * Block Reel defaults to 2<br>
 * Item Reel defaults to 3<br>
 * Entity Reel defaults to 5
 * 
 * @author chrismin13
 */

public class FishingRodDurability extends ItemDurability {

	private int fishCatch = 1;
	private int blockReel = 2;
	private int itemReel = 3;
	private int entityReel = 5;

	/**
	 * @return The durability reduced when a fish is caught.
	 */
	public int getFishCatch() {
		return fishCatch;
	}

	/**
	 * Set the durability reduced when a fish is caught.
	 * 
	 * @param fishCatch
	 */
	public FishingRodDurability setFishCatch(int fishCatch) {
		this.fishCatch = fishCatch;
		return this;
	}

	/**
	 * @return The durability reduced when the hook is on a block and the
	 *         fishing rod is reeled.
	 */
	public int getBlockReel() {
		return blockReel;
	}

	/**
	 * Set the durability reduced when the hook is on a block and the fishing
	 * rod is reeled.
	 * 
	 * @param blockReel
	 */
	public FishingRodDurability setBlockReel(int blockReel) {
		this.blockReel = blockReel;
		return this;
	}

	/**
	 * @return The durability reduced when the hook is on an item on the ground
	 *         and the fishing rod is reeled.
	 */
	public int getItemReel() {
		return itemReel;
	}

	/**
	 * Set the durability reduced when the hook is on an item on the ground and
	 * the fishing rod is reeled.
	 * 
	 * @param itemReel
	 */
	public FishingRodDurability setItemReel(int itemReel) {
		this.itemReel = itemReel;
		return this;
	}

	/**
	 * @return The durability reduced when the hook is on an entity that is
	 *         neither a fish nor an item and the fishing rod is reeled.
	 */
	public int getEntityReel() {
		return entityReel;
	}

	/**
	 * Set the durability reduced when the hook is on an entity that is neither
	 * a fish nor an item and the fishing rod is reeled.
	 * 
	 * @param entityReel
	 */
	public FishingRodDurability setEntityReel(int entityReel) {
		this.entityReel = entityReel;
		return this;
	}

}
