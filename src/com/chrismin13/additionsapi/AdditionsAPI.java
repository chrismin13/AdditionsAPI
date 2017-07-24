package com.chrismin13.additionsapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.additionsapi.commands.Additions;
import com.chrismin13.additionsapi.events.AdditionsAPIInitializationEvent;
import com.chrismin13.additionsapi.files.ConfigFile;
import com.chrismin13.additionsapi.files.DataFile;
import com.chrismin13.additionsapi.files.LangFile;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomItemStack;
import com.chrismin13.additionsapi.items.StorageCustomItem;
import com.chrismin13.additionsapi.listeners.DurabilityBar;
import com.chrismin13.additionsapi.listeners.mcMMO;
import com.chrismin13.additionsapi.listeners.custom.ArrowFromCustomBowHit;
import com.chrismin13.additionsapi.listeners.custom.CustomElytraPlayerToggleGlide;
import com.chrismin13.additionsapi.listeners.custom.CustomItemBlockBreak;
import com.chrismin13.additionsapi.listeners.custom.CustomItemBlockIgnite;
import com.chrismin13.additionsapi.listeners.custom.CustomItemFish;
import com.chrismin13.additionsapi.listeners.custom.CustomItemFurnaceBurn;
import com.chrismin13.additionsapi.listeners.custom.CustomItemPlayerInteract;
import com.chrismin13.additionsapi.listeners.custom.CustomItemShearEntity;
import com.chrismin13.additionsapi.listeners.custom.CustomShieldEntityDamageByEntity;
import com.chrismin13.additionsapi.listeners.custom.EntityDamageByPlayerUsingCustomItem;
import com.chrismin13.additionsapi.listeners.custom.EntityShootCustomBow;
import com.chrismin13.additionsapi.listeners.custom.PlayerCustomItemDamage;
import com.chrismin13.additionsapi.listeners.vanilla.Anvil;
import com.chrismin13.additionsapi.listeners.vanilla.BlockBreak;
import com.chrismin13.additionsapi.listeners.vanilla.BlockIgnite;
import com.chrismin13.additionsapi.listeners.vanilla.CraftingTable;
import com.chrismin13.additionsapi.listeners.vanilla.EnchantItem;
import com.chrismin13.additionsapi.listeners.vanilla.EntityDamage;
import com.chrismin13.additionsapi.listeners.vanilla.EntityShootBow;
import com.chrismin13.additionsapi.listeners.vanilla.EntityToggleGlide;
import com.chrismin13.additionsapi.listeners.vanilla.Experience;
import com.chrismin13.additionsapi.listeners.vanilla.FurnaceBurn;
import com.chrismin13.additionsapi.listeners.vanilla.PlayerDeath;
import com.chrismin13.additionsapi.listeners.vanilla.PlayerFish;
import com.chrismin13.additionsapi.listeners.vanilla.PlayerInteract;
import com.chrismin13.additionsapi.listeners.vanilla.PlayerShearEntity;
import com.chrismin13.additionsapi.recipes.CustomRecipe;
import com.chrismin13.additionsapi.utils.Debug;
import com.comphenix.attribute.NbtFactory;
import com.comphenix.attribute.NbtFactory.NbtCompound;
import com.google.common.collect.ImmutableList;

import us.fihgu.toolbox.item.ModelInjector;
import us.fihgu.toolbox.resourcepack.ResourcePackListener;
import us.fihgu.toolbox.resourcepack.ResourcePackManager;
import us.fihgu.toolbox.resourcepack.ResourcePackServer;

public class AdditionsAPI extends JavaPlugin implements Listener {

	private static JavaPlugin instance;

