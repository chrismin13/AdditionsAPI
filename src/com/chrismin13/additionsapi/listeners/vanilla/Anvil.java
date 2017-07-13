package com.chrismin13.additionsapi.listeners.vanilla;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomItemStack;

import net.md_5.bungee.api.ChatColor;

public class Anvil implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemRename(PrepareAnvilEvent event) {
		if (event.getResult() == null)
			return;
		ItemStack resultItem = event.getResult();
		if (!AdditionsAPI.isCustomItem(resultItem))
			return;
		AnvilInventory inv = event.getInventory();
		ItemMeta resultMeta = resultItem.getItemMeta();
		CustomItemStack cStack = new CustomItemStack(resultItem);
		CustomItem cItem = cStack.getCustomItem();

		ItemStack leftItem = inv.getItem(0);
		ItemMeta leftMeta = leftItem.getItemMeta();
		/*
		 * A fix for the bug that occurred due to the Display Name of the Custom
		 * Item having a ChatColor.RESET in front.
		 */
		if (cItem.getDisplayName() != null && resultMeta.getDisplayName() != null) {
			String renamedDisplayName = resultMeta.getDisplayName();
			if (cItem.getDisplayName() != renamedDisplayName
					&& leftMeta.getDisplayName().startsWith(ChatColor.RESET + "")) {
				if (renamedDisplayName.startsWith("r"))
					renamedDisplayName = renamedDisplayName.replaceFirst("r", "");
				resultMeta.setDisplayName(renamedDisplayName);

			}
		}
		/*
		 * A fix for being able to put books of any enchantment, even if it's
		 * forbidden
		 */
		if (!cItem.getForbidenEnchantments().isEmpty())
			for (Enchantment ench : cItem.getForbidenEnchantments())
				if (resultItem.containsEnchantment(ench))
					event.setResult(new ItemStack(Material.AIR));

		if (cItem.getDisplayName() != null) {
			String customName = cItem.getDisplayName();
			/*
			 * Fixes a bug that allowed you to rename the resultItem to it
			 * original material name
			 */
			if (resultMeta.getDisplayName() == null) {
				resultMeta.setDisplayName(customName);

			} else {
				/*
				 * TODO: A fix for the italic text even if you didn't change the
				 * name
				 */
			}
		}
		resultItem.setItemMeta(resultMeta);
		/*
		 * A fix for the fake lore not updating when adding Sharpness.
		 */
		cStack.updateLore();
	}
}
