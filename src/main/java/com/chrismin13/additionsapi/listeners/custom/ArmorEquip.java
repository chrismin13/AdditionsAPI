package com.chrismin13.additionsapi.listeners.custom;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomItemStack;
import com.chrismin13.additionsapi.permissions.ArmorPermissions;
import com.chrismin13.additionsapi.utils.PermissionUtils;
import com.codingforcookies.armorequip.ArmorEquipEvent;

public class ArmorEquip implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onArmorEquip(ArmorEquipEvent event) {
		if (event.isCancelled())
			return;

		if (event.getNewArmorPiece() == null)
			return;
		ItemStack item = event.getNewArmorPiece();

		if (!AdditionsAPI.isCustomItem(item))
			return;
		CustomItemStack cStack = new CustomItemStack(item);
		CustomItem cItem = cStack.getCustomItem();

		if (!(cItem.getPermissions() instanceof ArmorPermissions))
			return;
		ArmorPermissions perm = (ArmorPermissions) cItem.getPermissions();

		if (!PermissionUtils.allowedAction(event.getPlayer(), perm.getType(), perm.getWear()))
			event.setCancelled(true);
	}

}
