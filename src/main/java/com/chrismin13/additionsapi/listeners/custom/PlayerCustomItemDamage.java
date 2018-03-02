package com.chrismin13.additionsapi.listeners.custom;

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

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.events.item.PlayerCustomItemBreakEvent;
import com.chrismin13.additionsapi.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomItemStack;

import com.chrismin13.additionsapi.utils.NumberUtils;

public class PlayerCustomItemDamage implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCustomItemDamage(PlayerCustomItemDamageEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		/*
		 * Somehow, someone got an NPE here. No idea how, but let's just prevent it from
		 * happening in the future. They were using PaperSpigot so it's probably
		 * something to do with that. Or I messed something up elsewhere xD
		 * 
		 * EDIT: I messed up - it's treefeller that dropped the axe's durability to
		 * below 0, it got removed and thus got nulled. Same for sickles.
		 */
		if (event.getCustomItem() == null)
			return;

		CustomItem cItem = event.getCustomItem();
		ItemStack item = event.getItem();
		CustomItemStack cStack = new CustomItemStack(item);
		int durability = 0;
		if (cItem.hasFakeDurability())
			durability = cStack.getFakeDurability();
		else
			durability = item.getType().getMaxDurability() - item.getDurability();
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
			item.setDurability((short) (item.getType().getMaxDurability() - durability));
		}
		if (durability < 0) {
			PlayerCustomItemBreakEvent breakEvent = new PlayerCustomItemBreakEvent(player, item, cItem);
			Bukkit.getPluginManager().callEvent(breakEvent);
			if (!event.isCancelled()) {
				item.setAmount(0);
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
		if (AdditionsAPI.isCustomItem(event.getItem()) && !(event instanceof PlayerCustomItemDamageEvent))
			event.setCancelled(true);
	}

}
