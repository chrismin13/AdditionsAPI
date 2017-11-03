package com.chrismin13.additionsapi.durability;

/**
 * This specifies how a pickaxe will have its durability reduced when performing
 * certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 1<br>
 * EntityHit defautls to 2
 * 
 * @author chrismin13
 */
public class PickaxeDurability extends ItemDurability {

	public PickaxeDurability() {
		super.setBlockBreak(1);
		super.setEntityHit(2);
	}
	
}
