package com.chrismin13.moreminecraft.api.items;

import org.bukkit.Material;

import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.enums.ToolType;

public class CustomTool extends CustomItem {

	// === VARIABLES === //

	private ToolType toolType;

	private Double attackSpeed;
	private Double attackDamage;
	private boolean fakeDamageLore = true;
	private boolean hideAttributes = true;

	// === CREATING THE TOOL === //

	public CustomTool(Material material, int amount, short durability, String customItemIdName) {
		super(material, amount, durability, customItemIdName, ItemType.TOOL,
				ToolType.getToolType(material).getDurabilityMechanics());
		this.toolType = ToolType.getToolType(material);
	}

	// === CONFIGURING THE TOOL === //

	public ToolType getToolType() {
		return toolType;
	}

	// === ATTACK SPEED / DAMAGE === //

	public void setAttackSpeed(Double attackSpeed) {
		this.attackSpeed = attackSpeed;
	}

	public void setAttackDamage(Double attackDamage) {
		this.attackDamage = attackDamage;
	}

	public Double getAttackSpeed() {
		return attackSpeed;
	}

	public Double getAttackDamage() {
		return attackDamage;
	}

	// === DAMAGE LORE === //

	public void setFakeDamageLore(Boolean lore) {
		fakeDamageLore = lore;
	}

	public boolean getFakeDamageLore() {
		return fakeDamageLore;
	}

	// === ITEMFLAGS === //

	public boolean hideAttributes() {
		return hideAttributes;
	}

	public void hideAttributes(boolean hideAttributes) {
		this.hideAttributes = hideAttributes;
	}
}
