package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.api.CustomTool;
import com.chrismin13.moreminecraft.enums.ToolType;
import com.chrismin13.moreminecraft.events.EntityDamageByPlayerUsingCustomItemEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;

public class EntityDamageByPlayerUsingCustomItem implements Listener {

	@EventHandler
	public void onEntityDamageByCustomItem(EntityDamageByPlayerUsingCustomItemEvent event) {
		if (event.isCancelled())
			return;
		Player player = event.getPlayer();
		CustomItem cItem = event.getCustomItem();
		//ItemType type = cItem.getItemType();
		ItemStack item = event.getCustomItemStack().getItemStack();
		GameMode gm = player.getGameMode();
		if ((gm == GameMode.SURVIVAL || gm == GameMode.ADVENTURE) && cItem.hasFakeDurability()) {
			PlayerCustomItemDamageEvent damageEvent = new PlayerCustomItemDamageEvent(player, item, 0, cItem);
			if (cItem instanceof CustomTool) {
				ToolType toolType = ((CustomTool) cItem).getToolType();
				if (toolType == ToolType.AXE || toolType == ToolType.PICKAXE || toolType == ToolType.SPADE) {
					damageEvent.setDamage(2);
				} else if (toolType == ToolType.SWORD || toolType == ToolType.HOE) {
					damageEvent.setDamage(1);
				}
			}
			if (damageEvent.getDamage() != 0)
				Bukkit.getServer().getPluginManager().callEvent(damageEvent);
		}
	}

}
