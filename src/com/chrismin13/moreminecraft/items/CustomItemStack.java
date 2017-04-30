package com.chrismin13.moreminecraft.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.chrismin13.moreminecraft.events.MoreMinecraftAPIInitializationEvent;
import com.chrismin13.moreminecraft.events.item.PlayerCustomItemDamageEvent;
import com.chrismin13.moreminecraft.files.DataFile;
import com.chrismin13.moreminecraft.items.textured.CustomTexturedItem;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;
import com.chrismin13.moreminecraft.utils.Debug;
import com.chrismin13.moreminecraft.utils.LangFileUtils;
import com.comphenix.attribute.Attributes;
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
	 *            Initialized using the
	 *            {@link MoreMinecraftAPIInitializationEvent}.
	 */
	public CustomItemStack(String idName) {
		this(getCustomItem(idName));
	}

	/**
	 * Create a new CustomItemStack.
	 * 
	 * @param idName
	 *            The idName of the Custom Item. The Custom Item must be already
	 *            Initialized using the
	 *            {@link MoreMinecraftAPIInitializationEvent}.
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
	 *            Initialized using the
	 *            {@link MoreMinecraftAPIInitializationEvent}.
	 */
	private static CustomItem getCustomItem(String idName) {
		for (CustomItem cItem : CustomItemUtils.getAllCustomItems())
			if (cItem.getIdName().equals(idName))
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
		if (CustomItemUtils.isCustomItem(item)) {
			for (CustomItem cItem : CustomItemUtils.getAllCustomItems())
				if (cItem.getIdName().equals(CustomItemUtils.getIdName(item)))
					this.cItem = cItem;
		} else {
			Debug.sayError("The following ItemStack is not a CustomItem: Material: " + item.getType() + ", Durability: "
					+ item.getDurability() + ". Please report this to the developer of the Custom Item.");
		}
		if (data.getCustomItem(item.getType(), item.getDurability()) != null)
			this.texture = data.getCustomItem(item.getType(), item.getDurability()).getTexture();
	}

	@SuppressWarnings("deprecation")
	private static ItemStack getItemStack(CustomItem cItem, short durability) {
		if (CustomItemUtils.getAllCustomItemStacks() != null)
			for (CustomItemStack cStack : CustomItemUtils.getAllCustomItemStacks())
				if (cStack.getCustomItem().getIdName().equals(cItem.getIdName()))
					return cStack.getItemStack();
		// Material, amount and durability
		ItemStack item = NbtFactory
				.getCraftItemStack(new ItemStack(cItem.getMaterial(), cItem.getAmount(), durability));

		// Store data in the nbt data of the ItemStack about the CustomItem's ID
		NbtCompound nbt = NbtFactory.fromItemTag(item);
		nbt.put("CustomItem.IdName", cItem.getIdName());

		ItemMeta meta = item.getItemMeta();

		// Unbreakable
		meta.spigot().setUnbreakable(cItem.isUnbreakable());

		// Display Name
		meta.setDisplayName(cItem.getDisplayName());

		// Enchantments
		for (Enchantment e : cItem.getEnchantmnets().keySet())
			meta.addEnchant(e, cItem.getEnchantmnets().get(e), true);

		// Lore
		meta.setLore(cItem.getFullLore(cItem.getEnchantmnets(), cItem.getFakeDurability()));

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
	 * already Initialized with the {@link MoreMinecraftAPIInitializationEvent}.
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
				if (string.startsWith(ChatColor.GRAY + "Durability: ")) {
					String durability = string.replaceFirst(ChatColor.GRAY + LangFileUtils.get("durability") + " ", "");
					String segments[] = durability.split(" / ");
					return Integer.parseInt(segments[0]);
				}
			}
		}
		return 0;
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

			meta.setLore(cItem.getFullLore(itemStack.getEnchantments(), durability));

			itemStack.setItemMeta(meta);
		}
		return this;
	}

	/**
	 * Reduces the durability of the CustomItemStack, whether it's using Fake
	 * Durability or not. It uses the {@link PlayerCustomItemDamageEvent}, so it could
	 * be cancelled.
	 * 
	 * @param durabilityToReduce
	 *            The amount of durability that will be reduced
	 */
	public CustomItemStack reduceDurability(Player player, int durabilityToReduce) {
		Bukkit.getPluginManager()
				.callEvent(new PlayerCustomItemDamageEvent(player, getItemStack(), durabilityToReduce, cItem));
		return this;
	}

	/**
	 * Updates the lore of the ItemStack using the
	 * {@link CustomItem#getFullLore(java.util.Map, int)} method. This is
	 * necessary if you added Enchantments and will be done automatically if the
	 * Item was enchanted in an enchantment table or had its durability changed.
	 */
	public CustomItemStack updateLore() {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setLore(cItem.getFullLore(itemStack.getEnchantments(), getFakeDurability()));
		itemStack.setItemMeta(meta);
		return this;
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
