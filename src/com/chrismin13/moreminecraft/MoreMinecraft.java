package com.chrismin13.moreminecraft;

import java.io.IOException;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.chrismin13.moreminecraft.events.MoreMinecraftAPIInitializationEvent;
import com.chrismin13.moreminecraft.files.ConfigFile;
import com.chrismin13.moreminecraft.files.DataFile;
import com.chrismin13.moreminecraft.listeners.custom.ArrowFromCustomBowHit;
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
import com.chrismin13.moreminecraft.listeners.vanilla.Anvil;
import com.chrismin13.moreminecraft.listeners.vanilla.BlockBreak;
import com.chrismin13.moreminecraft.listeners.vanilla.BlockIgnite;
import com.chrismin13.moreminecraft.listeners.vanilla.CraftingTable;
import com.chrismin13.moreminecraft.listeners.vanilla.EnchantItem;
import com.chrismin13.moreminecraft.listeners.vanilla.EntityDamageByEntity;
import com.chrismin13.moreminecraft.listeners.vanilla.EntityShootBow;
import com.chrismin13.moreminecraft.listeners.vanilla.EntityToggleGlide;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerFish;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerInteract;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerShearEntity;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;
import com.chrismin13.moreminecraft.utils.Debug;

import us.fihgu.toolbox.resourcepack.ResourcePackListener;
import us.fihgu.toolbox.resourcepack.ResourcePackManager;
import us.fihgu.toolbox.resourcepack.ResourcePackServer;

public class MoreMinecraft extends JavaPlugin {

	private static JavaPlugin instance;

	public void onEnable() {

		instance = this;

		ConfigFile.getInstance().setup();
		DataFile.getInstance().setup();

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new EnchantItem(), this);
		pm.registerEvents(new Anvil(), this);
		pm.registerEvents(new CraftingTable(), this);
		pm.registerEvents(new BlockBreak(), this);
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
		pm.registerEvents(new ArrowFromCustomBowHit(), this);
		new ResourcePackListener().register(this);
		// After all plugins have been enabled
		BukkitRunnable task = new BukkitRunnable() {
			@Override
			public void run() {
				
				Debug.say("Starting MoreMinecraftAPI Intialization");
				getServer().getPluginManager().callEvent(new MoreMinecraftAPIInitializationEvent());
				Debug.say("Finished Initialization.");
				Debug.saySuper("aaaaand chat spam too. :P");
				
				// TODO
				// save custom item id configuration
				//CustomItemManager.saveIdConfiguration();

				if (ResourcePackManager.hasResource()) {
					setupHTTPServer();
				}
			}
		};
		task.runTask(this);
	}
	
	public void onDisable() {
		ResourcePackServer.stopServer();
	}

	private void setupHTTPServer() {
		try {
			ResourcePackManager.Load();
			ResourcePackManager.buildResourcePack();
			System.out.println("Starting a http server for hosting resource pack.");
			ResourcePackServer.startServer();
			this.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static JavaPlugin getInstance() {
		return instance;
	}
}
