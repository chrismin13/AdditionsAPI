package com.chrismin13.additionsapi.listeners.custom;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.durability.ElytraDurability;
import com.chrismin13.additionsapi.events.elytra.CustomElytraPlayerToggleGlideEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.utils.ElytraDurabilityTask;

public class CustomElytraPlayerToggleGlide implements Listener {

	private static HashMap<UUID, Integer> playersGliding = new HashMap<UUID, Integer>();

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCustomElytraPlayerGlide(CustomElytraPlayerToggleGlideEvent customEvent) {
		if (customEvent.isCancelled())
			return;
		CustomItem cItem = customEvent.getCustomItem();
		Player player = customEvent.getPlayer();
		UUID playerUUID = player.getUniqueId();
		if (cItem.getDurabilityMechanics() instanceof ElytraDurability) {
			cancelPlayerGlideDamage(playerUUID);
			ElytraDurabilityTask task = new ElytraDurabilityTask(player, customEvent.getCustomItemStack().getItemStack(), cItem);
			task.runTaskTimer(AdditionsAPI.getInstance(), 0L, 20L);
			playersGliding.put(playerUUID, task.getTaskId());
		}
	}

	public static void cancelPlayerGlideDamage(UUID playerUUID) {
		if (playersGliding.containsKey(playerUUID)) {
			Bukkit.getScheduler().cancelTask(playersGliding.get(playerUUID));
			playersGliding.remove(playerUUID);
		}
	}

}
