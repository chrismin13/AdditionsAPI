package com.chrismin13.moreminecraft.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.chrismin13.moreminecraft.api.items.CustomItemStack;

public class ArrowFromCustomBowHitEvent extends CustomBowStackEvent {

	private ProjectileHitEvent projectileHitEvent;
	private LivingEntity shooter;
	
	public ArrowFromCustomBowHitEvent(ProjectileHitEvent event, LivingEntity shooter, CustomItemStack cBowStack) {
		super(cBowStack);
		this.projectileHitEvent = event;
		this.shooter = shooter;
	}

	/**
	 * @return the projectileHitEvent
	 */
	public ProjectileHitEvent getProjectileHitEvent() {
		return projectileHitEvent;
	}

	/**
	 * @param projectileHitEvent the projectileHitEvent to set
	 */
	public void setProjectileHitEvent(ProjectileHitEvent projectileHitEvent) {
		this.projectileHitEvent = projectileHitEvent;
	}

	/**
	 * @return the shooter
	 */
	public LivingEntity getShooter() {
		return shooter;
	}

	/**
	 * @param shooter the shooter to set
	 */
	public void setShooter(LivingEntity shooter) {
		this.shooter = shooter;
	}	
}
