package com.chrismin13.moreminecraft.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.durability.ItemDurability;
import com.chrismin13.moreminecraft.events.item.CustomItemBlockBreakEvent;
import com.chrismin13.moreminecraft.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.moreminecraft.items.CustomItem;
import com.chrismin13.moreminecraft.utils.MaterialUtils;

public class CustomItemBlockBreak implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCustomItemBlockBreak(CustomItemBlockBreakEvent event) {
		if (event.isCancelled())
			return;
		BlockBreakEvent breakEvent = event.getBlockBreakEvent();
		Player player = breakEvent.getPlayer();
		Block block = breakEvent.getBlock();
		Material material = block.getType();
		CustomItem cItem = event.getCustomItem();
		ItemStack item = event.getCustomItemStack().getItemStack();
		Boolean instantlyBreakable = MaterialUtils.isInstantlyBreakable(material);
		PlayerCustomItemDamageEvent damageEvent = new PlayerCustomItemDamageEvent(player, item, 0, cItem);
		ItemDurability mechanics = cItem.getDurabilityMechanics();
		
		if (instantlyBreakable) {
			damageEvent.setDamage(mechanics.getInstantBlockBreak());
		} else {
			damageEvent.setDamage(mechanics.getBlockBreak());
		}
		
		if (mechanics.getSpecialBlockDurability(material) != null) {
			damageEvent.setDamage(mechanics.getSpecialBlockDurability(material));
		}
		
		if (damageEvent.getDamage() != 0)
			Bukkit.getServer().getPluginManager().callEvent(damageEvent);
	}

}
