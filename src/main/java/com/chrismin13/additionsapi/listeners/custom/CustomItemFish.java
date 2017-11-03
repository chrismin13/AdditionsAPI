package com.chrismin13.additionsapi.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.durability.FishingRodDurability;
import com.chrismin13.additionsapi.events.item.CustomItemFishEvent;
import com.chrismin13.additionsapi.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.permissions.FishingRodPermissions;
import com.chrismin13.additionsapi.utils.PermissionUtils;

public class CustomItemFish implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCustomItemFish(CustomItemFishEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		CustomItem cItem = customEvent.getCustomItem();
		PlayerFishEvent event = customEvent.getPlayerFishEvent();
		Player player = event.getPlayer();
		if (cItem.getDurabilityMechanics() instanceof FishingRodDurability) {
			ItemStack item = customEvent.getCustomItemStack().getItemStack();
			State state = event.getState();
			FishingRodDurability mechanics = (FishingRodDurability) cItem.getDurabilityMechanics();
			PlayerCustomItemDamageEvent damageEvent = new PlayerCustomItemDamageEvent(player, item, 0, cItem);

			switch (state) {
			case CAUGHT_ENTITY:
				if (event.getCaught().getType() == EntityType.DROPPED_ITEM) {
					damageEvent.setDamage(mechanics.getItemReel());
				} else {
					damageEvent.setDamage(mechanics.getEntityReel());
				}
				break;
			case CAUGHT_FISH:
				damageEvent.setDamage(mechanics.getFishCatch());
				break;
			case IN_GROUND:
				damageEvent.setDamage(mechanics.getBlockReel());
				break;
			default:
				break;
			}

			if (damageEvent.getDamage() != 0)
				Bukkit.getServer().getPluginManager().callEvent(damageEvent);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onCustomItemFishLowest(CustomItemFishEvent customEvent) {
		if (customEvent.isCancelled())
			return;

		CustomItem cItem = customEvent.getCustomItem();

		if (!(cItem.getPermissions() instanceof FishingRodPermissions))
			return;
		FishingRodPermissions perm = (FishingRodPermissions) cItem.getPermissions();

		PlayerFishEvent event = customEvent.getPlayerFishEvent();

		if (!PermissionUtils.allowedAction(event.getPlayer(), perm.getType(), perm.getReel()))
			event.setCancelled(true);
	}

}
