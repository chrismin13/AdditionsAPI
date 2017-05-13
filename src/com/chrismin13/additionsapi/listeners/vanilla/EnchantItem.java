package com.chrismin13.additionsapi.listeners.vanilla;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomItemStack;

public class EnchantItem implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemEnchant(EnchantItemEvent event) {
		if (event.isCancelled() || !AdditionsAPI.isCustomItem(event.getItem()))
			return;
		ItemStack item = event.getItem();
		CustomItemStack cStack = new CustomItemStack(item);
		Map<Enchantment, Integer> eToAdd = event.getEnchantsToAdd();
		Set<Enchantment> eSet = eToAdd.keySet();
		List<Enchantment> e = new ArrayList<Enchantment>(eSet);
		CustomItem cItem = cStack.getCustomItem();
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
			cStack.updateLore();
		}
	}
}
