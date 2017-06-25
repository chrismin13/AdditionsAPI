package com.chrismin13.additionsapi.utils;

import org.bukkit.entity.LivingEntity;

import com.chrismin13.additionsapi.items.CustomItemStack;

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
	public BowStackContainer setBowStack(CustomItemStack cBowStack) {
		this.cBowStack = cBowStack;
		return this;
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
	public BowStackContainer setOwner(LivingEntity owner) {
		this.owner = owner;
		return this;
	}
}