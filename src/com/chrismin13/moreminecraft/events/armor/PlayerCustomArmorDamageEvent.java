package com.chrismin13.moreminecraft.events.armor;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.moreminecraft.items.CustomArmor;

public class PlayerCustomArmorDamageEvent extends PlayerCustomItemDamageEvent {

	private DamageCause damageCause;

	public PlayerCustomArmorDamageEvent(PlayerItemDamageEvent event, CustomArmor cArmor, DamageCause damageCause, short durability) {
		super(event, cArmor);
		event.setDamage(durability);
		setDamageCause(damageCause);
	}

	public PlayerCustomArmorDamageEvent(Player player, ItemStack item, CustomArmor cArmor, DamageCause damageCause, short durability) {
		super(player, item, durability, cArmor);
		setDamageCause(damageCause);
	}
	
	public PlayerCustomArmorDamageEvent(Player player, ItemStack item, CustomArmor cArmor, DamageCause damageCause, float damage, boolean hadThorns) {
		this(player, item, cArmor, damageCause, (short) cArmor.getDurabilityMechanics().getArmorDamage(damageCause, damage, hadThorns));
	}
	
	public PlayerCustomArmorDamageEvent(Player player, ItemStack item, CustomArmor cArmor, boolean usedThorns) {
		this(player, item, cArmor, DamageCause.THORNS, (short) cArmor.getDurabilityMechanics().getThornsExtraDamageOnHit());
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
