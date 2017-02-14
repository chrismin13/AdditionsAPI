package com.chrismin13.moreminecraft.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import com.chrismin13.moreminecraft.api.recipes.CustomRecipes;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.utils.Debug;
import com.chrismin13.moreminecraft.utils.MaterialUtils;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.Attribute;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.AttributeType;
import com.chrismin13.moreminecraft.utils.attributestorage.Attributes.Operation;

public class CustomItem implements Cloneable {

	// Required variables
	private Material material = Material.AIR;
	private int amount = 0;
	private short durability = 0;
	private String customItemIdName;
	private ItemType itemType;

	// Unbreakable
	private boolean unbreakable = false;
	private boolean unbreakableVisibility = false;

	// Name
	private String displayName = null;

	// Enchanting
	private boolean canBeEnchanted = false;
	private Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
	private List<Enchantment> forbiddenEnchantments = new ArrayList<Enchantment>();

	// Recipes
	private boolean canBeCombinedInCrafting = false;
	private CustomRecipes recipes = new CustomRecipes();

	// Attributes
	private List<Attribute> attributes = new ArrayList<Attribute>();
	private final UUID attributeStorageUUID = UUID.fromString("8c16d72b-d950-410c-b7d1-eeed86e734c7");
	
	// Lore
	private List<String> lore = new ArrayList<String>();

	// Durability
	private boolean addFakeDurability = false;
	private int fakeDurability = 0;

	// ItemFlags
	private List<ItemFlag> itemFlags = new ArrayList<ItemFlag>();
	
	// Textures
	private boolean customTexture = true;

	/**
	 * Create a new Custom Item from the specified Material, Amount, Durability
	 * Short and ItemType
	 * 
	 * @param material
	 * @param amount
	 * @param durability
	 * @param itemType
	 */
	public CustomItem(final Material material, final int amount, final short durability, final String customItemIdName,
			final ItemType itemType) {
		this.material = material;
		this.amount = amount;
		this.durability = durability;
		this.customItemIdName = customItemIdName;
		this.itemType = itemType;
	}

	// === MATERIAL, AMOUNT & DURABILITY === //

	/**
	 * Get the Material of the Custom Item
	 * 
	 * @return Material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Get the Amount of the Custom Item
	 * 
	 * @return Amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Get the Durability Short of the Custom Item
	 * 
	 * @return Durability Short
	 */
	public short getDurability() {
		return durability;
	}

	/**
	 * Set the Material of the Custom Item
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

	/**
	 * Set the Amount of the Custom Item
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Set the Durability of the Custom Item
	 */
	public void setDurability(int durability) {
		this.durability = (short) durability;
	}

	/**
	 * Set the Durability Short of the Custom Item
	 */
	public void setDurability(Short durabilityShort) {
		this.durability = durabilityShort;
	}

	// === ITEM TYPE === //

	public ItemType getItemType() {
		return itemType;
	}

	// === UNBREAKABLE === //

	/**
	 * Get a boolean of whether the Custom Item is Unbreakable or not
	 * 
	 * @return Boolean
	 */
	public boolean isUnbreakable() {
		return unbreakable;
	}

	/**
	 * Set the Custom Item to be Unbreakable or not
	 */
	public void setUnbreakable(Boolean unbreakable) {
		this.unbreakable = unbreakable;
	}

	/**
	 * Get a boolean of whether the Custom Item has a visible Unbreakable tag in
	 * the lore or not
	 * 
	 * @return Boolean
	 */
	public boolean getUnbreakableVisibility() {
		return unbreakableVisibility;
	}

	/**
	 * Set the Custom Item to have a Unbreakable Lore tag or not
	 */
	public void setUnbreakableVisibility(Boolean unbreakableVisibility) {
		this.unbreakableVisibility = unbreakableVisibility;
	}

	// === DISPLAY NAME === //

