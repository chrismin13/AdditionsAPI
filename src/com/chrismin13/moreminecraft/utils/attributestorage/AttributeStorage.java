// The MIT License (MIT)
//
// Copyright (c) 2015 Kristian Stangeland
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation 
// files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, 
// modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the 
// Software is furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the 
// Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
// WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR 
// COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
// ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package com.chrismin13.moreminecraft.utils.attributestorage;

import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.Attribute;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.AttributeType;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.Operation;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.Slot;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Store meta-data in an ItemStack as attributes.
 * 
 * @author Kristian
 */
public class AttributeStorage {
	private ItemStack target;
	private final UUID uniqueKey;
	private Attributes attributes;

	private AttributeStorage(ItemStack target, UUID uniqueKey) {
		this.target = Preconditions.checkNotNull(target, "target cannot be NULL");
		this.uniqueKey = Preconditions.checkNotNull(uniqueKey, "uniqueKey cannot be NULL");
		this.attributes = new Attributes(target);
	}

	private AttributeStorage(ItemStack target, UUID uniqueKey, List<Attribute> attributes) {
		this.target = Preconditions.checkNotNull(target, "target cannot be NULL");
		this.uniqueKey = Preconditions.checkNotNull(uniqueKey, "uniqueKey cannot be NULL");
		this.attributes = new Attributes(target);
		if (!attributes.isEmpty()) {
			for (Attribute current : attributes)
				this.attributes.add(current);
		}
	}

	/**
	 * Construct a new attribute storage system.
	 * <p>
	 * The key must be the same in order to retrieve the same data.
	 * 
	 * @param target
	 *            - the item stack where the data will be stored.
	 * @param uniqueKey
	 *            - the unique key used to retrieve the correct data.
	 */
	public static AttributeStorage newTarget(ItemStack target, UUID uniqueKey) {
		return new AttributeStorage(target, uniqueKey);
	}

	/**
	 * Construct a new attribute storage system.
	 * <p>
	 * The key must be the same in order to retrieve the same data.
	 * 
	 * @param target
	 *            - the item stack where the data will be stored.
	 * @param uniqueKey
	 *            - the unique key used to retrieve the correct data.
	 * @param attributes
	 *            - the list of attributes that will be added to the item stack
	 */
	public static AttributeStorage newTarget(ItemStack target, UUID uniqueKey, List<Attribute> attributes) {
		return new AttributeStorage(target, uniqueKey, attributes);
	}

	/**
	 * Retrieve the data stored in the item's attribute.
	 * 
	 * @param defaultValue
	 *            - the default value to return if no data can be found.
	 * @return The stored data, or defaultValue if not found.
	 */
	public String getData(String defaultValue) {
		Attribute current = getAttribute(new Attributes(target), uniqueKey);
		return current != null ? current.getName() : defaultValue;
	}

	/**
	 * Determine if we are storing any data.
	 * 
	 * @return TRUE if we are, FALSE otherwise.
	 */
	public boolean hasData() {
		return getAttribute(new Attributes(target), uniqueKey) != null;
	}

