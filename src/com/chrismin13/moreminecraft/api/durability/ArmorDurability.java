package com.chrismin13.moreminecraft.api.durability;

import java.util.Arrays;
import java.util.List;

public class ArmorDurability extends ItemDurability {

	private int hitByEntity = 1;
	private int hitByBlock = 1;
	private int thornsExtraDamage;
	private List<Integer> nearExplosionDamage = Arrays.asList(13, 14);
	private List<Integer> farExplosionDamage = Arrays.asList(1);
	
	public int getHitByEntity() {
		return hitByEntity;
	}

	public void setHitByEntity(int hitByEntity) {
		this.hitByEntity = hitByEntity;
	}

	public int getHitByBlock() {
		return hitByBlock;
	}

	public void setHitByBlock(int hitByBlock) {
		this.hitByBlock = hitByBlock;
	}

	public int getThornsExtraDamage() {
		return thornsExtraDamage;
	}

	public void setThornsExtraDamage(int thornsExtraDamage) {
		this.thornsExtraDamage = thornsExtraDamage;
	}

	public List<Integer> getNearExplosionDamage() {
		return nearExplosionDamage;
	}

	public void setNearExplosionDamage(List<Integer> nearExplosionDamage) {
		this.nearExplosionDamage = nearExplosionDamage;
	}

	public void addNearExplosionDamage(int durability) {
		this.nearExplosionDamage.add(durability);
	}

	public List<Integer> getFarExplosionDamage() {
		return farExplosionDamage;
	}

	public void setFarExplosionDamage(List<Integer> farExplosionDamage) {
		this.farExplosionDamage = farExplosionDamage;
	}

	public void addFarExplosionDamage(int durability) {
		this.farExplosionDamage.add(durability);
	}

}