	/**
	 * Get the display name of the CustomItem
	 * 
	 * @return String
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Set the display name of the Custom Item
	 */
	public void setDisplayName(String displayName) {
		// TODO: Fix the bug where an r is added in the anvil
		this.displayName = ChatColor.RESET + displayName;
	}

	/**
	 * Remove the display name of the Custom Item
	 */
	public void removeDisplayName() {
		this.displayName = null;
	}

	// === ID NAME === //

	/**
	 * Get the Custom Item's ID Name
	 * 
	 * @return Integer - the ID Name
	 */
	public String getCustomItemIdName() {
		return customItemIdName;
	}

	/**
	 * Set the Custom Item's ID Name
	 */
	public void setCustomItemIdName(String customItemIdName) {
		this.customItemIdName = customItemIdName;
	}

	// === ENCHANTMENTS === //

	/**
	 * Get a List of the Enchantments that are not allowed for this item
	 */
	public List<Enchantment> getForbidenEnchantments() {
		return forbiddenEnchantments;
	}

	/**
	 * Add an Enchantment to the list of forbidden enchants.
	 */
	public void addForbiddenEnchantment(Enchantment forbiddenEnchantment) {
		forbiddenEnchantments.add(forbiddenEnchantment);
	}

	/**
	 * Add an Enchantment list to the list of forbidden enchants.
	 */
	public void addForbiddenEnchantment(List<Enchantment> forbiddenEnchantments) {
		this.forbiddenEnchantments.addAll(forbiddenEnchantments);
	}

	/**
	 * Get if the Item can be Enchanted
	 */
	public boolean isEnchantable() {
		return canBeEnchanted;
	}

	/**
	 * Set if the item can be Enchanted
	 */
	public void setEnchantable(boolean canBeEnchanted) {
		this.canBeEnchanted = canBeEnchanted;
	}

	// === RECIPES === //

	/**
	 * Get if the Item can be Combined in a Crafting Table
	 */
	public boolean isCombinableInCrafting() {
		return canBeCombinedInCrafting;
	}

	/**
	 * Set if the item can be Combined in a Crafting Table
	 */
	public void setCombinedInCrafting(boolean canBeCombined) {
		canBeCombinedInCrafting = canBeCombined;
	}

	public CustomRecipes getCustomRecipes() {
		return recipes;
	}

	public void setCustomRecipes(CustomRecipes recipes) {
		this.recipes = recipes;
	}

	// === ATTRIBUTES === //

	public void addAttribute(AttributeType type, Double amount, EquipmentSlot slot, Operation operation) {
		addAttribute(type, amount, slot, operation, attributeStorageUUID);
	}

