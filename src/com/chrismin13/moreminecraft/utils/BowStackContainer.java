package com.chrismin13.moreminecraft.utils;

import org.bukkit.entity.LivingEntity;

import com.chrismin13.moreminecraft.items.CustomItemStack;

public class BowStackContainer {
	
	private CustomItemStack cBowStack;
	private LivingEntity owner;
	
	public BowStackContainer(CustomItemStack cBowStack, LivingEntity owner) { 
		this.cBowStack = cBowStack;
		this.owner = owner;
	}

	/**
	 * @return the cBowStack
	 */
	public CustomItemStack getBowStack() {
		return cBowStack;
	}

	/**
	 * @param cBowStack the cBowStack to set
	 */
	public void setBowStack(CustomItemStack cBowStack) {
		this.cBowStack = cBowStack;
	}

	/**
	 * @return the owner
	 */
	public LivingEntity getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(LivingEntity owner) {
		this.owner = owner;
	}
}