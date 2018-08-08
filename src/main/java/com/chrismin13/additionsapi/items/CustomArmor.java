package com.chrismin13.additionsapi.items;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import com.chrismin13.additionsapi.durability.ArmorDurability;
import com.chrismin13.additionsapi.durability.ItemDurability;
import com.chrismin13.additionsapi.enums.ArmorType;
import com.comphenix.attribute.Attributes.AttributeType;
import com.comphenix.attribute.Attributes.Operation;

/**
 * Creates a Custom Item with the ItemType ARMOR and the ArmorDurability
 * Durability Mechanics.
 * 
 * @author chrismin13
 *
 */
public class CustomArmor extends CustomItem {

	// === CREATING THE ARMOR === //

	/**
	 * Creates a Custom Armor Piece.
	 * 
	 * @param material
	 *            The Material that the Custom Armor will be made of. Valid
	 *            Materials are all that contain in their name HELMET,
	 *            CHESTPLATE, LEGGINGS or BOOTS.
	 * @param amount
	 *            The amount of the Custom Armor. Recommended value is one, as
	 *            Armor Pieces do not have a stackable size higher than 1 by
	 *            default.
	 * @param durability
	 *            The Durability of the Custom Armor.
	 * @param idName
	 *            the Custom Armor's ID Name. This MUST BE SIMILAR to
	 *            "vanilla_additions:emerald_sword" and is saved in the
	 *            ItemStack so you can easily detect which Custom Armor it is.
	 */
	public CustomArmor(Material material, int amount, short durability, String idName) {
		super(material, amount, durability, idName);
		super.setDurabilityMechanics(new ArmorDurability());
	}

	// === CONFIGURING THE ARMOR === //

	/**
	 * @return The ArmorType of the CustomTool. This is found using the
	 *         {@link ArmorType#getArmorType(Material)} method, where material
	 *         is the Custom Armor's Material.
	 */
	public ArmorType getArmorType() {
		return ArmorType.getArmorType(getMaterial());
	}

	/** 
	 * Adds an Attribute to the CustomItem. If you don't know what they do or
	 * how they work, check out the Minecraft wiki. The UUID will be the one
	 * that is found by default in Minecraft for the specified Slot. The name
	 * will be, as a placeholder, TBD (which means To Be Determined). <br>
	 * The {@link EquipmentSlot} will be automatically added by checking the
	 * Armor Type of the Material of the Custom Armor.
	 * 
	 * @param type
	 *            The attribute that will be added.
	 * @param amount
	 *            The amount of the attribute. Can also be a negative value.
	 * @param operation
	 *            The math operation that will be used for the amount specified.
	 */
	public CustomArmor addAttribute(AttributeType type, double amount, Operation operation) {
		super.addAttribute(type, amount, getArmorType().getEquipmentSlot(), operation);
		return this;
	}

	// === DURABILITY MECHANICS === //

	/**
	 * @return ArmorDurability - The ItemDurability mechanics. These determine
	 *         how the durability of the CustomArmor, both for Fake and real
	 *         durability, will drop.
	 */
	@Override
	public ArmorDurability getDurabilityMechanics() {
		return (ArmorDurability) super.getDurabilityMechanics();
	}

	/**
	 * These determine how the durability of the CustomArmor, both for Fake and
	 * real durability, will drop.
	 * 
	 * @param itemDurability
	 *            the itemDurability to set. Every class that extends or is
	 *            {@link ArmorDurability} is valid.
	 */
	@Override
	@Deprecated
	public CustomItem setDurabilityMechanics(ItemDurability itemDurability) {
		if (!(itemDurability instanceof ArmorDurability))
			throw new ClassCastException("Cannot cast the ItemDurability specified to ArmorDurability!");
		super.setDurabilityMechanics(itemDurability);
		return this;
	}

	/**
	 * These determine how the durability of the CustomArmor, both for Fake and
	 * real durability, will drop.
	 * 
	 * @param armorDurability
	 *            the armorDurbility to set. Every class that extends or is
	 *            {@link ArmorDurability} is valid.
	 */
	public CustomArmor setDurabilityMechanics(ArmorDurability armorDurability) {
		super.setDurabilityMechanics(armorDurability);
		return this;
	}

	/**
	 * Adds the {@link AttributeType} Armor with the amount specified using the
	 * addition operation.
	 * 
	 * @param amount
	 *            The amount of armor to be added.
	 */
	public CustomArmor addArmor(double amount) {
		addAttribute(AttributeType.ARMOR, amount, Operation.ADD_NUMBER);
		return this;
	}

	/**
	 * Adds the {@link AttributeType} Armor Toughness with the amount specified
	 * using the addition operation. The Armor Toughness Attribute was added in
	 * 1.9.1, so, just like this plugin, it won't work on earlier versions of
	 * Minecraft. I haven't tested this feature on those old versions!
	 * 
	 * @param amount
	 *            The amount of armor to be added.
	 */
	public CustomArmor addArmorToughness(double amount) {
		addAttribute(AttributeType.ARMOR_TOUGHNESS, amount, Operation.ADD_NUMBER);
		return this;
	}
}