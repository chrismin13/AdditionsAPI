package com.chrismin13.additionsapi.durability;

/**
 * This specifies how a sword will have its durability reduced when performing
 * certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 2<br>
 * EntityHit defautls to 1
 * 
 * @author chrismin13
 */
public class SwordDurability extends ItemDurability {

	public SwordDurability() {
		super.setBlockBreak(2);
		super.setEntityHit(1);
	}

}
