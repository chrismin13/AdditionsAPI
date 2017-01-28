package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.events.CustomItemFishEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;

public class CustomItemFish implements Listener {

	@EventHandler
	public void onCustomItemFish(CustomItemFishEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		CustomItem cItem = customEvent.getCustomItem(); 
		PlayerFishEvent event = customEvent.getPlayerFishEvent();
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
		if (cItem.getItemType() == ItemType.FISHING_ROD) {
			ItemStack item;
			if (inv.getItemInMainHand().getType() == Material.FISHING_ROD) {
				item = inv.getItemInMainHand(); 
			} else if (inv.getItemInOffHand().getType() == Material.FISHING_ROD) {
				item = inv.getItemInOffHand();
			} else {
				return;
			}
			State state = event.getState();
			PlayerCustomItemDamageEvent damageEvent = new PlayerCustomItemDamageEvent(player, item, 0, cItem);
			switch(state) {
			case CAUGHT_ENTITY:
				if (event.getCaught().getType() == EntityType.DROPPED_ITEM) {
					damageEvent.setDamage(3);
				} else {
					damageEvent.setDamage(5);
				}
				break;
			case CAUGHT_FISH:
				damageEvent.setDamage(1);
				break;
			case IN_GROUND:
				damageEvent.setDamage(2);
				break;
			default:
				break;			
			}
			if (damageEvent.getDamage() != 0)
				Bukkit.getServer().getPluginManager().callEvent(damageEvent);
		}
	}
	
}
