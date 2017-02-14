package com.chrismin13.moreminecraft.api;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.chrismin13.moreminecraft.files.DataFile;
import com.chrismin13.moreminecraft.utils.CustomItemUtils;
import com.chrismin13.moreminecraft.utils.Debug;
import com.chrismin13.moreminecraft.utils.attributestorage.AttributeStorage;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.AttributeType;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.Operation;

public class CustomItemStack {

	private ItemStack itemStack;
	private CustomItem cItem;
	private String texture;
	private static DataFile data = DataFile.getInstance();

	public CustomItemStack(CustomItem cItem, short durability, String texture) {
		this.itemStack = getItemStack(cItem, durability);
		this.cItem = cItem;
		this.texture = texture;
	}

	public CustomItemStack(ItemStack item) {
		this.itemStack = item;
		if (CustomItemUtils.isCustomItem(item))
			this.cItem = CustomItemUtils.getCustomItem(item);
		else
			Debug.sayTrueError("The following ItemStack is not a CustomItem: Material: " + item.getType()
					+ ", Durability: " + item.getDurability());
		this.texture = data.getCustomItem(item.getType(), item.getDurability()).getTexture();
	}

	private static ItemStack getItemStack(CustomItem cItem, short durability) {
		// Material, amount and durability
		ItemStack item = new ItemStack(cItem.getMaterial(), cItem.getAmount(), durability);
		ItemMeta meta = item.getItemMeta();

		// Unbreakable
		meta.spigot().setUnbreakable(cItem.isUnbreakable());

		// Unbreakable Visibility
		if (!cItem.getUnbreakableVisibility())
			meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

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
		attributeStorage.setData(cItem.getCustomItemIdName());
		item = attributeStorage.getTarget();

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

	public void setTexture(String texture) {
		itemStack.setDurability(data.getCustomItem(cItem.getCustomItemIdName(), texture).getDurability());
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

}
