package com.chrismin13.additionsapi.durability;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * This specifies how an item will have its durability reduced when performing
 * certain actions.<br>
 * All values default to 0.
 * 
 * @author chrismin13
 */
public class ItemDurability {

	private int blockBreak = 0;
	private int instantBlockBreak = 0;
	private int entityHit = 0;
	private HashMap<Material, Integer> specialBlockBreak = null;

	/**
	 * @return The amount of durability reduced when breaking a block.
	 */
	public int getBlockBreak() {
		return blockBreak;
	}

	/**
	 * @param block
	 *            The block that you want to know the durability for.
	 * @return The amount of durability reduced when breaking the block
	 *         specified.
	 */
	public int getBlockBreak(Block block) {
		if (specialBlockBreak != null && specialBlockBreak.containsKey(block.getType()))
			return specialBlockBreak.get(block.getType());
		return blockBreak;
	}

	/**
	 * Set the amount of durability reduced when breaking a block.<br>
	 * 
	 * @param blockBreak
	 */
	public ItemDurability setBlockBreak(int blockBreak) {
		this.blockBreak = blockBreak;
		return this;
	}

	/**
	 * @return The amount of durability reduced when breaking a block that has
	 *         an instant breaking speed, e.g. Tall Grass.
	 */
	public int getInstantBlockBreak() {
		return instantBlockBreak;
	}

	/**
	 * Set the amount of durability reduced when breaking a block that has an
	 * instant breaking speed, e.g. Tall Grass.
	 * 
	 * @param instantBlockBreak
	 */
	public ItemDurability setInstantBlockBreak(int instantBlockBreak) {
		this.instantBlockBreak = instantBlockBreak;
		return this;
	}

	/**
	 * @return The amount of durability reduced when hitting another entity.
	 */
	public int getEntityHit() {
		return entityHit;
	}

	/**
	 * Set the amount of durability reduced when hitting another entity.
	 * 
	 * @param entityHit
	 */
	public ItemDurability setEntityHit(int entityHit) {
		this.entityHit = entityHit;
		return this;
	}

	/**
	 * 
	 * Adds a Special Block. <br>
	 * Special Blocks will override the BlockBreak and InstantBlockBreak and
	 * damage the item.
	 * 
	 * @param material
	 *            the block
	 * @param durability
	 *            the durability that will be cut
	 */
	public ItemDurability addSpecialBlock(Material material, int durability) {
		if (specialBlockBreak == null)
			specialBlockBreak = new HashMap<Material, Integer>();
		specialBlockBreak.put(material, durability);
		return this;
	}

	/**
	 * @return the map that contains the special blocks with their durabilities
	 */
	public HashMap<Material, Integer> getAllSpecialBlocks() {
		return specialBlockBreak;
	}

	/**
	 * 
	 * Get the Durability that will be cut for the Block Specified
	 * 
	 * @param material
	 *            the block
	 * @return the durability that will be cut
	 */
	public Integer getSpecialBlockDurability(Material material) {
		if (specialBlockBreak != null && specialBlockBreak.get(material) != null)
			return specialBlockBreak.get(material);
		return null;
	}

}
