package com.chrismin13.additionsapi.items;

import org.bukkit.Color;

import com.chrismin13.additionsapi.enums.ArmorType;

/**
 * A Custom Armor that is made of a leather armor material. Includes
 * modifications for the Color of the Leather Armor.
 * 
 * @author chrismin13
 *
 */
public class CustomLeatherArmor extends CustomArmor {

	// === VARIABLES === //

	private Color color;

	// ================= //

	// === CREATING THE ARMOR === //

	/**
	 * Creates a Custom Leather Armor piece.
	 * 
	 * @param armorType
	 *            The type of the Leather Armor. This will automatically set the
	 *            Material using {@link ArmorType#getLeatherMaterial()}.
	 * @param amount
	 *            The amount of the Leather Armor. Recommended value is one, as
	 *            Armor Pieces do not have a stackable size higher than 1 by
	 *            default.
	 * @param durability
	 *            The durability of the Leather Armor.
	 * @param idName
	 *            the Custom Armor's ID Name. This MUST BE SIMILAR to
	 *            "vanilla_additions:emerald_sword" and is saved in the
	 *            ItemStack so you can easily detect which Custom Armor it is.
	 * @param color
	 *            The color that the leather armor will have.
	 */
	public CustomLeatherArmor(ArmorType armorType, int amount, short durability, String idName, Color color) {
		super(armorType.getLeatherMaterial(), amount, durability, idName);
		this.color = color;
	}

	// === COLOR === //

	/**
	 * @return The Color that the leather armor will have.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the CustomLeatherArmor's Color.
	 * 
	 * @param color
	 *            the color to set. Use {@link Color#fromRGB(int, int, int)} to
	 *            find it easily using RGB values.
	 */
	public CustomLeatherArmor setColor(Color color) {
		this.color = color;
		return this;
	}
}