package com.chrismin13.additionsapi.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.chrismin13.additionsapi.AdditionsAPI;
import com.chrismin13.additionsapi.events.AdditionsAPIInitializationEvent;
import com.chrismin13.additionsapi.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.additionsapi.files.DataFile;
import com.chrismin13.additionsapi.items.textured.CustomTexturedItem;
import com.chrismin13.additionsapi.utils.Debug;
import com.chrismin13.additionsapi.utils.LangFileUtils;
import com.comphenix.attribute.Attributes;
import com.comphenix.attribute.Attributes.Attribute;
import com.comphenix.attribute.Attributes.AttributeType;
import com.comphenix.attribute.Attributes.Operation;
import com.comphenix.attribute.NbtFactory;
import com.comphenix.attribute.NbtFactory.NbtCompound;

import us.fihgu.toolbox.item.ModelInjector;

/**
 * A CustomItemStack contains an ItemStack and a Custom Item. Especially useful
 * if you want to get the ItemStack of a Custom Item or vice versa.
 * 
 * @author chrismin13
 *
 */
public class CustomItemStack implements Cloneable {

	private ItemStack itemStack;
	private CustomItem cItem;
	private String texture;
	private static DataFile data = DataFile.getInstance();

	/**
	 * Create a new CustomItemStack.<br>
	 * Durability and Texture will be the default values.
	 * 
	 * @param idName
	 *            The idName of the Custom Item. The Custom Item must be already
	 *            Initialized using the {@link AdditionsAPIInitializationEvent}.
	 */
	public CustomItemStack(String idName) {
		this(getCustomItem(idName));
	}
	
	/**
	 * Create a new CustomItemStack.
	 * 
	 * @param idName
	 *            The idName of the Custom Item. The Custom Item must be already
	 *            Initialized using the {@link AdditionsAPIInitializationEvent}.
	 * @param durability
	 *            The durability of the {@link ItemStack}.
	 * @param texture
	 *            The texture of the {@link ItemStack}.
	 */
	public CustomItemStack(String idName, short durability, String texture) {
		this(getCustomItem(idName), durability, texture);
	}

	/**
	 * Get a CustomItem.
	 * 
	 * @param idName
	 *            The idName of the Custom Item. The Custom Item must be already
	 *            Initialized using the {@link AdditionsAPIInitializationEvent}.
	 */
	private static CustomItem getCustomItem(String idName) {
		for (CustomItem cItem : AdditionsAPI.getAllCustomItems())
			if (cItem.getIdName().equalsIgnoreCase(idName))
				return cItem;
		return null;
	}

	/**
	 * Create a new CustomItemStack.
	 * 
	 * @param cItem
	 *            The CustomItem that will be used to construct the
	 *            {@link ItemStack}.
	 */
	public CustomItemStack(CustomItem cItem) {
		this.itemStack = getItemStack(cItem, cItem.getDurability());
		this.cItem = cItem;
		if (cItem instanceof ModelInjector) {
			this.texture = data.getCustomItem(cItem.getMaterial(), cItem.getDurability()).getTexture();
		} else {
			this.texture = null;
		}
	}

	/**
	 * Create a new CustomItemStack.
	 * 
	 * @param cItem
	 *            The CustomItem that will be used to construct the
	 *            {@link ItemStack}.
	 * @param durability
	 *            The durability of the {@link ItemStack}.
	 * @param texture
	 *            The name of the texture of the {@link ItemStack}.
	 */
	public CustomItemStack(CustomItem cItem, short durability, String texture) {
		this.itemStack = getItemStack(cItem, durability);
		this.cItem = cItem;
		this.texture = texture;
	}

	/**
	 * Create a new CustomItemStack.
	 * 
	 * @param item
	 *            The {@link ItemStack} that will be used to find the
	 *            {@link CustomItem}, durability and texture.
	 */
	public CustomItemStack(ItemStack item) {
		this.itemStack = item;
		try {
			CustomItem cItem = AdditionsAPI.getCustomItem(item);
			if (cItem != null)
				this.cItem = cItem;
		} catch (NullPointerException exception) {
			new NullPointerException("The following ItemStack is not a CustomItem: Material: " + item.getType()
					+ ", Durability: " + item.getDurability()
					+ ". Please report this and the following Stacktrace to the developer of the Custom Item.")
							.printStackTrace();
		}
		if (data.getCustomItem(item.getType(), item.getDurability()) != null)
			this.texture = data.getCustomItem(item.getType(), item.getDurability()).getTexture();
	}

