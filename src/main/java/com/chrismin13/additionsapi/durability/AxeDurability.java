package com.chrismin13.additionsapi.durability;

/**
 * This specifies how an axe will have its durability reduced when performing
 * certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * BlockBreak defaults to 1<br>
 * EntityHit defautls to 2
 * 
 * @author chrismin13
 */
public class AxeDurability extends ItemDurability {

	public AxeDurability() {
		super.setBlockBreak(1);
		super.setEntityHit(2);
	}

}