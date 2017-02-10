package com.chrismin13.moreminecraft;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.moreminecraft.events.MoreMinecraftAPIInitializationEvent;
import com.chrismin13.moreminecraft.files.ConfigFile;
import com.chrismin13.moreminecraft.listeners.custom.CustomElytraPlayerToggleGlide;
import com.chrismin13.moreminecraft.listeners.custom.CustomItemBlockBreak;
import com.chrismin13.moreminecraft.listeners.custom.CustomItemBlockIgnite;
import com.chrismin13.moreminecraft.listeners.custom.CustomItemFish;
import com.chrismin13.moreminecraft.listeners.custom.CustomItemPlayerInteract;
import com.chrismin13.moreminecraft.listeners.custom.CustomItemShearEntity;
import com.chrismin13.moreminecraft.listeners.custom.CustomShieldEntityDamageByEntity;
import com.chrismin13.moreminecraft.listeners.custom.EntityDamageByPlayerUsingCustomItem;
import com.chrismin13.moreminecraft.listeners.custom.EntityShootCustomBow;
import com.chrismin13.moreminecraft.listeners.custom.PlayerCustomItemDamage;
import com.chrismin13.moreminecraft.listeners.vanilla.AnvilEvent;
import com.chrismin13.moreminecraft.listeners.vanilla.BlockBreak;
import com.chrismin13.moreminecraft.listeners.vanilla.BlockIgnite;
import com.chrismin13.moreminecraft.listeners.vanilla.CraftingTableEvent;
import com.chrismin13.moreminecraft.listeners.vanilla.EnchantmentListener;
import com.chrismin13.moreminecraft.listeners.vanilla.EntityDamageByEntity;
import com.chrismin13.moreminecraft.listeners.vanilla.EntityShootBow;
import com.chrismin13.moreminecraft.listeners.vanilla.EntityToggleGlide;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerFish;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerInteract;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerItemDamage;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerShearEntity;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;
import com.chrismin13.moreminecraft.utils.Debug;

public class MoreMinecraft extends JavaPlugin {

	private static JavaPlugin instance;

	public void onEnable() {

		instance = this;

		ConfigFile.getInstance().setup();
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new EnchantmentListener(), this);
		pm.registerEvents(new AnvilEvent(), this);
		pm.registerEvents(new CraftingTableEvent(), this);
		pm.registerEvents(new BlockBreak(), this);
		pm.registerEvents(new PlayerItemDamage(), this);
		pm.registerEvents(new CustomItemBlockBreak(), this);
		pm.registerEvents(new EntityDamageByEntity(), this);
		pm.registerEvents(new EntityDamageByPlayerUsingCustomItem(), this);
		pm.registerEvents(new PlayerCustomItemDamage(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new CustomItemPlayerInteract(), this);
		pm.registerEvents(new PlayerShearEntity(), this);
		pm.registerEvents(new CustomItemShearEntity(), this);
		pm.registerEvents(new PlayerFish(), this);
		pm.registerEvents(new CustomItemFish(), this);
		pm.registerEvents(new BlockIgnite(), this);
		pm.registerEvents(new CustomItemBlockIgnite(), this);
		pm.registerEvents(new EntityShootBow(), this);
		pm.registerEvents(new EntityShootCustomBow(), this);
		pm.registerEvents(new CustomShieldEntityDamageByEntity(), this);
		pm.registerEvents(new EntityToggleGlide(), this);
		pm.registerEvents(new CustomElytraPlayerToggleGlide(), this);
		pm.registerEvents(new CustomItemUtils(), this);
		
		// TODO: Find a better way of doing this.
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				Debug.say("Starting MoreMinecraftAPI Intialization");
				getServer().getPluginManager().callEvent(new MoreMinecraftAPIInitializationEvent());
				Debug.say("Finished Initialization.");
				Debug.saySuper("aaaaand chat spam. :P");
			}
		}, 50L);
	}

	public void onDisable() {

	}

	public static JavaPlugin getInstance() {
		return instance;
	}
}
