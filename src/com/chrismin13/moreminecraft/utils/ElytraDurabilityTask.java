package com.chrismin13.moreminecraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.chrismin13.moreminecraft.api.CustomItem;
import com.chrismin13.moreminecraft.events.PlayerCustomItemDamageEvent;
import com.chrismin13.moreminecraft.listeners.custom.CustomElytraPlayerToggleGlide;

public class ElytraDurabilityTask extends BukkitRunnable {
	
    private Player player;
    private ItemStack elytra;
    private CustomItem cItem;
    private boolean justStarted = true;
    
    public ElytraDurabilityTask(Player player, ItemStack elytra, CustomItem cItem) {
        this.player = player;
        this.cItem = cItem;
        this.elytra = elytra;
    }

    @Override
    public void run() {
    	if (justStarted) {
    		justStarted = false;
    		return;
    	}
        if (player.isGliding()) {
        	Bukkit.getPluginManager().callEvent(new PlayerCustomItemDamageEvent(player, elytra, 1, cItem));
        } else {
        	CustomElytraPlayerToggleGlide.cancelPlayerGlideDamage(player.getUniqueId());
        }
    }

}