	@SuppressWarnings("deprecation")
	private static ItemStack getItemStack(CustomItem cItem, short durability) {
		if (AdditionsAPI.getAllCustomItemStacks() != null)
			for (CustomItemStack cStack : AdditionsAPI.getAllCustomItemStacks())
				if (cStack.getCustomItem().getIdName().equalsIgnoreCase(cItem.getIdName()))
					return cStack.getItemStack();
		// Material, amount and durability
		ItemStack item = NbtFactory
				.getCraftItemStack(new ItemStack(cItem.getMaterial(), cItem.getAmount(), durability));

		// Store data in the nbt data of the ItemStack about the CustomItem's ID
		NbtCompound nbt = NbtFactory.fromItemTag(item);
		nbt.put("CustomItem.IdName", cItem.getIdName());

		ItemMeta meta = item.getItemMeta();

		// Unbreakable
		meta.setUnbreakable(cItem.isUnbreakable());

		// Display Name
		meta.setDisplayName(cItem.getDisplayName());

		// Enchantments
		for (Enchantment e : cItem.getEnchantmnets().keySet())
			meta.addEnchant(e, cItem.getEnchantmnets().get(e), true);

		// Lore
		meta.setLore(cItem.getFullLore(cItem.getEnchantmnets(), cItem.getFakeDurability(), null));

		// ItemFlags
		for (ItemFlag flag : cItem.getItemFlags())
			meta.addItemFlags(flag);

		// Set the lore and return the item
		item.setItemMeta(meta);

		// Add Attributes
		for (Attributes.Attribute attribute : cItem.getAttributes()) {
			Attributes attributes = new Attributes(item);
			attributes.add(attribute);
			item = attributes.getStack();
		}

		// Leather Armor stuff
		if (cItem instanceof CustomLeatherArmor) {
			LeatherArmorMeta leatherMeta = (LeatherArmorMeta) item.getItemMeta();

			leatherMeta.setColor(((CustomLeatherArmor) cItem).getColor());

			item.setItemMeta(leatherMeta);
		}
		return item;
	}

	/**
	 * @return The {@link CustomItem} that is contained in the
	 *         {@link CustomItemStack}.
	 */
	public CustomItem getCustomItem() {
		return cItem;
	}

	/**
	 * @return The texture of the Custom Item. If the Custom Item does not
	 *         implement {@link ModelInjector} (in other words, it's not a
	 *         {@link CustomTexturedItem} or similar and does not contain a
	 *         Texture) it will return null.
	 */
	public String getTexture() {
		return texture;
	}

	/**
	 * Sets the Texture of the ItemStack. This is only applicable for
	 * CustomItems that implement {@link ModelInjector} and have the Texture
	 * already Initialized with the {@link AdditionsAPIInitializationEvent}.
	 * <br>
	 * When the Texture is set, the ItemStack's durability is changed according
	 * to the data file.
	 * 
	 * @param texture
	 *            The name of the Texture.
	 */
	public CustomItemStack setTexture(String texture) {
		itemStack.setDurability(data.getCustomItem(cItem.getIdName(), texture).getDurability());
		return this;
	}

	/**
	 * @return The {@link ItemStack} that the {@link CustomItemStack} contains.
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}

	/**
	 * @return The Fake Durability of the ItemStack. This is only valid if the
	 *         {@link CustomItem} has Fake Durability. If it does not, it will
	 *         return 0.
	 */
	public int getFakeDurability() {
		if (cItem.hasFakeDurability()) {
			for (String string : itemStack.getItemMeta().getLore()) {
				if (string.startsWith(CustomItem.LORE_PREFIX + ChatColor.GRAY + LangFileUtils.get("durability"))) {
					String durability = string.replaceFirst(
							CustomItem.LORE_PREFIX + ChatColor.GRAY + LangFileUtils.get("durability") + " ", "");
					String segments[] = durability.split(" / ");
					return Integer.parseInt(segments[0]);
				}
			}
		}
		return 0;
	}

