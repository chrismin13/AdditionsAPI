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

package com.comphenix.attribute;

import java.util.List;
import java.util.UUID;

import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.utils.EquipmentSlotUtils;
import com.chrismin13.moreminecraft.utils.MaterialUtils;
import com.comphenix.attribute.Attributes.Attribute;
import com.comphenix.attribute.Attributes.AttributeType;
import com.comphenix.attribute.Attributes.Operation;
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
				EquipmentSlot slot = EquipmentSlotUtils.valueFromAttribute(attributes.get(0).getSlot());
				attributes.add(Attribute.newBuilder().name(data).amount(MaterialUtils.getBaseDamage(target) - 1).uuid(uniqueKey)
						.operation(Operation.ADD_NUMBER).type(AttributeType.GENERIC_ATTACK_DAMAGE).slot(slot).build());
				attributes.add(Attribute.newBuilder().name(data).amount(MaterialUtils.getBaseSpeed(target) - 4).uuid(uniqueKey)
						.operation(Operation.ADD_NUMBER).type(AttributeType.GENERIC_ATTACK_SPEED).slot(slot).build());
			} else {
				attributes.add(Attribute.newBuilder().name(data).amount(MaterialUtils.getBaseDamage(target) - 1).uuid(uniqueKey)
						.operation(Operation.ADD_NUMBER).type(AttributeType.GENERIC_ATTACK_DAMAGE).slot(EquipmentSlot.HAND)
						.build());
				attributes.add(Attribute.newBuilder().name(data).amount(MaterialUtils.getBaseSpeed(target) - 4).uuid(uniqueKey)
						.operation(Operation.ADD_NUMBER).type(AttributeType.GENERIC_ATTACK_SPEED).slot(EquipmentSlot.HAND)
						.build());
			}
		} else {
			current.setName(data);
		}

		this.target = attributes.getStack();
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