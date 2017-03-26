package com.chrismin13.moreminecraft.listeners.custom;

import java.util.List;
import java.util.Map;

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
import org.bukkit.inventory.meta.ItemMeta;

import com.chrismin13.moreminecraft.api.items.CustomItem;
import com.chrismin13.moreminecraft.events.PlayerCustomItemBreakEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;
import com.chrismin13.moreminecraft.utils.NumberUtils;

public class PlayerCustomItemDamage implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerCustomItemDamage(PlayerCustomItemDamageEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		CustomItem cItem = event.getCustomItem();
		ItemStack item = event.getItem();
		ItemMeta meta = item.getItemMeta();
		Map<Enchantment, Integer> enchantments = item.getEnchantments();
		int durability = 0;
		if (cItem.hasFakeDurability())
			durability = CustomItemUtils.getCurrentFakeDurability(item);
		else
			durability = item.getDurability();
		// TODO: Add the randomness factor for every damage taken
		if (!item.containsEnchantment(Enchantment.DURABILITY)) {
			durability -= event.getDamage();
		} else if (NumberUtils.calculateChance(1 / ((double) item.getEnchantmentLevel(Enchantment.DURABILITY) + 1D))) {
			durability -= event.getDamage();
		}
		if (cItem.hasFakeDurability()) {
			List<String> lore = cItem.getFullLore(enchantments, durability);
			meta.setLore(lore);
			item.setItemMeta(meta);
		} else if (!item.getItemMeta().spigot().isUnbreakable()) {
			item.setDurability((short) durability);
		}
		if (durability < 0) {
			PlayerCustomItemBreakEvent breakEvent = new PlayerCustomItemBreakEvent(player, item, cItem);
			Bukkit.getPluginManager().callEvent(breakEvent);
			if (!event.isCancelled()) {
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
