package com.chrismin13.moreminecraft.listeners.custom;

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

import com.chrismin13.moreminecraft.durability.HoeDurability;
import com.chrismin13.moreminecraft.durability.ItemDurability;
import com.chrismin13.moreminecraft.durability.SpadeDurability;
import com.chrismin13.moreminecraft.events.item.CustomItemPlayerInteractEvent;
import com.chrismin13.moreminecraft.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.moreminecraft.items.CustomItem;

public class CustomItemPlayerInteract implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCustomItemPlayerInteract(CustomItemPlayerInteractEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		PlayerInteractEvent event = customEvent.getPlayerInteractEvent();
		Action action = event.getAction();
		CustomItem cItem = customEvent.getCustomItem();
		ItemStack item = customEvent.getCustomItemStack().getItemStack();
		Player player = event.getPlayer();
		PlayerCustomItemDamageEvent damageEvent = new PlayerCustomItemDamageEvent(player, item, 0, cItem);
		if (action == Action.RIGHT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			Material material = block.getType();
			Location blockLoc = block.getLocation();
			blockLoc.setY(blockLoc.getBlockY() + 1);
			Material materialUp = blockLoc.getBlock().getType();
			@SuppressWarnings("deprecation")
			byte data = block.getData();
			ItemDurability mechanics = cItem.getDurabilityMechanics();

			BlockFace face = event.getBlockFace();
			if (materialUp == Material.AIR && (face == BlockFace.UP || face == BlockFace.EAST || face == BlockFace.NORTH
					|| face == BlockFace.SOUTH || face == BlockFace.WEST)) {
				if (mechanics instanceof SpadeDurability && material == Material.GRASS) {
					damageEvent.setDamage(((SpadeDurability) mechanics).getPathTile());
				} else if (mechanics instanceof HoeDurability
						&& (material == Material.GRASS || (material == Material.DIRT && data != (byte) 2))) {
					damageEvent.setDamage(((HoeDurability) mechanics).getHoe());
				}
			}
		}
		if (damageEvent.getDamage() != 0)
			Bukkit.getServer().getPluginManager().callEvent(damageEvent);
	}
}
