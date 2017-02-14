package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.CustomItemStack;
import com.chrismin13.moreminecraft.events.CustomItemBlockBreakEvent;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;

public class BlockBreak implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (CustomItemUtils.isCustomItem(item)) {
			CustomItemBlockBreakEvent customEvent = new CustomItemBlockBreakEvent(event,
					new CustomItemStack(item));
			Bukkit.getServer().getPluginManager().callEvent(customEvent);
		}
	}

}
