package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.events.CustomShieldPlayerDamageByEntityEvent;
import com.chrismin13.moreminecraft.events.EntityDamageByPlayerUsingCustomItemEvent;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class EntityDamageByEntity implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		// EntityDamageByCustomItemEvent
		Entity damager = event.getDamager();
		EntityType damagerType = damager.getType();
		if (damagerType == EntityType.PLAYER) {
			Player player = (Player) damager;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (CustomItemUtils.isCustomItem(item)) {
				CustomItem cItem = CustomItemUtils.getCustomItem(item);
				Bukkit.getServer().getPluginManager()
						.callEvent(new EntityDamageByPlayerUsingCustomItemEvent(event, cItem));
			}
		}
		Entity damagee = event.getEntity();
		EntityType damageeType = damagee.getType();
		if (damageeType == EntityType.PLAYER) {
			Player player = (Player) damagee;
			PlayerInventory inv = player.getInventory();
			ItemStack item;
			if (inv.getItemInMainHand().getType() == Material.SHIELD) {
				item = inv.getItemInMainHand();
			} else if (inv.getItemInOffHand().getType() == Material.SHIELD) {
				item = inv.getItemInOffHand();
			} else {
				return;
			}
			if (player.isBlocking() && CustomItemUtils.isCustomItem(item)) {
				CustomItem cItem = CustomItemUtils.getCustomItem(item);
				Bukkit.getServer().getPluginManager().callEvent(new CustomShieldPlayerDamageByEntityEvent(event, cItem));
			}
		}
	}
}
