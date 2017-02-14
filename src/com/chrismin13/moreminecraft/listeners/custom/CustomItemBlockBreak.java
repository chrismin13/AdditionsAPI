package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.api.CustomTool;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.enums.ToolType;
import com.chrismin13.moreminecraft.events.CustomItemBlockBreakEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;
import com.chrismin13.moreminecraft.utils.MaterialUtils;

public class CustomItemBlockBreak implements Listener {

	@EventHandler
	public void onCustomItemBlockBreak(CustomItemBlockBreakEvent event) {
		if (event.isCancelled())
			return;
		BlockBreakEvent breakEvent = event.getBlockBreakEvent();
		Player player = breakEvent.getPlayer();
		CustomItem cItem = event.getCustomItem();
		ItemType type = cItem.getItemType();
		ItemStack item = event.getCustomItemStack().getItemStack();
		Block block = breakEvent.getBlock();
		Material material = block.getType();
		Boolean instantlyBreakable = MaterialUtils.isInstantlyBreakable(material);
		if (cItem.hasFakeDurability()) {
			PlayerCustomItemDamageEvent damageEvent = new PlayerCustomItemDamageEvent(player, item, 0, cItem);
			if (cItem instanceof CustomTool) {
				ToolType toolType = ((CustomTool) cItem).getToolType();
				if (toolType == ToolType.AXE || toolType == ToolType.PICKAXE || toolType == ToolType.SPADE) {
					if (!instantlyBreakable)
						damageEvent.setDamage(1);
				} else if (toolType == ToolType.SWORD) {
					if (!instantlyBreakable)
						damageEvent.setDamage(2);
				}
			} else if (type == ItemType.SHEARS && MaterialUtils.willDamageShears(material)) {
				damageEvent.setDamage(1);
			}
			if (damageEvent.getDamage() != 0)
				Bukkit.getServer().getPluginManager().callEvent(damageEvent);
		}
	}

}
