package com.chrismin13.additionsapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.chrismin13.additionsapi.commands.AdditionsCmd;
import com.chrismin13.additionsapi.commands.AdditionsTab;
import com.chrismin13.additionsapi.events.AdditionsAPIInitializationEvent;
import com.chrismin13.additionsapi.events.AdditionsAPIPostInitializationEvent;
import com.chrismin13.additionsapi.files.ConfigFile;
import com.chrismin13.additionsapi.files.CustomItemConfig;
import com.chrismin13.additionsapi.files.DataFile;
import com.chrismin13.additionsapi.files.LangFile;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomItemStack;
import com.chrismin13.additionsapi.items.StorageCustomItem;
import com.chrismin13.additionsapi.listeners.DurabilityBar;
import com.chrismin13.additionsapi.listeners.custom.ArmorEquip;
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
import com.chrismin13.additionsapi.listeners.custom.PlayerDropCustomItem;
import com.chrismin13.additionsapi.listeners.custom.PlayerPickupCustomItem;
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
import com.chrismin13.additionsapi.listeners.vanilla.PlayerDropItem;
import com.chrismin13.additionsapi.listeners.vanilla.PlayerFish;
import com.chrismin13.additionsapi.listeners.vanilla.PlayerInteract;
import com.chrismin13.additionsapi.listeners.vanilla.PlayerPickupItem;
import com.chrismin13.additionsapi.listeners.vanilla.PlayerShearEntity;
import com.chrismin13.additionsapi.recipes.CustomRecipe;
import com.chrismin13.additionsapi.utils.Debug;
import com.codingforcookies.armorequip.ArmorListener;
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

		// Initialize Metrics
		new Metrics(this);

		// Registering listeners
		for (Listener listener : Arrays.asList(new EnchantItem(), new Anvil(), new CraftingTable(), new BlockBreak(),
				new CustomItemBlockBreak(), new EntityDamage(), new EntityDamageByPlayerUsingCustomItem(),
				new PlayerCustomItemDamage(), new PlayerInteract(), new CustomItemPlayerInteract(),
				new PlayerShearEntity(), new CustomItemShearEntity(), new PlayerFish(), new CustomItemFish(),
				new BlockIgnite(), new CustomItemBlockIgnite(), new EntityShootBow(), new EntityShootCustomBow(),
				new CustomShieldEntityDamageByEntity(), new EntityToggleGlide(), new CustomElytraPlayerToggleGlide(),
				this, new ArrowFromCustomBowHit(), new PlayerDeath(), new DurabilityBar(), new FurnaceBurn(),
				new CustomItemFurnaceBurn(), new ResourcePackListener(), new Experience(), new ArmorListener(),
				new ArmorEquip(), new PlayerDropItem(), new PlayerDropCustomItem(), new PlayerPickupItem(),
				new PlayerPickupCustomItem())) {
			getServer().getPluginManager().registerEvents(listener, this);
		}

		// Commands
		PluginCommand additions = getCommand("additions");
		additions.setExecutor(new AdditionsCmd());
		additions.setTabCompleter(new AdditionsTab());

		// Commented out - these are not ready yet. Works on Linux but still fighting
		// for the rest of the OSes.
		// Tools.loadAgentLibrary();
		// new RuntimeTransformer(ItemStackTransformer.class,
		// CraftItemStack_1_12_Transformer.class);

		// After all plugins have been enabled
		getServer().getScheduler().scheduleSyncDelayedTask(this, () -> load());
	}

	public void onDisable() {
		// Useful when reloading!
		if (!ConfigFile.getInstance().getConfig().getBoolean("resource-pack.use-minepack"))
			ResourcePackServer.stopServer();

		DurabilityBar.removeAllDurabilityBars();
	}

	private static void setupHTTPServer() {
		try {
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
		if (ConfigFile.getInstance().getConfig().getBoolean("resource-pack.smooth-leather-armor-texture"))
			event.addResourcePackFromPlugin(instance, "resource/smooth_armor.zip");
		event.addResourcePackFromPlugin(instance, "resource/no_hoe_sound.zip");
		instance.getServer().getPluginManager().callEvent(event);

		Debug.say("Finished Initialization.");
		Debug.saySuper("aaaaand chat spam too. :P");

		AdditionsAPIPostInitializationEvent postEvent = new AdditionsAPIPostInitializationEvent();
		instance.getServer().getPluginManager().callEvent(postEvent);

		if (ResourcePackManager.hasResource()) {
			setupHTTPServer();
			if (ResourcePackManager.neededRebuild
					&& ConfigFile.getInstance().getConfig().getBoolean("resource-pack.send-to-player")) {
				for (Player player : Bukkit.getOnlinePlayers()) {
					ResourcePackListener.sendResourcePack(player, player.getAddress().getHostString());
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

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.MONITOR)
	public void onInitialization(AdditionsAPIInitializationEvent event) {
		CustomItem[] cItems = event.getCustomItems();
		Debug.say("Recieved in total " + Integer.toString(cItems.length) + " Custom Items!");
		if (cItems.length == 0)
			return;
		List<CustomItemStack> cStacks = new ArrayList<CustomItemStack>();
		DataFile dataFile = DataFile.getInstance();
		for (CustomItem cItem : cItems) {
			Debug.saySuper("Currently Processing: " + cItem.getIdName());
			/*
			 * Adding to data.yml and obtaining values
			 */
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
					if (dataFile.getCustomItem(cItem.getMaterial(), freeDurability) == null
							&& dataFile.getCustomItem(idName, string) == null)
						dataFile.addStorageCustomItem(
								new StorageCustomItem(cItem.getMaterial(), freeDurability, idName, string));
				}
			}
			short durability = cItem.getDurability();
			/*
			 * CustomItems and ItemStacks
			 */
			CustomItemConfig cItemConfig = new CustomItemConfig(cItem);
			if (cItemConfig.isEnabled()) {
				CustomItemStack cStack = new CustomItemStack(cItem, durability, texture);
				ItemStack item = cStack.getItemStack();
				cStacks.add(cStack);
				if (cItemConfig.canBeCreated()) {
					String[] idPart = idName.split(":");
					int i = 1;
					for (CustomRecipe cRecipe : cItem.getCustomRecipes()) {
						NamespacedKey key = new NamespacedKey(idPart[0], idPart[1] + "_" + i);
						cRecipe.registerBukkitRecipe(key, item);
						i++;
					}
				}
			}
		}
		AdditionsAPI.cStacks = ImmutableList.copyOf(cStacks);

		List<CustomItem> cItemsList = new ArrayList<CustomItem>();
		for (CustomItemStack cStack : AdditionsAPI.cStacks)
			cItemsList.add(cStack.getCustomItem());
		AdditionsAPI.cItems = ImmutableList.copyOf(cItemsList);

		ConfigFile.getInstance().saveConfig();
		DataFile.getInstance().saveData();
	}

	// === STORAGE === //

	public static boolean isCustomItem(String idName) {
		try {
			for (CustomItem cItem : cItems)
				if (cItem.getIdName().equals(idName))
					return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static CustomItem getCustomItem(String idName) {
		try {
			for (CustomItem cItem : cItems)
				if (cItem.getIdName().equals(idName))
					return cItem;
		} catch (Exception e) {
			return null;
		}
		return null;
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
		try {
			String idName = getIdName(item);
			if (idName != null && isCustomItem(idName))
				return true;
		} catch (NullPointerException e) {
			return false;
		}
		return false;
	}

	public static CustomItem getCustomItem(ItemStack item) {
		try {
			String idName = getIdName(item);
			if (idName != null) {
				return getCustomItem(idName);
			}
		} catch (NullPointerException e) {
			return null;
		}
		return null;
	}

	public static List<String> getAllCustomItemIdNames() {
		if (cItems != null && !cItems.isEmpty()) {
			ArrayList<String> idNames = new ArrayList<String>();

			for (CustomItem cItem : cItems)
				idNames.add(cItem.getIdName());

			return idNames;
		}
		return null;
	}
}
