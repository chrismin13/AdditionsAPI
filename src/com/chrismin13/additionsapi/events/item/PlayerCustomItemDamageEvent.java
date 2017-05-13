package com.chrismin13.additionsapi.events.item;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.items.CustomItem;

public class PlayerCustomItemDamageEvent extends PlayerItemDamageEvent {

	private CustomItem cItem;

	public PlayerCustomItemDamageEvent(PlayerItemDamageEvent event, CustomItem cItem) {
		super(event.getPlayer(), event.getItem(), event.getDamage());
		if (cItem.isUnbreakable()) {
			this.setDamage(0);
		}
		this.setCancelled(event.isCancelled());
		this.cItem = cItem;
	}

	public PlayerCustomItemDamageEvent(Player player, ItemStack what, int damage, CustomItem cItem) {
		super(player, what, damage);
		this.cItem = cItem;
	}

	public CustomItem getCustomItem() {
		return cItem;
	}
	
	public void setCustomItem(CustomItem cItem) {
		this.cItem = cItem;
	}
}
