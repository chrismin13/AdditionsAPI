package com.chrismin13.moreminecraft.listeners.vanilla;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;

public class PlayerItemDamage implements Listener {

	@EventHandler
	public void onPlayerItemDamage(PlayerItemDamageEvent event) {
		if (event.getClass() == PlayerCustomItemDamageEvent.class)
			return;
	}
	
}