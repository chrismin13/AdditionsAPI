package com.chrismin13.moreminecraft.items;

import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;

import com.chrismin13.moreminecraft.enums.ToolType;
import com.comphenix.attribute.Attributes.AttributeType;
import com.comphenix.attribute.Attributes.Operation;

/**
 * 
 * Creates a CustomItem of the ItemType TOOL. Valid Materials are all that
 * include in their name SWORD, AXE, PICKAXE, SPADE or HOE. <br>
 * CustomTools also include getToolType (can be SWORD, AXE, PICKAXE, SPADE or
 * HOE) and can set the Attack Speed and Attack Damage as well as a Fake Lore
 * for Attack Speed and Attack Damage. <br>
 * {@link #addAttackSpeed(double)} and {@link #addAttackDamage(double)} don't
 * work like
 * {@link CustomItem#addAttribute(com.comphenix.attribute.Attributes.AttributeType, Double, org.bukkit.inventory.EquipmentSlot, com.comphenix.attribute.Attributes.Operation)}.
 * Instead the attributes are added after being calculated to match those who
 * are displayed under Swords, Axes, Pickaxes, Shovels or Hoes. For example, to
 * replicate a Diamond Sword you will set an Attack Speed of 1.6 and an Attack
 * Damage of 7.<br>
 * The Fake Attack Lore will add a lore that displays Attack Speed and Attack
 * Damage the same way that they are displayed in Items in Vanilla. This is
 * great if you want to change the Attack Speed or Attack Damage and not have
 * the Attribute Style text. Do not forget to also use
 * {@link #setAttributeVisibility(Boolean)} to hide the Attribute Text from
 * displaying. The strings used in the lore are customizable in the lang.yml
 * file.
 * 
 * @author chrismin13
 *
 */
public class CustomTool extends CustomItem {

	// === VARIABLES === //

	private boolean fakeDamageLore = false;

	// === CREATING THE TOOL === //

	/**
	 * Creates a Custom Tool
	 * 
	 * @param material
	 *            The Material that the Custom Tool will be made of. Valid
	 *            Materials are all that include in their name SWORD, AXE,
	 *            PICKAXE, SPADE or HOE.
	 * @param amount
	 *            The amount of the Custom Tool. Recommended value is 1, as
	 *            tools are not stackable by default.
	 * @param durability
	 *            The Durability of the Custom Tool.
	 * @param idName
	 *            the Custom Tool's ID Name. This MUST BE SIMILAR to
	 *            "vanilla_additions:emerald_sword" and is saved in the
	 *            ItemStack so you can easily detect which Custom Tool it is.
	 */
	public CustomTool(Material material, int amount, short durability, String idName) {
		super(material, amount, durability, idName, ToolType.getToolType(material).getDurabilityMechanics());
	}

	// === CONFIGURING THE TOOL === //

	/**
	 * @return The ToolType of the CustomTool. This is found using the
	 *         {@link ToolType#getToolType(Material)} method, where material is
	 *         the Custom Tool's Material.
	 */
	public ToolType getToolType() {
		return ToolType.getToolType(this.getMaterial());
	}

	// === ATTACK SPEED / DAMAGE === //

	/**
	 * This does not work like
	 * {@link CustomItem#addAttribute(com.comphenix.attribute.Attributes.AttributeType, Double, org.bukkit.inventory.EquipmentSlot, com.comphenix.attribute.Attributes.Operation)}.
	 * Instead the attributes are added after being calculated to match those
	 * who are displayed under Swords, Axes, Pickaxes, Shovels or Hoes. For
	 * example, to replicate a Diamond Sword you will set an Attack Speed of
	 * 1.6.
	 * 
	 * @param attackSpeed
	 *            The Attack Speed you want the Tool to have.
	 */
	public void addAttackSpeed(double attackSpeed) {
		addAttribute(AttributeType.GENERIC_ATTACK_SPEED, attackSpeed - 4, EquipmentSlot.HAND, Operation.ADD_NUMBER);
	}

	/**
	 * This does not work like
	 * {@link CustomItem#addAttribute(com.comphenix.attribute.Attributes.AttributeType, Double, org.bukkit.inventory.EquipmentSlot, com.comphenix.attribute.Attributes.Operation)}.
	 * Instead the attributes are added after being calculated to match those
	 * who are displayed under Swords, Axes, Pickaxes, Shovels or Hoes. For
	 * example, to replicate a Diamond Sword you will set an Attack Damage of 7.
	 * 
	 * @param attackDamage
	 *            The Attack Damage you want the Tool to have.
	 */
	public void addAttackDamage(double attackDamage) {
		addAttribute(AttributeType.GENERIC_ATTACK_SPEED, attackDamage - 1, EquipmentSlot.HAND, Operation.ADD_NUMBER);
	}

	// === DAMAGE LORE === //

	/**
	 * The Fake Attack Lore will add a lore that displays Attack Speed and
	 * Attack Damage the same way that they are displayed in Items in Vanilla.
	 * This is great if you want to change the Attack Speed or Attack Damage and
	 * not have the Attribute Style text. Do not forget to also use
	 * {@link #setAttributeVisibility(Boolean)} to hide the Attribute Text from
	 * displaying. The strings used in the lore are customizable in the lang.yml
	 * file.
	 * 
	 * @param lore
	 *            Whether you want the lore to be enabled or not.
	 */
	public void setFakeAttackLore(Boolean lore) {
		fakeDamageLore = lore;
	}

	/**
	 * The Fake Attack Lore will add a lore that displays Attack Speed and
	 * Attack Damage the same way that they are displayed in Items in Vanilla.
	 * This is great if you want to change the Attack Speed or Attack Damage and
	 * not have the Attribute Style text. Do not forget to also use
	 * {@link #setAttributeVisibility(Boolean)} to hide the Attribute Text from
	 * displaying. The strings used in the lore are customizable in the lang.yml
	 * file.
	 * 
	 * @return A boolean of whether the Fake Attack Lore is enabled or not.
	 */
	public boolean hasFakeAttackLore() {
		return fakeDamageLore;
	}
}