	/**
	 * @return int - The maximum fake durability of the CustomItem. If this is
	 *         above 0, then the Fake Durability lore is enabled. The fake
	 *         durability lore offers a near perfect recreation of the Minecraft
	 *         durability mechanics but hidden in the lore. This way, Textured
	 *         Items that have to be unbreakable can still have durability
	 *         mechanics.
	 */
	public int getMaxFakeDurability() {
		return cItem.getFakeDurability();
	}

	/**
	 * Sets the {@link ItemStack}'s Current Fake Durability. This is only valid
	 * of the {@link CustomItem} has Fake Durability.
	 * 
	 * @param durability
	 *            The durability to set.
	 */
	public CustomItemStack setFakeDurability(int durability) {
		if (cItem.hasFakeDurability()) {
			ItemMeta meta = itemStack.getItemMeta();
			List<String> oldLore = meta.getLore();
			List<String> lore = new ArrayList<String>();
			lore.addAll(meta.getLore());

			if (meta.getLore() != null && !meta.getLore().isEmpty()) {
				Debug.saySuper("Aint empty");
				for (String string : oldLore) {
					Debug.saySuper(string);
					if (string.startsWith(CustomItem.LORE_PREFIX) && string.endsWith(CustomItem.LORE_SUFFIX)) {
						Debug.saySuper("Starts and ends with");
						lore.remove(string);
					}
				}
			}
			lore.addAll(cItem.getFullLore(itemStack.getEnchantments(), durability, getAttributes()));

			meta.setLore(lore);
			itemStack.setItemMeta(meta);
		}
		return this;
	}

	/**
	 * Reduces the durability of the CustomItemStack, whether it's using Fake
	 * Durability or not. It uses the {@link PlayerCustomItemDamageEvent}, so it
	 * could be cancelled.
	 * 
	 * @param durabilityToReduce
	 *            The amount of durability that will be reduced
	 */
	public CustomItemStack reduceDurability(Player player, int durabilityToReduce) {
		GameMode gm = player.getGameMode();
		if (itemStack == null)
			return this;
		if ((gm.equals(GameMode.SURVIVAL) || gm.equals(GameMode.ADVENTURE)))
			if (cItem.hasFakeDurability())
				Bukkit.getPluginManager()
						.callEvent(new PlayerCustomItemDamageEvent(player, getItemStack(), durabilityToReduce, cItem));
			else if (!cItem.isUnbreakable())
				Bukkit.getPluginManager().callEvent(new PlayerItemDamageEvent(player, itemStack, durabilityToReduce));
		return this;
	}

	/**
	 * Updates the lore of the ItemStack using the
	 * {@link CustomItem#getFullLore(Map, int, Attributes)} method. This is
	 * necessary if you added Enchantments and will be done automatically if the
	 * Item was enchanted in an enchantment table or had its durability changed.
	 */
	public CustomItemStack updateLore() {
		return updateLore(itemStack.getEnchantments());
	}

	/**
	 * Updates the lore of the ItemStack using the
	 * {@link CustomItem#getFullLore(Map, int, Attributes)} method. This is
	 * necessary if you added Enchantments and will be done automatically if the
	 * Item was enchanted in an enchantment table or had its durability changed.
	 */
	public CustomItemStack updateLore(Map<Enchantment, Integer> enchantsToCheck) {
		ItemMeta meta = itemStack.getItemMeta();
		if (meta.getLore() == null || meta.getLore().isEmpty())
			return this;
		
		final List<String> lore = meta.getLore();
		final ArrayList<String> loreToRemove = new ArrayList<String>();

		if (meta.getLore() != null && !meta.getLore().isEmpty())
			for (String string : lore)
				/*
				 * By adding Lore Prefix and Suffix we can identify the Lore of
				 * the Custom Items part, thus making plugins like mcMMO which
				 * store data on the lore still compatible
				 */
				if (string.startsWith(CustomItem.LORE_PREFIX) && string.endsWith(CustomItem.LORE_SUFFIX))
					/*
					 * Must add it in another list and remove it later,
					 * otherwise it will be a Concurrent Modification
					 */
					loreToRemove.add(string);
		if (!loreToRemove.isEmpty())
			lore.removeAll(loreToRemove);

		if (cItem.getFullLore(enchantsToCheck, getFakeDurability(), getAttributes()) != null)
			lore.addAll(cItem.getFullLore(enchantsToCheck, getFakeDurability(), getAttributes()));

		meta.setLore(lore);
		itemStack.setItemMeta(meta);
		return this;
	}

