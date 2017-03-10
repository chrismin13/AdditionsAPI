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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.moreminecraft.api.durability.ArmorDurability;
import com.chrismin13.moreminecraft.api.items.CustomArmor;
import com.chrismin13.moreminecraft.api.items.CustomItemStack;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.events.CustomShieldPlayerDamageByEntityEvent;
import com.chrismin13.moreminecraft.events.EntityDamageByPlayerUsingCustomItemEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomArmorDamageEvent;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class EntityDamageByEntity implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		// EntityDamageByCustomItemEvent
		Entity damager = event.getDamager();
		EntityType damagerType = damager.getType();
		if (damagerType == EntityType.PLAYER) {
			Player player = (Player) damager;
			ItemStack item = player.getInventory().getItemInMainHand();
			if (CustomItemUtils.isCustomItem(item)) {
				Bukkit.getServer().getPluginManager()
						.callEvent(new EntityDamageByPlayerUsingCustomItemEvent(event, new CustomItemStack(item)));
			}
		}

		Entity damagee = event.getEntity();
		EntityType damageeType = damagee.getType();
		if (damageeType == EntityType.PLAYER) {
			Player player = (Player) damagee;
			PlayerInventory inv = player.getInventory();
			ItemStack item = inv.getItemInMainHand();
			// CustomShieldPlayerDamageByEntityEvent
			if (ItemType.getItemType(item) != ItemType.SHIELD) {
				item = inv.getItemInOffHand();
				if (ItemType.getItemType(item) == ItemType.SHIELD)
					if (player.isBlocking() && CustomItemUtils.isCustomItem(item)) {
						Bukkit.getServer().getPluginManager()
								.callEvent(new CustomShieldPlayerDamageByEntityEvent(event, new CustomItemStack(item)));
					}
			}
			// PlayerCustomArmorDamageEvent
			for (ItemStack armor : inv.getArmorContents())
				if (armor != null && armor.getType() != Material.AIR && CustomItemUtils.isCustomItem(armor)
						&& CustomItemUtils.getCustomItem(armor) instanceof CustomArmor
						&& CustomItemUtils.getCustomItem(armor).getDurabilityMechanics() instanceof ArmorDurability) {
					CustomArmor cArmor = (CustomArmor) CustomItemUtils.getCustomItem(armor);
					Bukkit.getServer().getPluginManager().callEvent(
							new PlayerCustomArmorDamageEvent(player, armor, cArmor, DamageCause.ENTITY_ATTACK));

				}
		}
	}
}
