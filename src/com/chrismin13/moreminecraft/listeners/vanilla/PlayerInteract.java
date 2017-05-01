package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.chrismin13.moreminecraft.enums.ToolType;
import com.chrismin13.moreminecraft.events.item.CustomItemPlayerInteractEvent;
import com.chrismin13.moreminecraft.items.CustomItemStack;
import com.chrismin13.moreminecraft.items.CustomTool;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class PlayerInteract implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteract(PlayerInteractEvent event) {
		ItemStack item;
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
		if (event.getHand() != null) {
			switch (event.getHand()) {
			case HAND:
				item = inv.getItemInMainHand();
				break;
			case OFF_HAND:
				item = inv.getItemInOffHand();
				break;
			default:
				return;
			}
			if (item.getType() == Material.AIR || item == null)
				return;
			if (CustomItemUtils.isCustomItem(item)) {
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
				if (materialUp == Material.AIR && (face == BlockFace.UP || face == BlockFace.EAST
						|| face == BlockFace.NORTH || face == BlockFace.SOUTH || face == BlockFace.WEST)) {
					if (shouldPlaySound(material, item, data)) {
						player.playSound(block.getLocation(), "additionsapi.hoe.till", 1.0F, 1.0F);
					}
				}
			}
		}
	}

	public static boolean shouldPlaySound(Material material, ItemStack item, byte data) {
		if ((material == Material.GRASS || (material == Material.DIRT && data != (byte) 2))) {
			if (!CustomItemUtils.isValidCustomItem(item))
				return true;
			CustomItemStack cStack = new CustomItemStack(item);
			if (cStack.getCustomItem() instanceof CustomTool) {
				CustomTool cTool = (CustomTool) cStack.getCustomItem();
				if (cTool.keepsHoeAbilities())
					return true;
				else
					return false;
			} else {
				return true;
			}
		}
		return false;
	}

}
