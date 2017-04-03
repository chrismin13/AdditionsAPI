package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.events.item.PlayerCustomItemBreakEvent;
import com.chrismin13.moreminecraft.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.moreminecraft.items.CustomItem;
import com.chrismin13.moreminecraft.items.CustomItemStack;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;
import com.chrismin13.moreminecraft.utils.NumberUtils;

public class PlayerCustomItemDamage implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCustomItemDamage(PlayerCustomItemDamageEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		CustomItem cItem = event.getCustomItem();
		ItemStack item = event.getItem();
		CustomItemStack cStack = new CustomItemStack(item);
		int durability = 0;
		if (cItem.hasFakeDurability())
			durability = cStack.getFakeDurability();
		else
			durability = item.getDurability();
		// TODO: Add support for mending.
		// TODO: Check if you can modify the durability for items that are not
		// unbreakable and with Fake Durability.
		if (!item.containsEnchantment(Enchantment.DURABILITY)) {
			durability -= event.getDamage();
		} else {
			for (int i = 1; i <= event.getDamage(); i++) {
				if (NumberUtils.calculateChance(1 / ((double) item.getEnchantmentLevel(Enchantment.DURABILITY) + 1D))) {
					durability -= 1;
				}
			}
		}
		if (cItem.hasFakeDurability()) {
			cStack.setFakeDurability(durability);
		} else if (!cItem.isUnbreakable()) {
			item.setDurability((short) durability);
		}
		if (durability < 0) {
			PlayerCustomItemBreakEvent breakEvent = new PlayerCustomItemBreakEvent(player, item, cItem);
			Bukkit.getPluginManager().callEvent(breakEvent);
			if (!event.isCancelled()) {
				// TODO: Add support for Armor
				player.getInventory().remove(item);
				player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1F, 1F);
			}
			return;
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void gamemodeCheck(PlayerCustomItemDamageEvent event) {
		GameMode gm = event.getPlayer().getGameMode();
		if (gm != GameMode.SURVIVAL && gm != GameMode.ADVENTURE) {
			event.setCancelled(true);
		}
		if (event.getDamage() == 0) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void customItemCheck(PlayerItemDamageEvent event) {
		if (CustomItemUtils.isCustomItem(event.getItem()) && !(event instanceof PlayerCustomItemDamageEvent))
			event.setCancelled(true);
	}

}
