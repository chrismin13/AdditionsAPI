package com.chrismin13.moreminecraft.api.items;

import org.bukkit.Material;

import com.chrismin13.moreminecraft.api.durability.ItemDurability;
import com.chrismin13.moreminecraft.enums.ItemType;

public class CustomBow extends CustomItem {

	public CustomBow(int amount, short durability, String customItemIdName, ItemDurability itemDurability) {
		super(Material.BOW, amount, durability, customItemIdName, ItemType.BOW, itemDurability);
	}

	public CustomBow(int amount, short durability, String customItemIdName) {
		super(Material.BOW, amount, durability, customItemIdName, ItemType.BOW);
	}
	
}
