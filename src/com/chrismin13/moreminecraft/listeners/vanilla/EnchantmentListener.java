package com.chrismin13.moreminecraft.listeners.vanilla;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class EnchantmentListener implements Listener {

	@EventHandler
	public void onItemEnchant(EnchantItemEvent event) {
		if (event.isCancelled())
			return;
		ItemStack item = event.getItem();
		ItemMeta meta = item.getItemMeta();
		Map<Enchantment, Integer> eToAdd = event.getEnchantsToAdd();
		Set<Enchantment> eSet = eToAdd.keySet();
		List<Enchantment> e = new ArrayList<Enchantment>(eSet);
		if (!CustomItemUtils.isCustomItem(item)) {
			return;
		}
		CustomItem cItem = CustomItemUtils.getCustomItem(item);
		/*
		 * Non Enchantable Items
		 */
		if (!cItem.isEnchantable()) {
			event.setCancelled(true);
		} else {
			/*
			 * Forbidden Enchantments
			 */
			List<Enchantment> eInvalid = cItem.getForbidenEnchantments();
			for (Enchantment eToCheck : e) {
				if (eInvalid.contains(eToCheck)) {
					if (eToAdd.containsKey(eToCheck)) {
						eToAdd.remove(eToCheck);
					}
					if (eToAdd.isEmpty()) {
						event.setCancelled(true);
						return;
					}
				}
			}
		}
		if (item.containsEnchantment(Enchantment.DAMAGE_ALL)) {
			meta.setLore(cItem.getFullLore(eToAdd, CustomItemUtils.getCurrentFakeDurability(item)));
		}
		item.setItemMeta(meta);
	}
}
