package com.chrismin13.additionsapi.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.enums.ToolType;
import com.chrismin13.additionsapi.permissions.PermissionType;
import com.chrismin13.additionsapi.utils.EquipmentSlotUtils;
import com.chrismin13.additionsapi.utils.LangFileUtils;
import com.chrismin13.additionsapi.utils.MaterialUtils;
import com.chrismin13.additionsapi.utils.NumberUtils;
import com.comphenix.attribute.Attributes;
import com.comphenix.attribute.Attributes.Attribute;
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
 * The Tool Like Attributes will add a lore that displays Attack Speed and
 * Attack Damage the same way that they are displayed in Items in Vanilla. This
 * is great if you want to change the Attack Speed or Attack Damage and not have
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

	private boolean toolLikeAttributes = false;

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
		super(material, amount, durability, idName, ToolType.getToolType(material).getDurabilityMechanics(), ToolType.getToolType(material).getItemPermissions(new String(idName).replaceAll(":", "."), PermissionType.DENY));
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
	public CustomTool addAttackSpeed(double attackSpeed) {
		addAttribute(AttributeType.GENERIC_ATTACK_SPEED, attackSpeed - 4.0, EquipmentSlot.HAND, Operation.ADD_NUMBER);
		return this;
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
	public CustomTool addAttackDamage(double attackDamage) {
		addAttribute(AttributeType.GENERIC_ATTACK_DAMAGE, attackDamage - 1.0, EquipmentSlot.HAND, Operation.ADD_NUMBER);
		return this;
	}

	// === DAMAGE LORE === //

	/**
	 * The Tool Like Attributes will add a lore that displays Attack Speed and
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
	public CustomTool setToolLikeAttributes(Boolean lore) {
		toolLikeAttributes = lore;
		return this;
	}

	/**
	 * The Tool Like Attributes will add a lore that displays Attack Speed and
	 * Attack Damage the same way that they are displayed in Items in Vanilla.
	 * This is great if you want to change the Attack Speed or Attack Damage and
	 * not have the Attribute Style text. Do not forget to also use
	 * {@link #setAttributeVisibility(Boolean)} to hide the Attribute Text from
	 * displaying. The strings used in the lore are customizable in the lang.yml
	 * file.
	 * 
	 * @return A boolean of whether the Tool Like Attributes is enabled or not.
	 */
	public boolean hasToolLikeAttributes() {
		return toolLikeAttributes;
	}

	public ArrayList<String> getToolLikeAttributes() {
		return getToolLikeAttributes(null, null);
	}

	public ArrayList<String> getToolLikeAttributes(Map<Enchantment, Integer> enchants, Attributes attributes) {
		final ArrayList<String> attributeLore = new ArrayList<String>();

		Attributes newAttributes = new Attributes(new ItemStack(getMaterial()));

		if (attributes != null)
			for (Attribute attribute : attributes.values())
				newAttributes.add(attribute);
		else
			for (Attribute attribute : getAttributes())
				newAttributes.add(attribute);

		if (newAttributes.values().iterator().hasNext()) {
			HashMap<EquipmentSlot, HashMap<AttributeType, Double>> finalAttributes = new HashMap<EquipmentSlot, HashMap<AttributeType, Double>>();
			for (EquipmentSlot slot : EquipmentSlot.values())
				finalAttributes.put(slot, new HashMap<AttributeType, Double>());

			for (Attribute attribute : newAttributes.values()) {
				EquipmentSlot slot = EquipmentSlotUtils.valueFromAttribute(attribute.getSlot());
				HashMap<AttributeType, Double> map = finalAttributes.get(slot);
				AttributeType type = attribute.getAttributeType();
				if (map.containsKey(type)) {
					switch (attribute.getOperation()) {
					case ADD_NUMBER:
						map.put(type, map.get(type) + attribute.getAmount());
						break;
					case ADD_PERCENTAGE:
						map.put(type, map.get(type) * (1 + attribute.getAmount()));
						break;
					case MULTIPLY_PERCENTAGE:
						map.put(type, map.get(type) * attribute.getAmount());
						break;
					}
				} else {
					switch (attribute.getOperation()) {
					case ADD_NUMBER:
						map.put(type, attribute.getAmount());
						break;
					case ADD_PERCENTAGE:
						map.put(type, type.getBaseValue() * (1 + attribute.getAmount()));
						break;
					case MULTIPLY_PERCENTAGE:
						map.put(type, type.getBaseValue() * attribute.getAmount());
						break;
					}
				}
				finalAttributes.put(slot, map);
			}
			for (EquipmentSlot slot : finalAttributes.keySet()) {
				for (AttributeType type : finalAttributes.get(slot).keySet()) {
					/*
					 * Check if the lore already contains text for that slot, if
					 * not add it.
					 */
					if (!attributeLore.contains(ChatColor.GRAY + EquipmentSlotUtils.valueFromLangFile(slot))) {
						attributeLore.add(ChatColor.GRAY + EquipmentSlotUtils.valueFromLangFile(slot));
					}
					/*
					 * Needed because this is how it's done for attributes in
					 * Vanilla. That being said, this hides an accuracy bug that
					 * occures with the attributes from, what I can tell to be,
					 * a conversion from double to float in game. This is not
					 * something that can be fixed easily as it is not caused by
					 * this API. Confirmed with Minecraft code that it's
					 * expected behaviour. For example, this is the amount of
					 * Attack Speed for the Swords: -2.4000000953674316D Full
					 * rant is available here:
					 * https://twitter.com/SupMushroomSoup/status/
					 * 853292658298671106
					 */
					double amount = NumberUtils.round(finalAttributes.get(slot).get(type), 2);
					String attributeName = LangFileUtils.get(type);

					// We must also consider the base values of each
					// Attribute.
					amount += type.getBaseValue();
					amount = NumberUtils.round(amount, 2);

					if (type.equals(AttributeType.GENERIC_ATTACK_DAMAGE) && enchants != null && !enchants.isEmpty()
							&& enchants.containsKey(Enchantment.DAMAGE_ALL)) {
						final int level = enchants.get(Enchantment.DAMAGE_ALL);
						if (level == 1) {
							amount += 1;
						} else if (level > 1) {
							amount = amount + 1 + (level - 1) * 0.5;
						}
					}
					/*
					 * Checking to remove the decimals at the end if the number
					 * does not have any.
					 */
					if ((amount == Math.floor(amount)) && !Double.isInfinite(amount)) {
						attributeLore.add(ChatColor.GRAY + " " + Integer.toString((int) amount) + " " + attributeName);
					} else {
						attributeLore.add(ChatColor.GRAY + " " + Double.toString(amount) + " " + attributeName);
					}
				}
			}
		} else {

			attributeLore.add(ChatColor.GRAY + EquipmentSlotUtils.valueFromLangFile(EquipmentSlot.HAND));

			double speed = NumberUtils.round(MaterialUtils.getBaseSpeed(getMaterial()), 2);
			/*
			 * Checking to remove the decimals at the end if the number does not
			 * have any.
			 */
			if ((speed == Math.floor(speed)) && !Double.isInfinite(speed)) {
				attributeLore.add(ChatColor.GRAY + " " + Integer.toString((int) speed) + " "
						+ LangFileUtils.get(AttributeType.GENERIC_ATTACK_SPEED));
			} else {
				attributeLore.add(ChatColor.GRAY + " " + Double.toString(speed) + " "
						+ LangFileUtils.get(AttributeType.GENERIC_ATTACK_SPEED));
			}

			double damage = NumberUtils.round(MaterialUtils.getBaseDamage(getMaterial()), 2);
			/*
			 * Checking to remove the decimals at the end if the number does not
			 * have any.
			 */
			if ((damage == Math.floor(damage)) && !Double.isInfinite(damage)) {
				attributeLore.add(ChatColor.GRAY + " " + Integer.toString((int) damage) + " "
						+ LangFileUtils.get(AttributeType.GENERIC_ATTACK_DAMAGE));
			} else {
				attributeLore.add(ChatColor.GRAY + " " + Double.toString(damage) + " "
						+ LangFileUtils.get(AttributeType.GENERIC_ATTACK_DAMAGE));
			}
		}
		return attributeLore;
	}
}