	/**
	 * Set the data stored in the attributes.
	 * 
	 * @param data
	 *            - the data.
	 */
	public void setData(String data) {
		Attribute current = getAttribute(attributes, uniqueKey);

		if (current == null) {
			if (attributes.size() != 0 && attributes.get(0) != null && attributes.get(0).getSlot() != null) {
				attributes.add(Attribute.newBuilder().name(data).amount(getBaseDamage(target) - 1).uuid(uniqueKey)
						.operation(Operation.ADD_NUMBER).type(AttributeType.GENERIC_ATTACK_DAMAGE)
						.slot(Slot.valueOf(attributes.get(0).getSlot().toUpperCase())).build());
				attributes.add(Attribute.newBuilder().name(data).amount(getBaseSpeed(target) - 4).uuid(uniqueKey)
						.operation(Operation.ADD_NUMBER).type(AttributeType.GENERIC_ATTACK_SPEED)
						.slot(Slot.valueOf(attributes.get(0).getSlot().toUpperCase())).build());
			} else {
				attributes.add(Attribute.newBuilder().name(data).amount(getBaseDamage(target) - 1).uuid(uniqueKey)
						.operation(Operation.ADD_NUMBER).type(AttributeType.GENERIC_ATTACK_DAMAGE).slot(Slot.MAINHAND)
						.build());
				attributes.add(Attribute.newBuilder().name(data).amount(getBaseSpeed(target) - 4).uuid(uniqueKey)
						.operation(Operation.ADD_NUMBER).type(AttributeType.GENERIC_ATTACK_SPEED).slot(Slot.MAINHAND)
						.build());
			}
		} else {
			current.setName(data);
		}

		this.target = attributes.getStack();
	}

	/**
	 * Retrieve the base damage of the given item.
	 * 
	 * @param stack
	 *            - the stack.
	 * @return The base damage.
	 */
	public static double getBaseDamage(ItemStack stack) {
		return getBaseDamage(stack.getType());
	}

