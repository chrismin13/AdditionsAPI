package com.chrismin13.moreminecraft.api.durability;

import java.util.Arrays;
import java.util.List;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class ArmorDurability extends ItemDurability {

	private List<DamageCause> damageCuasesWithDurability = Arrays.asList(DamageCause.BLOCK_EXPLOSION,
			DamageCause.CONTACT, DamageCause.ENTITY_ATTACK, DamageCause.ENTITY_EXPLOSION,
			DamageCause.ENTITY_SWEEP_ATTACK, DamageCause.FIRE, DamageCause.HOT_FLOOR, DamageCause.LAVA,
			DamageCause.LIGHTNING, DamageCause.PROJECTILE, DamageCause.THORNS, DamageCause.WITHER);
	private int thornsExtraDamage = 1;
	private int thornsExtraDamageOnHit = 2;

	public int getThornsExtraDamage() {
		return thornsExtraDamage;
	}

	public void setThornsExtraDamage(int thornsExtraDamage) {
		this.thornsExtraDamage = thornsExtraDamage;
	}

	/**
	 * @return the damageCuasesWithDurability
	 */
	public List<DamageCause> getDamageCuasesWithDurability() {
		return damageCuasesWithDurability;
	}

	/**
	 * @param damageCuasesWithDurability
	 *            the damageCuasesWithDurability to set
	 */
	public void setDamageCuasesWithDurability(List<DamageCause> damageCuasesWithDurability) {
		this.damageCuasesWithDurability = damageCuasesWithDurability;
	}

	/**
	 * @return the thornsExtraDamageOnHit
	 */
	public int getThornsExtraDamageOnHit() {
		return thornsExtraDamageOnHit;
	}

	/**
	 * @param thornsExtraDamageOnHit
	 *            the thornsExtraDamageOnHit to set
	 */
	public void setThornsExtraDamageOnHit(int thornsExtraDamageOnHit) {
		this.thornsExtraDamageOnHit = thornsExtraDamageOnHit;
	}

	/**
	 * Calculates the damage that the armor will take for the specified damage
	 */
	public int getArmorDamage(DamageCause cause, float damage, boolean hadThorns) {
		if (!damageCuasesWithDurability.contains(cause))
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
