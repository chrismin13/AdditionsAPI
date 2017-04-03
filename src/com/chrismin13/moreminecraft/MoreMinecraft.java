package com.chrismin13.moreminecraft;

import java.io.IOException;
import java.util.Arrays;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.chrismin13.moreminecraft.events.MoreMinecraftAPIInitializationEvent;
import com.chrismin13.moreminecraft.files.ConfigFile;
import com.chrismin13.moreminecraft.files.DataFile;
import com.chrismin13.moreminecraft.files.LangFile;
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
import com.chrismin13.moreminecraft.listeners.vanilla.EntityDamage;
import com.chrismin13.moreminecraft.listeners.vanilla.EntityShootBow;
import com.chrismin13.moreminecraft.listeners.vanilla.EntityToggleGlide;
import com.chrismin13.moreminecraft.listeners.vanilla.PlayerDeath;
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

		ConfigFile config = ConfigFile.getInstance();
		config.setup();
		if (config.getConfig().getBoolean("resource-pack.force-on-join"))
			ResourcePackManager.setForceResourcePack();
		
		DataFile.getInstance().setup();
		
		LangFile lang = LangFile.getInstance();
		lang.setup();
		String pluginName = "more_minecraft";
		lang.addEntry(pluginName, "sword", "Sword");
		lang.addEntry(pluginName, "axe", "Axe");
		lang.addEntry(pluginName, "picakaxe", "Pickaxe");
		lang.addEntry(pluginName, "spade", "Shovel");
		lang.addEntry(pluginName, "hoe", "Hoe");
		lang.addEntry(pluginName, "helmet", "Helmet");
		lang.addEntry(pluginName, "chestplate", "Chestplate");
		lang.addEntry(pluginName, "leggings", "Leggings");
		lang.addEntry(pluginName, "boots", "Boots");
		lang.addEntry(pluginName, "leather_helmet", "Cap");
		lang.addEntry(pluginName, "leather_chestplate", "Tunic");
		lang.addEntry(pluginName, "leather_leggings", "Pants");
		lang.addEntry(pluginName, "leather_boots", "Boots");
		lang.addEntry(pluginName, "attack_main_hand", "When in main hand:");
		lang.addEntry(pluginName, "attack_speed", "Attack Speed");
		lang.addEntry(pluginName, "attack_damage", "Attack Damage");
		lang.addEntry(pluginName, "durability", "Durability:");
		lang.addEntry(pluginName, "death_message", " using [CustomItem]");
		lang.addEntry(pluginName, "resource_pack_kick", "You must accept the resource pack in order to join the server! Click on the server once, then click edit and change Server Resource pack to True.");
		lang.saveData();

		for (Listener listener : Arrays.asList(new EnchantItem(), new Anvil(), new CraftingTable(), new BlockBreak(),
				new CustomItemBlockBreak(), new EntityDamage(), new EntityDamageByPlayerUsingCustomItem(),
				new PlayerCustomItemDamage(), new PlayerInteract(), new CustomItemPlayerInteract(),
				new PlayerShearEntity(), new CustomItemShearEntity(), new PlayerFish(), new CustomItemFish(),
				new BlockIgnite(), new CustomItemBlockIgnite(), new EntityShootBow(), new EntityShootCustomBow(),
				new CustomShieldEntityDamageByEntity(), new EntityToggleGlide(), new CustomElytraPlayerToggleGlide(),
				new CustomItemUtils(), new ArrowFromCustomBowHit(), new PlayerDeath())) {
			getServer().getPluginManager().registerEvents(listener, this);
		}
		
		new ResourcePackListener().register(this);

		// After all plugins have been enabled
		getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
			Debug.say("Starting MoreMinecraftAPI Intialization");
			getServer().getPluginManager().callEvent(new MoreMinecraftAPIInitializationEvent());
			Debug.say("Finished Initialization.");
			Debug.saySuper("aaaaand chat spam too. :P");

			if (ResourcePackManager.hasResource()) {
				setupHTTPServer();
			}
		});
	}

	public void onDisable() {
		ResourcePackServer.stopServer();
	}

	private void setupHTTPServer() {
		try {
			ResourcePackManager.Load();
			ResourcePackManager.buildResourcePack();
			Debug.sayTrue("Starting an HTTP Server for hosting the Resource Pack.");
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
