package com.chrismin13.moreminecraft.items;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.chrismin13.moreminecraft.files.DataFile;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;
import com.chrismin13.moreminecraft.utils.Debug;
import com.chrismin13.moreminecraft.utils.LangFileUtils;
import com.comphenix.attribute.AttributeStorage;
import com.comphenix.attribute.Attributes.AttributeType;
import com.comphenix.attribute.Attributes.Operation;

public class CustomItemStack implements Cloneable {

	private ItemStack itemStack;
	private CustomItem cItem;
	private String texture;
	private static DataFile data = DataFile.getInstance();
	
	public CustomItemStack(String idName) {
		this(getCustomItem(idName));
	}
	
	public CustomItemStack(String idName, short durability, String texture) {
		this(getCustomItem(idName), durability, texture);
	}
	
	private static CustomItem getCustomItem(String idName) {
		for(CustomItem cItem : CustomItemUtils.getAllCustomItems()) 
			if (cItem.getIdName().equals(idName))
				return cItem;
		return null;
	}
	
	public CustomItemStack(CustomItem cItem) {
		this(cItem, data.getCustomItem(cItem.getMaterial(), cItem.getDurability()).getDurability(),
				data.getCustomItem(cItem.getMaterial(), cItem.getDurability()).getTexture());
	}

	public CustomItemStack(CustomItem cItem, short durability, String texture) {
		this.itemStack = getItemStack(cItem, durability);
		this.cItem = cItem;
		this.texture = texture;
	}

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
		for (CustomItemStack cStack : CustomItemUtils.getAllCustomItemStacks())
			if (cStack.getCustomItem().getIdName().equals(cItem.getIdName()))
				return cStack.getItemStack();
		// Material, amount and durability
		ItemStack item = new ItemStack(cItem.getMaterial(), cItem.getAmount(), durability);
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

		if (cItem instanceof CustomTool) {
			// Add attack speed and attack damage

			CustomTool cTool = ((CustomTool) cItem);

			if (cTool.getAttackSpeed() != null)
				cItem.addAttribute(AttributeType.GENERIC_ATTACK_SPEED, cTool.getAttackSpeed() - 4, EquipmentSlot.HAND,
						Operation.ADD_NUMBER);
			if (cTool.getAttackDamage() != null)
				cItem.addAttribute(AttributeType.GENERIC_ATTACK_DAMAGE, cTool.getAttackDamage() - 1, EquipmentSlot.HAND,
						Operation.ADD_NUMBER);

			if (cTool.hideAttributes())
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		}

		// Set the lore and return the item
		item.setItemMeta(meta);

		// Store data in the attributes about the CustomItem's ID
		AttributeStorage attributeStorage = AttributeStorage.newTarget(item, cItem.getAttributeStorageUUID(),
				cItem.getAttributes());
		attributeStorage.setData(cItem.getIdName());
		item = attributeStorage.getTarget();

		// Leather Armor stuff
		if (cItem instanceof CustomLeatherArmor) {
			LeatherArmorMeta leatherMeta = (LeatherArmorMeta) item.getItemMeta();

			leatherMeta.setColor(((CustomLeatherArmor) cItem).getColor());

			item.setItemMeta(leatherMeta);
		}
		return item;
	}

	public CustomItemStack(CustomItem cItem, ItemStack item) {
		this.itemStack = item;
		this.cItem = cItem;
		this.texture = data.getCustomItem(item.getType(), item.getDurability()).getTexture();
	}

	public CustomItem getCustomItem() {
		return cItem.clone();
	}

	public String getTexture() {
		return new String(texture);
	}

	public CustomItemStack setTexture(String texture) {
		itemStack.setDurability(data.getCustomItem(cItem.getIdName(), texture).getDurability());
		return this;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

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

	public CustomItemStack setFakeDurability(int durability) {
		if (cItem.hasFakeDurability()) {
			ItemMeta meta = itemStack.getItemMeta();

			meta.setLore(cItem.getFullLore(itemStack.getEnchantments(), durability));

			itemStack.setItemMeta(meta);
		}
		return this;
	}

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