	/**
	 * Add an attribute to the ItemStack. If there are no already existing
	 * Attributes, Attack Speed and Attack Damage will be overriden, so keep
	 * that in mind.
	 * 
	 * @param type
	 * @param name
	 * @param amount
	 * @param uuid
	 * @param slot
	 * @param operation
	 */
	public void addAttribute(AttributeType type, String name, double amount, UUID uuid, EquipmentSlot slot,
			Operation operation) {
		Attribute attribute = Attribute.newBuilder().amount(amount).operation(operation).type(type).name(name)
				.uuid(uuid).slot(slot).build();
		new Attributes(itemStack).add(attribute);
		updateLore();
	}

	/**
	 * @return The Attributes of the Custom Item.
	 */
	public Attributes getAttributes() {
		return new Attributes(itemStack);
	}

	/**
	 * Remove an Attribute from the Custom Item.
	 * 
	 * @param type
	 * @param name
	 * @param amount
	 * @param uuid
	 * @param slot
	 * @param operation
	 */
	public void removeAttribute(AttributeType type, String name, double amount, UUID uuid, EquipmentSlot slot,
			Operation operation) {
		Attribute attribute = Attribute.newBuilder().amount(amount).operation(operation).type(type).name(name)
				.uuid(uuid).slot(slot).build();
		new Attributes(itemStack).remove(attribute);
		updateLore();
	}

	/**
	 * Add all NBT Data from a Map. The String is the NBT Key, the Object is the
	 * data you wish to store. Valid types are: Void, byte, short, int, long,
	 * float, double, byte[], int[], String, List and Map.
	 * 
	 * @param nbtData
	 */
	public void addAllNBTData(Map<? extends String, ? extends Object> nbtData) {
		if (itemStack == null || itemStack.getType().equals(Material.AIR))
			return;
		ItemStack stack = NbtFactory.getCraftItemStack(itemStack);
		NbtCompound nbt = NbtFactory.fromItemTag(stack);
		nbt.putAll(nbtData);
		updateLore();
	}

	/**
	 * Add NBT Data. Valid types are: Void, byte, short, int, long, float,
	 * double, byte[], int[], String, List and Map.
	 * 
	 * @param nbtKey
	 * @param nbtData
	 */
	public void addNBTData(String nbtKey, Object nbtData) {
		if (itemStack == null || itemStack.getType().equals(Material.AIR))
			return;
		ItemStack stack = NbtFactory.getCraftItemStack(itemStack);
		NbtCompound nbt = NbtFactory.fromItemTag(stack);
		nbt.put(nbtKey, nbtData);
		updateLore();
	}

	/**
	 * @return The NBT Data that the ItemStack contains. They are cloned, just
	 *         in case there aren't any and empty data would be created, causing
	 *         problems with stacking.
	 */
	public NbtCompound getNBTData() {
		if (itemStack == null || itemStack.getType().equals(Material.AIR))
			return null;
		ItemStack stack = NbtFactory.getCraftItemStack(itemStack.clone());
		NbtCompound nbt = NbtFactory.fromItemTag(stack);
		return nbt;
	}

	/**
	 * Remove NBT Data from the ItemStack.
	 * 
	 * @param nbtKey
	 */
	public void removeNBTData(String nbtKey) {
		if (itemStack == null || itemStack.getType().equals(Material.AIR))
			return;
		ItemStack stack = NbtFactory.getCraftItemStack(itemStack);
		NbtCompound nbt = NbtFactory.fromItemTag(stack);
		nbt.remove(nbtKey);
		updateLore();
	}

	@Override
	public CustomItemStack clone() {
		try {
			return (CustomItemStack) super.clone();
		} catch (CloneNotSupportedException e) {
			Debug.sayTrueError("Cloning not supported!");
			e.printStackTrace();
		}
		return null;
	}
}