	public void onEnable() {

		instance = this;

		// Initializing Config
		ConfigFile config = ConfigFile.getInstance();
		config.setup();
		if (config.getConfig().getBoolean("resource-pack.force-on-join"))
			ResourcePackManager.setForceResourcePack();

		// Initializing Data File
		DataFile.getInstance().setup();

		// Initializing Lang File and adding all entries
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
		lang.addEntry(pluginName, "when_in_head", "When in head:");
		lang.addEntry(pluginName, "when_in_body", "When in body:");
		lang.addEntry(pluginName, "when_in_legs", "When in legs:");
		lang.addEntry(pluginName, "when_in_feet", "When in feet:");
		lang.addEntry(pluginName, "when_in_main_hand", "When in main hand:");
		lang.addEntry(pluginName, "when_in_off_hand", "When in off hand:");
		lang.addEntry(pluginName, "attribute_generic_attack_speed", "Attack Speed");
		lang.addEntry(pluginName, "attribute_generic_attack_damage", "Attack Damage");
		lang.addEntry(pluginName, "attribute_armor", "Armor");
		lang.addEntry(pluginName, "attribute_armor_toughness", "Armor Toughness");
		lang.addEntry(pluginName, "attribute_generic_follow_range", "Follow Range");
		lang.addEntry(pluginName, "attribute_generic_knockback_resistance", "Knockback Resistance");
		lang.addEntry(pluginName, "attribute_generic_luck", "Luck");
		lang.addEntry(pluginName, "attribute_generic_max_health", "Max Health");
		lang.addEntry(pluginName, "attribute_generic_movement_speed", "Speed");
		lang.addEntry(pluginName, "attribute_horse_jump_strength", "Horse Jump Strength");
		lang.addEntry(pluginName, "attribute_zombie_spawn_reinforcements", "Zombie Spawn Reinforcements");
		lang.addEntry(pluginName, "durability", "Durability:");
		lang.addEntry(pluginName, "death_message", " using [CustomItem]");
		lang.addEntry(pluginName, "resource_pack_kick",
				"You must accept the resource pack in order to join the server! Click on the server once, then click edit and change Server Resource pack to True.");
		lang.addEntry(pluginName, "item_durability_main_hand", "Item Durability in Main Hand: ");
		lang.addEntry(pluginName, "item_durability_off_hand", "Item Durability in Off Hand: ");

		lang.saveLang();

		// Registering listeners
		for (Listener listener : Arrays.asList(new EnchantItem(), new Anvil(), new CraftingTable(), new BlockBreak(),
				new CustomItemBlockBreak(), new EntityDamage(), new EntityDamageByPlayerUsingCustomItem(),
				new PlayerCustomItemDamage(), new PlayerInteract(), new CustomItemPlayerInteract(),
				new PlayerShearEntity(), new CustomItemShearEntity(), new PlayerFish(), new CustomItemFish(),
				new BlockIgnite(), new CustomItemBlockIgnite(), new EntityShootBow(), new EntityShootCustomBow(),
				new CustomShieldEntityDamageByEntity(), new EntityToggleGlide(), new CustomElytraPlayerToggleGlide(),
				this, new ArrowFromCustomBowHit(), new PlayerDeath(), new DurabilityBar(), new FurnaceBurn(),
				new CustomItemFurnaceBurn(), new ResourcePackListener(), new Experience())) {
			getServer().getPluginManager().registerEvents(listener, this);
		}

		// Custom mcMMO version with support for the Additions API.
		if (getServer().getPluginManager().getPlugin("mcMMO") != null) {
			/*
			 * Will try to register listener - might not work since mcMMO might
			 * not have the changes yet.
			 */
			try {
				for (Listener listener : Arrays.asList(new mcMMO())) {
					getServer().getPluginManager().registerEvents(listener, this);
				}
			} catch (Exception ignore) {
			}
		}

		// Commands
		getCommand("additions").setExecutor(new Additions());

		// After all plugins have been enabled
		getServer().getScheduler().scheduleSyncDelayedTask(this, () -> load());
	}

	public void onDisable() {
		// Useful when reloading!
		ResourcePackServer.stopServer();
		DurabilityBar.removeAllDurabilityBars();
	}

	private static void setupHTTPServer() {
		try {
			ResourcePackManager.Load();
			ResourcePackManager.buildResourcePack();
			Debug.sayTrue("Starting an HTTP Server for hosting the Resource Pack.");
			ResourcePackServer.startServer();
			instance.saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void load() {
		Debug.say("Starting AdditionsAPI Intialization");
		AdditionsAPIInitializationEvent event = new AdditionsAPIInitializationEvent();
		if (ConfigFile.getInstance().getConfig().getBoolean("resource-pack.force-on-join"))
			event.addResourcePackFromPlugin(instance, "resource/smooth_armor.zip");
		event.addResourcePackFromPlugin(instance, "resource/no_hoe_sound.zip");
		instance.getServer().getPluginManager().callEvent(event);
		Debug.say("Finished Initialization.");
		Debug.saySuper("aaaaand chat spam too. :P");

		if (ResourcePackManager.hasResource()) {
			setupHTTPServer();
			if (ResourcePackManager.neededRebuild) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					Bukkit.getScheduler().runTask(getInstance(), () -> {
						String link;
						if (player.getAddress().getHostString().equals("127.0.0.1")) {
							link = "http://" + ResourcePackServer.localhost + ":" + ResourcePackServer.port
									+ ResourcePackServer.path;
						} else {
							link = "http://" + ResourcePackServer.host + ":" + ResourcePackServer.port
									+ ResourcePackServer.path;
						}
						player.setResourcePack(link);
					});
				}
			}
		}
	}

