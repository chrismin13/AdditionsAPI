package com.chrismin13.additionsapi.durability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * This specifies how an armor piece will have its durability reduced when
 * performing certain actions. Default values are set up exactly like in vanilla
 * Minecraft.<br>
 * thornsExtraDamage defaults to 1<br>
 * thornsExtraDamage defautls to 2<br>
 * Durability drop when an entity is hit is calculated according to the damage
 * dealt to the player wearing the armor, just like in Vanilla Minecraft.<br>
 * All other values of {@link ItemDurability} remain at their defaults.
 * 
 * @author chrismin13
 */
public class ArmorDurability extends ItemDurability {

	private ArrayList<DamageCause> damageCausesWithDurability = new ArrayList<DamageCause>();
	private int thornsExtraDamage = 1;
	private int thornsExtraDamageOnHit = 2;

	public ArmorDurability() {
		damageCausesWithDurability.addAll(Arrays.asList(DamageCause.BLOCK_EXPLOSION, DamageCause.CONTACT,
				DamageCause.ENTITY_ATTACK, DamageCause.ENTITY_EXPLOSION, DamageCause.FIRE, DamageCause.LAVA,
				DamageCause.LIGHTNING, DamageCause.PROJECTILE, DamageCause.THORNS, DamageCause.WITHER));
		try {
			damageCausesWithDurability.add(DamageCause.HOT_FLOOR);
		} catch (NoSuchFieldError ignore) {
		}
		try {
			damageCausesWithDurability.add(DamageCause.ENTITY_SWEEP_ATTACK);
		} catch (NoSuchFieldError ignore) {
		}
	}

	/**
	 * @return The extra durability that will be reduced in case the Armor has
	 *         the Thorns Enchantment.
	 */
	public int getThornsExtraDamage() {
		return thornsExtraDamage;
	}

	/**
	 * Set the extra durability that will be reduced in case the Armor has the
	 * Thorns Enchantment.
	 * 
	 * @param thornsExtraDamage
	 */
	public ArmorDurability setThornsExtraDamage(int thornsExtraDamage) {
		this.thornsExtraDamage = thornsExtraDamage;
		return this;
	}

	/**
	 * @return The {@link DamageCause}s that will affect the Armor's Durability.
	 */
	public List<DamageCause> getDamageCuasesWithDurability() {
		return damageCausesWithDurability;
	}

	/**
	 * Set the {@link DamageCause}s that will affect the Armor's Durability.
	 * 
	 * @param damageCuasesWithDurability
	 */
	public ArmorDurability setDamageCuasesWithDurability(ArrayList<DamageCause> damageCuasesWithDurability) {
		this.damageCausesWithDurability = damageCuasesWithDurability;
		return this;
	}

	/**
	 * @return the thornsExtraDamageOnHit
	 */
	public int getThornsExtraDamageOnHit() {
		return thornsExtraDamageOnHit;
	}

	/**
	 * Set the extra amount of damage that will be dealt in case the Thorn
	 * enchantment hits back the entity that attacked the player that wears this
	 * armor piece.
	 * 
	 * @param thornsExtraDamageOnHit
	 */
	public ArmorDurability setThornsExtraDamageOnHit(int thornsExtraDamageOnHit) {
		this.thornsExtraDamageOnHit = thornsExtraDamageOnHit;
		return this;
	}

	/**
	 * Calculates the damage that the armor will take for the specified damage.
	 * This is according to the Vanilla Minecraft code, which divides the damage
	 * by 4, increases it to at least 1 if it was less, and rounds it to an int.
	 * There are also extra checks to prevent an armor piece to be damaged by a
	 * forbidden {@link DamageCause} as well as calculations for adding extra
	 * damage if the armor piece had Thorns.
	 */
	public int getArmorDamage(DamageCause cause, float damage, boolean hadThorns) {
		if (!damageCausesWithDurability.contains(cause))
			return 0;

		damage = damage / 4.0F;

		if (damage < 1.0F) {
			damage = 1.0F;
		}

		if (hadThorns)
			return thornsExtraDamage + (int) damage;
		else
			return (int) damage;
	}
}
