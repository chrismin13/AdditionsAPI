package com.chrismin13.additionsapi.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.durability.HoeDurability;
import com.chrismin13.additionsapi.durability.ItemDurability;
import com.chrismin13.additionsapi.durability.SpadeDurability;
import com.chrismin13.additionsapi.enums.ToolType;
import com.chrismin13.additionsapi.events.item.CustomItemPlayerInteractEvent;
import com.chrismin13.additionsapi.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.permissions.HoePermissions;
import com.chrismin13.additionsapi.permissions.ItemPermissions;
import com.chrismin13.additionsapi.permissions.ShieldPermissions;
import com.chrismin13.additionsapi.permissions.SpadePermissions;
import com.chrismin13.additionsapi.utils.PermissionUtils;

public class CustomItemPlayerInteract implements Listener {

	String version = Bukkit.getServer().getVersion();

	@EventHandler
	public void onCustomItemPlayerInteract(CustomItemPlayerInteractEvent customEvent) {
		PlayerInteractEvent event = customEvent.getPlayerInteractEvent();
		Action action = event.getAction();

		CustomItem cItem = customEvent.getCustomItem();
		ItemStack item = customEvent.getCustomItemStack().getItemStack();

		Player player = event.getPlayer();

		PlayerCustomItemDamageEvent damageEvent = new PlayerCustomItemDamageEvent(player, item, 0, cItem);

		if (customEvent.isCancelled()) {
			/*
			 * TODO:
			 * For some crazy reason the event is always cancelled when using a Shield and
			 * Right Clicking Air. WTF SPIGOT. Even though the event is already cancelled,
			 * it still lowers the shield when you cancel it.
			 */
			if (action.equals(Action.RIGHT_CLICK_AIR)) {

				boolean check = true;

				switch (event.getHand()) {
				case OFF_HAND:
					if (player.getInventory().getItemInMainHand().getType().equals(Material.SHIELD))
						check = false;
				default:
					break;
				}

				if (check && cItem.getPermissions() instanceof ShieldPermissions) {
					ShieldPermissions perm = (ShieldPermissions) cItem.getPermissions();

					/*
					 * The isHandRaised method doesn't exist below 1.11, so shield blocking will not
					 * be blocked by permissions
					 */
					if (!(version.contains("1.9") || version.contains("1.10")) && !player.isHandRaised()
							&& !PermissionUtils.allowedAction(player, perm.getType(), perm.getBlock()))
						customEvent.setCancelled(true);
				}

			}
			return;
		}

		if (action == Action.RIGHT_CLICK_BLOCK) {

			// SHIELDS

			boolean check = true;

			switch (event.getHand()) {
			case OFF_HAND:
				if (player.getInventory().getItemInMainHand().getType().equals(Material.SHIELD))
					check = false;
			default:
				break;
			}

			if (check && cItem.getPermissions() instanceof ShieldPermissions) {
				ShieldPermissions perm = (ShieldPermissions) cItem.getPermissions();

				/*
				 * The isHandRaised method doesn't exist below 1.11, so shield blocking will not
				 * be blocked by permissions
				 */
				if ((version.contains("1.9") || version.contains("1.10")) && !player.isHandRaised()
						&& !PermissionUtils.allowedAction(player, perm.getType(), perm.getBlock()))
					customEvent.setCancelled(true);
			}

			// HOES AND SHOVELS

			Block block = event.getClickedBlock();

			Material material = block.getType();

			Location blockLoc = block.getLocation();
			blockLoc.setY(blockLoc.getBlockY() + 1);
			Material materialUp = blockLoc.getBlock().getType();

			@SuppressWarnings("deprecation")
			byte data = block.getData();

			ItemDurability mechanics = cItem.getDurabilityMechanics();
			ItemPermissions perm = cItem.getPermissions();

			BlockFace face = event.getBlockFace();

			if (materialUp == Material.AIR && (face == BlockFace.UP || face == BlockFace.EAST || face == BlockFace.NORTH
					|| face == BlockFace.SOUTH || face == BlockFace.WEST)) {

				if (mechanics instanceof SpadeDurability && material == Material.GRASS) {

					if (perm instanceof SpadePermissions && PermissionUtils.allowedAction(player, perm.getType(),
							((SpadePermissions) perm).getTile()))
						damageEvent.setDamage(((SpadeDurability) mechanics).getPathTile());
					else
						event.setCancelled(true);
				} else if ((material == Material.GRASS || material == Material.GRASS_PATH
						|| (material == Material.DIRT && data != (byte) 2))) {

					if (ToolType.getToolType(cItem.getMaterial()) == ToolType.HOE
							&& (!cItem.hasHoeAbilities() || !(perm instanceof HoePermissions && PermissionUtils
									.allowedAction(player, perm.getType(), ((HoePermissions) perm).getHoe()))))
						event.setCancelled(true);
					else if (mechanics instanceof HoeDurability)
						damageEvent.setDamage(((HoeDurability) mechanics).getHoe());
				}

			}
		}

		if (damageEvent.getDamage() != 0)
			Bukkit.getServer().getPluginManager().callEvent(damageEvent);
	}
}
