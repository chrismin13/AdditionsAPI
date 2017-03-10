package com.chrismin13.moreminecraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.durability.ArmorDurability;
import com.chrismin13.moreminecraft.api.items.CustomArmor;

public class PlayerCustomArmorDamageEvent extends PlayerCustomItemDamageEvent {

	private DamageCause damageCause;

	public PlayerCustomArmorDamageEvent(PlayerItemDamageEvent event, CustomArmor cArmor, DamageCause damageCause) {
		super(event, cArmor);
		event.setDamage(getDamage(damageCause, cArmor.getDurabilityMechanics()));
		setDamageCause(damageCause);
	}

	public PlayerCustomArmorDamageEvent(Player player, ItemStack what, CustomArmor cArmor, DamageCause damageCause) {
		super(player, what, getDamage(damageCause, cArmor.getDurabilityMechanics()), cArmor);
		setDamageCause(damageCause);
	}

	private static int getDamage(DamageCause damageCause, ArmorDurability dur) {
		if (damageCause == DamageCause.ENTITY_ATTACK)
			return dur.getHitByEntity();
		return 0;
	}
	
	/**
	 * @return the damageCause
	 */
	public DamageCause getDamageCause() {
		return damageCause;
	}

	/**
	 * @param damageCause
	 *            the damageCause to set
	 */
	public void setDamageCause(DamageCause damageCause) {
		this.damageCause = damageCause;
	}

}
