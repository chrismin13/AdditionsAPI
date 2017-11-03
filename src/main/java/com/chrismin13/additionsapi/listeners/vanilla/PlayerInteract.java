package com.chrismin13.additionsapi.listeners.vanilla;

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
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.enums.ToolType;
import com.chrismin13.additionsapi.events.item.CustomItemPlayerInteractEvent;
import com.chrismin13.additionsapi.items.CustomItemStack;
import com.chrismin13.additionsapi.permissions.HoePermissions;
import com.chrismin13.additionsapi.permissions.ItemPermissions;
import com.chrismin13.additionsapi.utils.PermissionUtils;

public class PlayerInteract implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
		ItemStack item = inv.getItemInMainHand();
		if (event.getHand() != null)
			switch (event.getHand()) {
			case OFF_HAND:
				item = inv.getItemInOffHand();
				break;
			default:
				break;
			}

		if (AdditionsAPI.isCustomItem(item)) {
			CustomItemPlayerInteractEvent customEvent = new CustomItemPlayerInteractEvent(event,
					new CustomItemStack(item));
			Bukkit.getServer().getPluginManager().callEvent(customEvent);
		}
		if (event.getAction() != null && event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& ToolType.getToolType(item.getType()) != null
				&& ToolType.getToolType(item.getType()).equals(ToolType.HOE)) {
			Block block = event.getClickedBlock();
			Material material = block.getType();
			Location blockLoc = block.getLocation();
			blockLoc.setY(blockLoc.getBlockY() + 1);
			Material materialUp = blockLoc.getBlock().getType();
			@SuppressWarnings("deprecation")
			byte data = block.getData();

			BlockFace face = event.getBlockFace();
			if (materialUp == Material.AIR && (face == BlockFace.UP || face == BlockFace.EAST || face == BlockFace.NORTH
					|| face == BlockFace.SOUTH || face == BlockFace.WEST))
				if (shouldPlaySound(material, item, data, player))
					player.playSound(block.getLocation(), "additionsapi.hoe.till", 1.0F, 1.0F);
		}
	}

	public static boolean shouldPlaySound(Material material, ItemStack item, byte data, Player player) {
		if ((material == Material.GRASS || (material == Material.DIRT && data != (byte) 2))) {
			if (!AdditionsAPI.isCustomItem(item))
				return true;
			CustomItemStack cStack = new CustomItemStack(item);
			ItemPermissions perm = cStack.getCustomItem().getPermissions();
			if (cStack.getCustomItem().hasHoeAbilities() && perm instanceof HoePermissions
					&& PermissionUtils.allowedAction(player, perm.getType(), ((HoePermissions) perm).getHoe()))
				return true;
		}
		return false;
	}

}
