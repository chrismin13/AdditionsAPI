package com.chrismin13.additionsapi.listeners.vanilla;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomItemStack;

/**
 * 
 * @author chrismin13
 *
 */
public class Experience implements Listener {

	Random random = new Random();

	/**
	 * This manages the Mending Enchantment for Custom Items. Surprisingly, this
	 * does work perfectly as vanilla might pick the item with Mending and
	 * Unbreakable but not apply the durability change as it is unbreakable and
	 * instead give the levels to the player, which we can use here! :D This is
	 * why I'm only using Unbreakable items and not all items with Mending and
	 * Fake Durability and it's also why this is hard coded, and there's no way
	 * to change the modifier.
	 * 
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	public void onExpChange(PlayerExpChangeEvent event) {
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
		ArrayList<ItemStack> itemsToCheck = new ArrayList<>();

		for (ItemStack item : inv.getArmorContents())
			itemsToCheck.add(item);
		itemsToCheck.add(inv.getItemInMainHand());
		itemsToCheck.add(inv.getItemInOffHand());

		ArrayList<CustomItemStack> cStacksWithMending = new ArrayList<>();
		for (ItemStack item : itemsToCheck)
			if (AdditionsAPI.isCustomItem(item)) {
				CustomItemStack cStack = new CustomItemStack(item);
				CustomItem cItem = cStack.getCustomItem();
				if (cStack.getItemStack().containsEnchantment(Enchantment.MENDING) && cItem.hasFakeDurability()
						&& cItem.isUnbreakable())
					cStacksWithMending.add(cStack);
			}

		if (!cStacksWithMending.isEmpty()) {
			int xp = event.getAmount();
			CustomItemStack cStack = cStacksWithMending.get(random.nextInt(cStacksWithMending.size()));
			int durabilityPointsAvailable = xp * 2;
			int currentDurability = cStack.getFakeDurability();
			int maxDurability = cStack.getMaxFakeDurability();
			if (maxDurability > currentDurability) {
				int finalDurability = currentDurability + durabilityPointsAvailable;
				int finalXp = 0;
				if (finalDurability > maxDurability) {
					finalXp = (finalDurability - maxDurability) / 2;
					finalDurability = maxDurability;
				}
				/*
				 * Gotta set the durbility and not reduce it negatively as that
				 * would take into consideration the unbreaking enchantment.
				 */
				cStack.setFakeDurability(finalDurability);
				event.setAmount(finalXp);
			}
		}
	}

}
