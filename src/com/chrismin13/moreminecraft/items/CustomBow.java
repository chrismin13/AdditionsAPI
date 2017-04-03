package com.chrismin13.moreminecraft.items;

import org.bukkit.Material;

import com.chrismin13.moreminecraft.durability.ItemDurability;

public class CustomBow extends CustomItem {

	public CustomBow(int amount, short durability, String customItemIdName, ItemDurability itemDurability) {
		super(Material.BOW, amount, durability, customItemIdName, itemDurability);
	}

	public CustomBow(int amount, short durability, String customItemIdName) {
		super(Material.BOW, amount, durability, customItemIdName);
	}
	
}
