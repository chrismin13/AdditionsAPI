package com.chrismin13.moreminecraft.listeners.custom;

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

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.api.CustomTool;
import com.chrismin13.moreminecraft.enums.ToolType;
import com.chrismin13.moreminecraft.events.CustomItemPlayerInteractEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;

public class CustomItemPlayerInteract implements Listener {

	@EventHandler
	public void onCustomItemPlayerInteract(CustomItemPlayerInteractEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		PlayerInteractEvent event = customEvent.getPlayerInteractEvent();
		Action action = event.getAction();
		CustomItem cItem = customEvent.getCustomItem();
		//ItemType type = cItem.getItemType();
		ItemStack item;
		Player player = event.getPlayer();
		PlayerInventory inv = player.getInventory();
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
		PlayerCustomItemDamageEvent damageEvent = new PlayerCustomItemDamageEvent(player, item, 0, cItem);
		if (action == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			Material material = block.getType();
			Location blockLoc = block.getLocation();
			blockLoc.setY(blockLoc.getBlockY() + 1);
			Material materialUp = blockLoc.getBlock().getType();
			@SuppressWarnings("deprecation")
			byte data = block.getData();

			BlockFace face = event.getBlockFace();
			if (materialUp == Material.AIR && (face == BlockFace.UP || face == BlockFace.EAST || face == BlockFace.NORTH
					|| face == BlockFace.SOUTH || face == BlockFace.WEST)) {
				if (cItem instanceof CustomTool) {
					ToolType toolType = ((CustomTool) cItem).getToolType();
					if (toolType == ToolType.SPADE && material == Material.GRASS) {
						damageEvent.setDamage(1);
					} else if (toolType == ToolType.HOE
							&& (material == Material.GRASS || (material == Material.DIRT && data != (byte) 2))) {
						damageEvent.setDamage(1);
					}
				}
			}
		}
		if (damageEvent.getDamage() != 0)
			Bukkit.getServer().getPluginManager().callEvent(damageEvent);
	}

}