	public void addAttribute(AttributeType type, Double amount, EquipmentSlot slot, Operation operation, UUID uuid) {
		if (slot == EquipmentSlot.HEAD || slot == EquipmentSlot.CHEST || slot == EquipmentSlot.FEET
				|| slot == EquipmentSlot.LEGS) {
			attributes.add(Attribute.newBuilder().name("TBD").amount(amount).uuid(UUID.randomUUID())
					.operation(operation).type(type).slot(slot).build());
		} else {
			attributes.add(Attribute.newBuilder().name("TBD").amount(amount).uuid(uuid).operation(operation).type(type)
					.slot(slot).build());
		}
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	// === ITEM FLAGS === //

	public void addItemFlag(ItemFlag itemFlag) {
		itemFlags.add(itemFlag);
	}

	public List<ItemFlag> getItemFlags() {
		return itemFlags;
	}

	// === FAKE DURABILITY === //

	public void addFakeDurability(Boolean durability) {
		addFakeDurability = durability;
	}

	public boolean hasFakeDurability() {
		return addFakeDurability;
	}

	public void setFakeDurability(int durability) {
		fakeDurability = durability;
	}

	public int getFakeDurability() {
		return fakeDurability;
	}

	// === ENCHANTMENTS === //

	public Map<Enchantment, Integer> getEnchantmnets() {
		return enchantments;
	}

	public void addEnchantment(Enchantment enchantment, int level) {
		enchantments.put(enchantment, level);
	}

	// === LORE === //

	public void addLoreLine(String lore) {
		this.lore.add(lore);
	}

	public void setLore(List<String> string) {
		this.lore = string;
	}

	public List<String> getLore() {
		return lore;
	}

	public List<String> getFullLore(Map<Enchantment, Integer> enchantments, int durability) {
		// Lore

		List<String> loreToAdd = new ArrayList<String>();

		loreToAdd.addAll(lore);

		// Fake Damage Lore
		// TODO: Add language file

		if (this instanceof CustomTool && ((CustomTool) this).getFakeDamageLore()) {

			CustomTool cTool = ((CustomTool) this);

			Double attackSpeed = 0D;
			Double attackDamage = 0D;

			Boolean hasSpeed = false;
			Boolean hasDamage = false;

			if (cTool.getAttackSpeed() != null) {
				attackSpeed = cTool.getAttackSpeed();
				hasSpeed = true;
			}

			if (cTool.getAttackDamage() != null) {
				attackDamage = cTool.getAttackDamage();
				hasDamage = true;
			}

			for (Attribute attribute : attributes) {
				if (attribute.getAttributeType() != null) {

					AttributeType type = attribute.getAttributeType();
					Double amount = attribute.getAmount();

					if (type == AttributeType.GENERIC_ATTACK_SPEED && !hasSpeed) {
						attackSpeed = amount + 4;
						hasSpeed = true;
					} else if (type == AttributeType.GENERIC_ATTACK_DAMAGE && !hasDamage) {
						attackDamage = amount + 1;
						hasDamage = true;
					}
				}
			}

			ItemStack fromMaterial = new ItemStack(this.getMaterial());

			if (!hasSpeed)
				attackSpeed = MaterialUtils.getBaseSpeed(fromMaterial);
			if (!hasDamage)
				attackDamage = MaterialUtils.getBaseDamage(fromMaterial);

			loreToAdd.add("");
			loreToAdd.add(ChatColor.GRAY + "When in main hand:");

			if (enchantments != null) {
				if (enchantments.containsKey(Enchantment.DAMAGE_ALL)) {
					int level = enchantments.get(Enchantment.DAMAGE_ALL);
					if (level == 1) {
						attackDamage += 1;
					} else {
						attackDamage = attackDamage + 1 + ((level - 1) * 0.5);
					}
				}
			}

			if ((attackSpeed == Math.floor(attackSpeed)) && !Double.isInfinite(attackSpeed)) {
				loreToAdd.add(ChatColor.GRAY + " " + Integer.toString(attackSpeed.intValue()) + " Attack Speed");
			} else {
				loreToAdd.add(ChatColor.GRAY + " " + attackSpeed.toString() + " Attack Speed");
			}
			if ((attackDamage == Math.floor(attackDamage)) && !Double.isInfinite(attackDamage)) {
				loreToAdd.add(ChatColor.GRAY + " " + Integer.toString(attackDamage.intValue()) + " Attack Damage");
			} else {
				loreToAdd.add(ChatColor.GRAY + " " + attackDamage.toString() + " Attack Damage");
			}
		}
		if (addFakeDurability) {
			loreToAdd.add(ChatColor.GRAY + "Durability: " + Integer.toString(durability) + " / "
					+ Integer.toString(fakeDurability));
		}

		return loreToAdd;
	}

	@Override
	protected CustomItem clone() {
		try {
			return (CustomItem) super.clone();
		} catch (CloneNotSupportedException e) {
			Debug.sayTrueError("Cloning not supported!");
			e.printStackTrace();
		}
		return null;
	}

	public UUID getAttributeStorageUUID() {
		return attributeStorageUUID;
	}

	public boolean hasCustomTexture() {
		return customTexture;
	}

	public void setCustomTexture(boolean customTexture) {
		this.customTexture = customTexture;
	}

}
