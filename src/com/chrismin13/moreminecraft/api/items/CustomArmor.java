package com.chrismin13.moreminecraft.api.items;

import org.bukkit.Material;

import com.chrismin13.moreminecraft.api.durability.ArmorDurability;
import com.chrismin13.moreminecraft.api.durability.ItemDurability;
import com.chrismin13.moreminecraft.enums.ArmorType;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.AttributeType;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.Operation;

public class CustomArmor extends CustomItem {

	// === VARIABLES === //

	private ArmorType armorType;

	// === CREATING THE ARMOR === //

	public CustomArmor(Material material, int amount, short durability, String customItemIdName, ArmorType armorType) {
		super(material, amount, durability, customItemIdName, ItemType.ARMOR);
		this.armorType = armorType;
		super.setDurabilityMechanics(new ArmorDurability());
	}

	// === CONFIGURING THE ARMOR === //

	public ArmorType getArmorType() {
		return this.armorType;
	}

	public void addAttribute(AttributeType type, Double amount, Operation operation) {
		super.addAttribute(type, amount, armorType.getEquipmentSlot(), operation);
	}

	// === DURABILITY MECHANICS === //
	
	@Override
	public ArmorDurability getDurabilityMechanics() {
		return (ArmorDurability) super.getDurabilityMechanics();
	}
	
	@Override
	@Deprecated
	public void setDurabilityMechanics(ItemDurability itemDurability) {
		if (!(itemDurability instanceof ArmorDurability))
			throw new ClassCastException("Cannot cast the ItemDurability specified to ArmorDurability!");
		super.setDurabilityMechanics(itemDurability);
	}
	
	public void setDurabilityMechanics(ArmorDurability armorDurability) {
		super.setDurabilityMechanics(armorDurability);
	}
	
}