	public static JavaPlugin getInstance() {
		return instance;
	}

	// === VARIABLES === //

	private static ImmutableList<CustomItemStack> cStacks;
	private static ImmutableList<CustomItem> cItems;

	// === INITIALIZATION === //

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInitialization(AdditionsAPIInitializationEvent event) {
		CustomItem[] cItems = event.getCustomItems();
		Debug.say("Recieved in total " + Integer.toString(cItems.length) + " Custom Items!");
		if (cItems.length == 0)
			return;
		List<CustomItemStack> cStacks = new ArrayList<CustomItemStack>();
		for (CustomItem cItem : cItems) {
			Debug.saySuper("Currently Processing: " + cItem.getIdName());
			/*
			 * Adding to data.yml and obtaining values
			 */
			DataFile dataFile = DataFile.getInstance();
			String texture = null;
			String idName = cItem.getIdName();
			if (cItem instanceof ModelInjector) {
				ModelInjector model = (ModelInjector) cItem;
				final HashMap<String, Short> textures = model.getAllTextures();
				for (String string : textures.keySet()) {
					Debug.saySuper("Currently Processing Texure: " + string);
					short freeDurability;
					if (dataFile.getCustomItem(cItem.getIdName(), string) == null) {
						freeDurability = dataFile.getFreeDurability(cItem.getMaterial());
						Debug.saySuper("Set durability of Texture '" + string + "' to: " + freeDurability);
					} else {
						freeDurability = dataFile.getCustomItem(cItem.getIdName(), string).getDurability();
						Debug.saySuper("Set durability of Texture '" + string + "' to: " + freeDurability);
					}
					textures.put(string, freeDurability);
					if (model.getDefaultTexture().equals(string)) {
						Debug.saySuper("Texture " + string + " is default texture.");
						cItem.setDurability(freeDurability);
						texture = string;
					}
					dataFile.addStorageCustomItem(
							new StorageCustomItem(cItem.getMaterial(), freeDurability, idName, string));
				}
			}
			short durability = cItem.getDurability();
			/*
			 * CustomItems and ItemStacks
			 */
			CustomItemStack cStack = new CustomItemStack(cItem, durability, texture);
			ItemStack item = cStack.getItemStack();
			cStacks.add(cStack);
			for (CustomRecipe cRecipe : cItem.getCustomRecipes()) {
				cRecipe.registerBukkitRecipe(item);
			}
		}
		AdditionsAPI.cStacks = ImmutableList.copyOf(cStacks);

		List<CustomItem> cItemsList = new ArrayList<CustomItem>();
		for (CustomItemStack cStack : cStacks)
			cItemsList.add(cStack.getCustomItem());
		AdditionsAPI.cItems = ImmutableList.copyOf(cItemsList);

		DataFile.getInstance().saveData();
	}

	// === STORAGE === //

	public static boolean isCustomItem(String idName) {
		try {
			for (CustomItemStack cStack : cStacks)
				if (cStack.getCustomItem().getIdName().equals(idName))
					return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static ImmutableList<CustomItemStack> getAllCustomItemStacks() {
		// TODO: Test if it's immutable
		return cStacks;
	}

	public static ImmutableList<CustomItem> getAllCustomItems() {
		return cItems;
	}

	public static void clearAll() {
		cStacks = null;
		cItems = null;
	}

	// === ITEMSTACKS === //

	public static String getIdName(ItemStack item) {
		if (item == null || item.getType().equals(Material.AIR))
			return null;
		ItemStack stack = NbtFactory.getCraftItemStack(item.clone());
		NbtCompound nbt = NbtFactory.fromItemTag(stack);
		return nbt.getString("CustomItem.IdName", null);
	}

	public static boolean isCustomItem(ItemStack item) {
		if (getIdName(item) != null)
			if (isCustomItem(getIdName(item)))
				return true;
		return false;
	}
}
