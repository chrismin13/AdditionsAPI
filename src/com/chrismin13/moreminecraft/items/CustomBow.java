package com.chrismin13.moreminecraft.items;

import org.bukkit.Material;

import com.chrismin13.moreminecraft.durability.BowDurability;
import com.chrismin13.moreminecraft.durability.ItemDurability;
import com.chrismin13.moreminecraft.items.textured.CustomTexturedBow;

/**
 * A Custom Bow. No special stuff at the moment. Mostly used for
 * {@link CustomTexturedBow}
 * 
 * @author chrismin13
 *
 */
public class CustomBow extends CustomItem {

	/**
	 * Creates a Custom Bow.
	 * 
	 * @param amount
	 *            The amount that the Custom Bow will have. Recommended value is
	 *            one as bows do not have a stackable size higher than 1 by
	 *            default.
	 * @param durability
	 *            The durability of the Custom Bow.
	 * @param idName
	 *            the Custom Bow's ID Name. This MUST BE SIMILAR to
	 *            "vanilla_additions:emerald_sword" and is saved in the
	 *            ItemStack so you can easily detect which Custom Bow it is.
	 * @param itemDurability
	 *            The Bow's Item Durability. Recommended class is
	 *            {@link BowDurability}
	 */
	public CustomBow(int amount, short durability, String idName, ItemDurability itemDurability) {
		super(Material.BOW, amount, durability, idName, itemDurability);
	}

	/**
	 * Creates a Custom Bow.
	 * 
	 * @param amount
	 *            The amount that the Custom Bow will have. Recommended value is
	 *            one as bows do not have a stackable size higher than 1 by
	 *            default.
	 * @param durability
	 *            The durability of the Custom Bow.
	 * @param idName
	 *            the Custom Bow's ID Name. This MUST BE SIMILAR to
	 *            "vanilla_additions:emerald_sword" and is saved in the
	 *            ItemStack so you can easily detect which Custom Bow it is.
	 */
	public CustomBow(int amount, short durability, String idName) {
		super(Material.BOW, amount, durability, idName);
	}

}