	/**
	 * Retrieve the base damage of the given item.
	 * 
	 * @param stack
	 *            - the stack.
	 * @return The base damage.
	 */
	public static double getBaseDamage(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Swords
		case WOOD_SWORD:
			return 4;
		case GOLD_SWORD:
			return 4;
		case STONE_SWORD:
			return 5;
		case IRON_SWORD:
			return 6;
		case DIAMOND_SWORD:
			return 7;
		// Axe
		case WOOD_AXE:
			return 7;
		case GOLD_AXE:
			return 7;
		case STONE_AXE:
			return 9;
		case IRON_AXE:
			return 9;
		case DIAMOND_AXE:
			return 9;
		// Pickaxe
		case WOOD_PICKAXE:
			return 2;
		case STONE_PICKAXE:
			return 3;
		case IRON_PICKAXE:
			return 4;
		case GOLD_PICKAXE:
			return 2;
		case DIAMOND_PICKAXE:
			return 5;
		// Spades
		case WOOD_SPADE:
			return 2.5;
		case STONE_SPADE:
			return 3.5;
		case IRON_SPADE:
			return 4.5;
		case GOLD_SPADE:
			return 2.5;
		case DIAMOND_SPADE:
			return 5.5;
		// Hoes
		case WOOD_HOE:
			return 1;
		case STONE_HOE:
			return 1;
		case IRON_HOE:
			return 1;
		case GOLD_HOE:
			return 1;
		case DIAMOND_HOE:
			return 1;
		default:
			return 1;

		}
	}

	/**
	 * Retrieve the base damage of the given item.
	 * 
	 * @param stack
	 *            - the stack.
	 * @return The base damage.
	 */
	public static double getBaseSpeed(ItemStack stack) {
		return getBaseSpeed(stack.getType());
	}

	/**
	 * Retrieve the base damage of the given item.
	 * 
	 * @param material
	 *            - the material.
	 * @return The base damage.
	 */
	public static double getBaseSpeed(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Swords
		case WOOD_SWORD:
			return 1.6;
		case GOLD_SWORD:
			return 1.6;
		case STONE_SWORD:
			return 1.6;
		case IRON_SWORD:
			return 1.6;
		case DIAMOND_SWORD:
			return 1.6;
		// Axe
		case WOOD_AXE:
			return 0.8;
		case GOLD_AXE:
			return 0.8;
		case STONE_AXE:
			return 0.9;
		case IRON_AXE:
			return 1;
		case DIAMOND_AXE:
			return 1;
		// Pickaxe
		case WOOD_PICKAXE:
			return 1.2;
		case STONE_PICKAXE:
			return 1.2;
		case IRON_PICKAXE:
			return 1.2;
		case GOLD_PICKAXE:
			return 1.2;
		case DIAMOND_PICKAXE:
			return 1.2;
		// Spades
		case WOOD_SPADE:
			return 1;
		case STONE_SPADE:
			return 1;
		case IRON_SPADE:
			return 1;
		case GOLD_SPADE:
			return 1;
		case DIAMOND_SPADE:
			return 1;
		// Hoes
		case WOOD_HOE:
			return 1;
		case STONE_HOE:
			return 2;
		case IRON_HOE:
			return 3;
		case GOLD_HOE:
			return 1;
		case DIAMOND_HOE:
			return 4;
		default:
			return 4;

		}
	}

	/**
	 * Retrieve the base armor of the given armor.
	 * 
	 * @param stack
	 *            - the stack.
	 * @return The base damage.
	 */
	public static double getBaseArmor(ItemStack stack) {
		return getBaseArmor(stack.getType());
	}

	/**
	 * Retrieve the base armor of the given armor.
	 * 
	 * @param material
	 *            - the material.
	 * @return The base damage.
	 */
	public static double getBaseArmor(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Leather Armor
		case LEATHER_HELMET:
			return 1;
		case LEATHER_CHESTPLATE:
			return 3;
		case LEATHER_LEGGINGS:
			return 2;
		case LEATHER_BOOTS:
			return 1;
		// Chainmail Armor
		case CHAINMAIL_HELMET:
			return 2;
		case CHAINMAIL_CHESTPLATE:
			return 5;
		case CHAINMAIL_LEGGINGS:
			return 4;
		case CHAINMAIL_BOOTS:
			return 1;
		// Iron Armor
		case IRON_HELMET:
			return 2;
		case IRON_CHESTPLATE:
			return 6;
		case IRON_LEGGINGS:
			return 5;
		case IRON_BOOTS:
			return 2;
		// Golden Armor
		case GOLD_HELMET:
			return 2;
		case GOLD_CHESTPLATE:
			return 5;
		case GOLD_LEGGINGS:
			return 4;
		case GOLD_BOOTS:
			return 1;
		// Diamond Armor
		case DIAMOND_HELMET:
			return 3;
		case DIAMOND_CHESTPLATE:
			return 8;
		case DIAMOND_LEGGINGS:
			return 6;
		case DIAMOND_BOOTS:
			return 3;
		default:
			return 0;

		}
	}

	/**
	 * Retrieve the base armor toughness of the given item.
	 * 
	 * @param stack
	 *            - the stack.
	 * @return The base damage.
	 */
	public static double getBaseArmorToughness(ItemStack stack) {
		return getBaseArmorToughness(stack.getType());
	}

	/**
	 * Retrieve the base armor toughness of the given item.
	 * 
	 * @param material
	 *            - the material.
	 * @return The base damage.
	 */
	// TODO: Disable for under 1.9.1
	public static double getBaseArmorToughness(Material material) {
		// Yes - we have to hard code these values. Cannot use
		// Operation.ADD_PERCENTAGE either.
		switch (material) {
		// Swords
		case DIAMOND_HELMET:
			return 2;
		case DIAMOND_CHESTPLATE:
			return 2;
		case DIAMOND_LEGGINGS:
			return 2;
		case DIAMOND_BOOTS:
			return 2;
		default:
			return 0;

		}
	}

	/**
	 * Retrieve the target stack. May have been changed.
	 * 
	 * @return The target stack.
	 */
	public ItemStack getTarget() {
		return target;
	}

	/**
	 * Retrieve an attribute by UUID.
	 * 
	 * @param attributes
	 *            - the attribute.
	 * @param id
	 *            - the UUID to search for.
	 * @return The first attribute associated with this UUID, or NULL.
	 */
	private Attribute getAttribute(Attributes attributes, UUID id) {
		if (attributes == null)
			return null;
		for (Attribute attribute : attributes.values()) {
			if (Objects.equal(attribute.getUUID(), id)) {
				return attribute;
			}
		}
		return null;
	}
}