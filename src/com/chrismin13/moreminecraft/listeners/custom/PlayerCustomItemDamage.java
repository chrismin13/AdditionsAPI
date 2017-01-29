package com.chrismin13.moreminecraft.listeners.custom;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.events.PlayerCustomItemBreakEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;
import com.chrismin13.moreminecraft.utils.NumberUtils;

public class PlayerCustomItemDamage implements Listener {

	@EventHandler
	public void onPlayerCustomItemDamage(PlayerCustomItemDamageEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		GameMode gm = player.getGameMode();
		if (gm != GameMode.SURVIVAL && gm != GameMode.ADVENTURE) {
			event.setCancelled(true);
			return;
		}
		int damage = event.getDamage();
		if (damage == 0) {
			event.setCancelled(true);
			return;
		}
		CustomItem cItem = event.getCustomItem();
		if (!cItem.hasFakeDurability()) {
			event.setCancelled(true);
			return;
		}
		ItemStack item = event.getItem();
		ItemMeta meta = item.getItemMeta();
		Map<Enchantment, Integer> enchantments = item.getEnchantments();
		int durability = CustomItemUtils.getCurrentFakeDurability(item);
		if (durability < 0) {
			player.getInventory().remove(item);
			return;
		}
		if (!item.containsEnchantment(Enchantment.DURABILITY)) {
			durability -= event.getDamage();
		} else if (NumberUtils.calculateChance(1 / ((double) item.getEnchantmentLevel(Enchantment.DURABILITY) + 1D))) {
			durability -= event.getDamage();
		}
		List<String> lore = cItem.getFullLore(enchantments, durability);
		meta.setLore(lore);
		item.setItemMeta(meta);
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

}
