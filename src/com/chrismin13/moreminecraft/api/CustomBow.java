package com.chrismin13.moreminecraft.api;

import org.bukkit.Material;

import com.chrismin13.moreminecraft.enums.ItemType;

public class CustomBow extends CustomItem {

	public CustomBow(Material material, int amount, short durability, String customItemIdName) {
		super(material, amount, durability, customItemIdName, ItemType.BOW);
	}

}
