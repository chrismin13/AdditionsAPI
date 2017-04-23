package com.chrismin13.moreminecraft.items;

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

import com.chrismin13.moreminecraft.durability.ItemDurability;
import com.chrismin13.moreminecraft.recipes.CustomRecipe;
import com.chrismin13.moreminecraft.recipes.CustomShapedRecipe;
import com.chrismin13.moreminecraft.enums.ItemType;
import com.chrismin13.moreminecraft.items.textured.CustomTexturedItem;
import com.chrismin13.moreminecraft.utils.Debug;
import com.chrismin13.moreminecraft.utils.LangFileUtils;
import com.chrismin13.moreminecraft.utils.MaterialUtils;
import com.chrismin13.moreminecraft.utils.MathUtils;
import com.comphenix.attribute.Attributes.Attribute;
import com.comphenix.attribute.Attributes.AttributeType;
import com.comphenix.attribute.Attributes.Operation;

/**
 * A Custom Item with multiple values that can be changed. <br>
 * <br>
 * If you want Custom Items with Custom Textures, see {@link CustomTexturedItem}
 * <br>
 * If you want to use this for tools (Sword, Axe, Pickaxe, Shovel, Hoe), use
 * {@link CustomTool} instead. There are similar classes available for other
 * ItemTypes too, such as Leather Armor. <br>
 * <br>
 * In order to use the Custom Items in-game, you can register them using the
 * MoreMinecraftAPIInitializationEvent. That way, Recipes and Custom Textures
 * will be functional. <br>
 * If you want to get an ItemStack for giving it to the player, make a new
 * {@link CustomItemStack} and get the ItemStack from there.
 * 
 * @author chrismin13
 *
 */
public class CustomItem implements Cloneable, Comparable<CustomItem> {

	// Required variables
	private Material material = Material.AIR;
	private int amount = 0;
	private short durability = 0;
	private String idName;

	// Unbreakable
	private boolean unbreakable = false;

	// Name
	private String displayName = null;

	// Enchanting
	private boolean canBeEnchanted = false;
	private Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
	private List<Enchantment> forbiddenEnchantments = new ArrayList<Enchantment>();

	// Recipes
	private boolean canBeCombinedInCrafting = false;
	private List<CustomRecipe> recipes = new ArrayList<CustomRecipe>();

	// Attributes
	private List<Attribute> attributes = new ArrayList<Attribute>();
	private final UUID attributeStorageUUID = UUID.fromString("8c16d72b-d950-410c-b7d1-eeed86e734c7");

	// Lore
	private List<String> lore = new ArrayList<String>();

	// Durability
	private ItemDurability itemDurability;
	private int fakeDurability = 0;

	// ItemFlags
	private List<ItemFlag> itemFlags = new ArrayList<ItemFlag>();

	/**
	 * Create a new Custom Item.
	 * 
	 * @param material
	 *            the material of the CustomItem
	 * @param amount
	 *            the amount of the CustomItem
	 * @param durability
	 *            the durability of the CustomItem
	 * @param idName
	 *            the Custom Item's ID Name. This MUST BE SIMILAR to
	 *            "vanilla_additions:emerald_sword" and is saved in the
	 *            ItemStack so you can easily detect which Custom Item it is.
	 */
	public CustomItem(final Material material, final int amount, final short durability, final String idName) {
		this(material, amount, durability, idName, ItemType.getItemType(material).getItemDurability());
	}

	/**
	 * Create a new Custom Item.
	 * 
	 * @param material
	 *            the material of the CustomItem
	 * @param amount
	 *            the amount of the CustomItem
	 * @param durability
	 *            the durability of the CustomItem
	 * @param itemDurability
	 *            the {@link ItemDurability} Durability System that will be used
	 *            for the Item. This will modify the way that durability drops
	 *            for the Custom Item. All Classes under package
	 *            com.chrismin13.moreminecraft.durability are valid.
	 * @param idName
	 *            the Custom Item's ID Name. This MUST BE SIMILAR to
	 *            "vanilla_additions:emerald_sword" and is saved in the
	 *            ItemStack so you can easily detect which Custom Item it is.
	 */
	public CustomItem(final Material material, final int amount, final short durability, final String idName,
			final ItemDurability itemDurability) {
		this.material = material;
		this.amount = amount;
		this.durability = durability;
		this.idName = idName;
		this.itemDurability = itemDurability;
	}

	// === MATERIAL, AMOUNT & DURABILITY === //

	/**
	 * @return The Material of the Custom Item
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @return The Amount of the Custom Item
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @return The Durability of the Custom Item. This will be the durability of
	 *         the default texture for a {@link CustomTexturedItem}. If the
	 *         TexturedItem was not processed during the
	 *         MoreMinecraftAPIInitializationEvent, it will be always 0.
	 */
	public short getDurability() {
		return durability;
	}

	/**
	 * Set the Material of the Custom Item
	 */
	public CustomItem setMaterial(Material material) {
		this.material = material;
		return this;
	}

	/**
	 * Set the Amount of the Custom Item
	 */
	public CustomItem setAmount(int amount) {
		this.amount = amount;
		return this;
	}

	/**
	 * Set the Durability of the Custom Item. You may not use this method on
	 * TexturedItems as it will mess up the durability of the default texture.
	 */
	public CustomItem setDurability(short durabilityShort) {
		this.durability = durabilityShort;
		return this;
	}

	// === ITEM TYPE === //

	public ItemType getItemType() {
		return ItemType.getItemType(material);
	}

	// === UNBREAKABLE === //

	/**
	 * @return Boolean that states if the item is unbreakable or not. However,
	 *         just because the Item is unbreakable doesn't mean that it will
	 *         not have it's durability reduced as it can have Fake Durability.
	 */
	public boolean isUnbreakable() {
		return unbreakable;
	}

	/**
	 * Set the Custom Item to be Unbreakable or not. However, just because you
	 * set Item to be unbreakable doesn't mean that it will not have it's
	 * durability reduced as it can have Fake Durability.
	 */
	public CustomItem setUnbreakable(Boolean unbreakable) {
		this.unbreakable = unbreakable;
		return this;
	}

	/**
	 * 
	 * @return Boolean of whether the unbreakable tag in the item lore is
	 *         visible or not. If it is true, then the tag is visible. If it is
	 *         invisible then the CustomItem includes the ItemFlag
	 *         HIDE_UNBREAKABLE
	 */
	public boolean getUnbreakableVisibility() {
		return !itemFlags.contains(ItemFlag.HIDE_UNBREAKABLE);
	}

	/**
	 * Set whether the unbreakable tag in the item lore will be visible or not.
	 * If it is set true, then the tag will be visible. If it is set to be
	 * hidden then the CustomItem includes the ItemFlag HIDE_UNBREAKABLE
	 */
	public CustomItem setUnbreakableVisibility(boolean unbreakableVisibility) {
		if (!unbreakableVisibility) {
			if (!itemFlags.contains(ItemFlag.HIDE_UNBREAKABLE)) {
				itemFlags.add(ItemFlag.HIDE_UNBREAKABLE);
			}
		} else if (itemFlags.contains(ItemFlag.HIDE_UNBREAKABLE)) {
			itemFlags.remove(ItemFlag.HIDE_UNBREAKABLE);
		}
		return this;
	}

	// === DISPLAY NAME === //

	/**
	 * @return The Display Name of the Custom Item.
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Set the display name of the Custom Item. By default, it will add
	 * ChatColor.RESET in front to prevent the slanted text. You can override
	 * that by adding another ChatColor afterwards, however be aware that it
	 * will show up if you rename the item in an anvil.
	 */
	public CustomItem setDisplayName(String displayName) {
		this.displayName = ChatColor.RESET + displayName;
		return this;
	}

	// === ID NAME === //

	/**
	 * @return String - the CustomItem's IdName. This has a similar format to
	 *         the ones found in vanila, e.g. vanilla_additions:emerald_sword,
	 *         and are saved using an nbt tag in the ItemStack. You can get the
	 *         IdName from any itemstack by using CustomItemUtils.getIdName or
	 *         creating a {@link CustomItemStack} and getting the idName from
	 *         there.
	 */
	public String getIdName() {
		return idName;
	}

	/**
	 * Set the CustomItem's IdName. This has a similar format to the ones found
	 * in vanila, e.g. vanilla_additions:emerald_sword, and are saved using an
	 * nbt tag in the ItemStack. You can get the IdName from any itemstack by
	 * using CustomItemUtils.getIdName or creating a {@link CustomItemStack} and
	 * getting the idName from there.
	 */
	public CustomItem setIdName(String IdName) {
		this.idName = IdName;
		return this;
	}

	// === ENCHANTMENTS === //

	/**
	 * Get a List of the Enchantments that are not allowed for this item. These
	 * will be checked when using an Enchantment Table or an Anvil to add
	 * Enchantments.
	 */
	public List<Enchantment> getForbidenEnchantments() {
		return forbiddenEnchantments;
	}

	/**
	 * Add an Enchantment to the list of forbidden enchants. Any Enchantments
	 * that are added will not be able to be added to the ItemStack neither from
	 * an Enchantment Table, nor from an Anvil.
	 */
	public CustomItem addForbiddenEnchantment(Enchantment forbiddenEnchantment) {
		forbiddenEnchantments.add(forbiddenEnchantment);
		return this;
	}

	/**
	 * Add an Enchantment list to the list of forbidden enchants. Any
	 * Enchantments that are added will not be able to be added to the ItemStack
	 * neither from an Enchantment Table, nor from an Anvil.
	 */
	public CustomItem addForbiddenEnchantment(List<Enchantment> forbiddenEnchantments) {
		this.forbiddenEnchantments.addAll(forbiddenEnchantments);
		return this;
	}

	/**
	 * @return boolean - If the Item can be Enchanted. If it cannot be enchanted
	 *         then after choosing an Enchantment in an Enchantment Table or
	 *         adding a Book in an Anvil, it will not do anything.
	 */
	public boolean isEnchantable() {
		return canBeEnchanted;
	}

	/**
	 * Set if the item can be Enchanted. If it cannot be enchanted then after
	 * choosing an Enchantment in an Enchantment Table or adding a Book in an
	 * Anvil, it will not do anything.
	 */
	public CustomItem setEnchantable(boolean canBeEnchanted) {
		this.canBeEnchanted = canBeEnchanted;
		return this;
	}

	// === RECIPES === //

	/**
	 * @return boolean - If the Item can be Combined in a Crafting Table. If it
	 *         cannot be, then adding two of the same items in a Crafting Table
	 *         will not give any result.
	 */
	public boolean isCombinableInCrafting() {
		return canBeCombinedInCrafting;
	}

	/**
	 * Set if the item can be Combined in a Crafting Table. If it cannot be,
	 * then adding two of the same items in a Crafting Table will not give any
	 * result.
	 */
	public CustomItem setCombinedInCrafting(boolean canBeCombined) {
		canBeCombinedInCrafting = canBeCombined;
		return this;
	}

	/**
	 * @return {@link List} - Returns a List of CustomShapedRecipes. These
	 *         recipes behave a lot like the Custom Recipes already found in
	 *         Bukkit and are converted to that format upon initialization. See
	 *         {@link CustomShapedRecipe}.
	 */
	public List<CustomRecipe> getCustomRecipes() {
		return recipes;
	}

	/**
	 * Adds the specified recipe to the list of CustomShapedRecipes. These
	 * recipes behave a lot like the Custom Recipes already found in Bukkit and
	 * are converted to that format upon initialization. See
	 * {@link CustomShapedRecipe}.
	 */
	public CustomItem addCustomRecipe(CustomRecipe recipe) {
		this.recipes.add(recipe);
		return this;
	}

	/**
	 * Adds all of the specified recipes to the list of CustomShapedRecipes.
	 * These recipes behave a lot like the Custom Recipes already found in
	 * Bukkit and are converted to that format upon initialization. See
	 * {@link CustomShapedRecipe}.
	 * @param <T>
	 */
	public CustomItem addAllCustomRecipes(List<? extends CustomRecipe> recipes) {
		this.recipes.addAll(recipes);
		return this;
	}

	// === ATTRIBUTES === //

	/**
	 * 
	 * @return Boolean of whether the attribute text in the item lore is visible
	 *         or not. If it is true, then the text is visible. If it is
	 *         invisible then the CustomItem includes the ItemFlag
	 *         HIDE_Attribute
	 */
	public boolean getAttributeVisibility() {
		return !itemFlags.contains(ItemFlag.HIDE_ATTRIBUTES);
	}

	/**
	 * Set whether the attribute text in the item lore will be visible or not.
	 * If it is set true, then the text will be visible. If it is set to be
	 * hidden then the CustomItem includes the ItemFlag HIDE_ATTRIBUTES
	 */
	public CustomItem setAttributeVisibility(boolean attributeVisibility) {
		if (!attributeVisibility) {
			if (!itemFlags.contains(ItemFlag.HIDE_ATTRIBUTES)) {
				itemFlags.add(ItemFlag.HIDE_ATTRIBUTES);
			}
		} else if (itemFlags.contains(ItemFlag.HIDE_ATTRIBUTES)) {
			itemFlags.remove(ItemFlag.HIDE_ATTRIBUTES);
		}
		return this;
	}

	/**
	 * Adds an Attribute to the CustomItem. If you don't know what they do or
	 * how they work, check out the Minecraft wiki. The UUID will be the one
	 * that is found by default in Minecraft for the specified Slot. The name
	 * will be, as a placeholder, TBD (which means To Be Determined).
	 * 
	 * @param type
	 *            The attribute that will be added.
	 * @param amount
	 *            The amount of the attribute. Can also be a negative value.
	 * @param slot
	 *            The {@link EquipmentSlot} at which the Attribute will be
	 *            applied. For example, the EquipmentSlot HAND will apply the
	 *            specified Attribute to the Main Hand only.
	 * @param operation
	 *            The math operation that will be used for the amount specified.
	 */
	public CustomItem addAttribute(AttributeType type, double amount, EquipmentSlot slot, Operation operation) {
		switch (slot) {
		case HEAD:
			addAttribute(type, amount, slot, operation, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
			return this;
		case CHEST:
			addAttribute(type, amount, slot, operation, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
			return this;
		case LEGS:
			addAttribute(type, amount, slot, operation, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
			return this;
		case FEET:
			addAttribute(type, amount, slot, operation, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
			return this;
		// TODO
		default:
			addAttribute(type, amount, slot, operation, attributeStorageUUID);
			return this;
		}
	}

	/**
	 * Adds an Attribute to the CustomItem. If you don't know what they do or
	 * how they work, check out the Minecraft wiki. The name will be, as a
	 * placeholder, TBD (which means To Be Determined).
	 * 
	 * @param type
	 *            The attribute that will be added.
	 * @param amount
	 *            The amount of the attribute. Can also be a negative value.
	 * @param slot
	 *            The {@link EquipmentSlot} at which the Attribute will be
	 *            applied. For example, the EquipmentSlot HAND will apply the
	 *            specified Attribute to the Main Hand only.
	 * @param operation
	 *            The math operation that will be used for the amount specified.
	 * @param uuid
	 *            The UUID that the Attribute will be saved with in the
	 *            ItemStack.
	 */
	public CustomItem addAttribute(AttributeType type, double amount, EquipmentSlot slot, Operation operation,
			UUID uuid) {
		// TODO: Remove the name TBD
		attributes.add(Attribute.newBuilder().name("TBD").amount(amount).uuid(uuid).operation(operation).type(type)
				.slot(slot).build());
		return this;
	}

	/**
	 * @return {@link List} - A list of Attributes that will be included with
	 *         the Custom Item. If you don't know what they do or how they work,
	 *         check out the Minecraft wiki.
	 */
	public List<Attribute> getAttributes() {
		return attributes;
	}

	// === ITEM FLAGS === //

	/**
	 * Add an ItemFlag to the CustomItem.
	 */
	public CustomItem addItemFlag(ItemFlag itemFlag) {
		itemFlags.add(itemFlag);
		return this;
	}

	/**
	 * @return {@link List} - A list of all ItemFlags included in the
	 *         CustomItem.
	 */
	public List<ItemFlag> getItemFlags() {
		return itemFlags;
	}

	// === FAKE DURABILITY === //

	/**
	 * Sets the Durability for the Fake Durability lore. The fake durability
	 * lore offers a near perfect recreation of the Minecraft durability
	 * mechanics but hidden in the lore. This way, Textured Items that have to
	 * be unbreakable can still have durability mechanics.
	 * 
	 * @param durability
	 *            The maximum durability of the CustomItem and the one that it
	 *            will be starting with. Any number above 0 will enable the fake
	 *            durability lore. If it is 0 then it will not be enabled.
	 */
	public CustomItem setFakeDurability(int durability) {
		fakeDurability = durability;
		return this;
	}

	/**
	 * @return int - The maximum fake durability of the CustomItem. If this is
	 *         above 0, then the Fake Durability lore is enabled. The fake
	 *         durability lore offers a near perfect recreation of the Minecraft
	 *         durability mechanics but hidden in the lore. This way, Textured
	 *         Items that have to be unbreakable can still have durability
	 *         mechanics.
	 */
	public int getFakeDurability() {
		return fakeDurability;
	}

	/**
	 * @return boolean - Whether the Custom Item will include the Fake
	 *         Durability lore or not. If the fake durability above 0, then the
	 *         Fake Durability lore is enabled. The fake durability lore offers
	 *         a near perfect recreation of the Minecraft durability mechanics
	 *         but hidden in the lore. This way, Textured Items that have to be
	 *         unbreakable can still have durability mechanics.
	 */
	public boolean hasFakeDurability() {
		return fakeDurability > 0;
	}

	// === ENCHANTMENTS === //

	/**
	 * @return {@link Map} - A map of the Enchantments included in the
	 *         CustomItem and their level.
	 */
	public Map<Enchantment, Integer> getEnchantmnets() {
		return enchantments;
	}

	/**
	 * Adds an Enchantment to the CustomItem.
	 * 
	 * @param enchantment
	 *            The Enchantment to add.
	 * @param level
	 *            The level of the Enchantment. Set this to 1 if the Enchantment
	 *            doesn't have multiple levels by default.
	 */
	public CustomItem addEnchantment(Enchantment enchantment, int level) {
		enchantments.put(enchantment, level);
		return this;
	}

	// === LORE === //

	/**
	 * Adds a line to the Lore that will be included with the CustomItem. This
	 * will go above the CustomTool's Fake Attack Speed and Attack Damage Lore
	 * and the CustomItem's Fake Durability lore.
	 */
	public CustomItem addLoreLine(String lore) {
		this.lore.add(lore);
		return this;
	}

	/**
	 * Set the lore of the CustomItem. This will go above the CustomTool's Fake
	 * Attack Speed and Attack Damage Lore and the CustomItem's Fake Durability
	 * lore.
	 */
	public CustomItem setLore(List<String> string) {
		this.lore = string;
		return this;
	}

	/**
	 * @return {@link List} - The lore of the Custom Item. This does not include
	 *         the CustomTool's Fake Attack Speed and Attack Damage Lore or the
	 *         CustomItem's Fake Durability lore.
	 */
	public List<String> getLore() {
		return lore;
	}

	/**
	 * @param enchantments
	 *            The Enchantments that will be taken into consideration when
	 *            creating the lore. Currently, only the Sharpness Enchantment
	 *            and its Level have to be taken into consideration.
	 * @param durability
	 *            The current fake durability that will be added if the
	 *            CustomItem has fake Durability.
	 * @return {@link List} - The lore of the Custom Item. This includes the
	 *         CustomTool's Fake Attack Speed and Attack Damage Lore and the
	 *         CustomItem's Fake Durability lore that have been customized
	 *         according to the specified parameters.
	 */
	public List<String> getFullLore(Map<Enchantment, Integer> enchantments, int durability) {
		// Lore

		List<String> loreToAdd = new ArrayList<String>();

		loreToAdd.addAll(lore);

		// Fake Damage Lore

		if (this instanceof CustomTool && ((CustomTool) this).hasFakeAttackLore()) {

			double attackSpeed = 0D;
			double attackDamage = 0D;

			Boolean hasSpeed = false;
			Boolean hasDamage = false;

			for (Attribute attribute : attributes) {
				if (attribute.getAttributeType() != null) {

					AttributeType type = attribute.getAttributeType();
					double amount = attribute.getAmount();

					if (type == AttributeType.GENERIC_ATTACK_SPEED && !hasSpeed) {
						attackSpeed += amount;
						hasSpeed = true;
					} else if (type == AttributeType.GENERIC_ATTACK_DAMAGE && !hasDamage) {
						attackDamage += amount;
						hasDamage = true;
					}
				}
			}

			if (!hasSpeed)
				attackSpeed = MaterialUtils.getBaseSpeed(material);
			else
				attackSpeed += 4.0;
			if (!hasDamage)
				attackDamage = MaterialUtils.getBaseDamage(material);
			else
				attackDamage += 1.0;

			loreToAdd.add("");
			loreToAdd.add(ChatColor.GRAY + LangFileUtils.get("attack_main_hand"));

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

			/*
			 * Needed because this is how it's done for attributes in Vanilla.
			 * That being said, this hides an accuracy bug that occures with the
			 * attributes from, what I can tell to be, a conversion from double
			 * to float in game. This is not something that can be fixed easily
			 * as it is not caused by this API. Confirmed with Minecraft code
			 * that it's expected behaviour. For example, this is the amount of
			 * Attack Speed for the Swords: -2.4000000953674316D
			 */
			attackSpeed = MathUtils.round(attackSpeed, 2);
			attackDamage = MathUtils.round(attackDamage, 2);

			if ((attackSpeed == Math.floor(attackSpeed)) && !Double.isInfinite(attackSpeed)) {
				loreToAdd.add(ChatColor.GRAY + " " + Integer.toString((int) attackSpeed) + " "
						+ LangFileUtils.get("attack_speed"));
			} else {
				loreToAdd.add(
						ChatColor.GRAY + " " + Double.toString(attackSpeed) + " " + LangFileUtils.get("attack_speed"));
			}
			if ((attackDamage == Math.floor(attackDamage)) && !Double.isInfinite(attackDamage)) {
				loreToAdd.add(ChatColor.GRAY + " " + Integer.toString((int) attackDamage) + " "
						+ LangFileUtils.get("attack_damage"));
			} else {
				loreToAdd.add(ChatColor.GRAY + " " + Double.toString(attackDamage) + " "
						+ LangFileUtils.get("attack_damage"));
			}
		}
		if (hasFakeDurability()) {
			loreToAdd.add(ChatColor.GRAY + LangFileUtils.get("durability") + " " + Integer.toString(durability) + " / "
					+ Integer.toString(fakeDurability));
		}

		return loreToAdd;
	}

	@Override
	public CustomItem clone() {
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

	/**
	 * Compare the CustomItems in regards to their durability. Used for
	 * generating the Resource Pack.
	 */
	@Override
	public int compareTo(CustomItem cItem) {
		final int before = -1;
		final int equal = 0;
		final int after = 1;

		if (this == cItem)
			return equal;

		if (this.durability < cItem.durability)
			return before;
		if (this.durability > cItem.durability)
			return after;

		return equal;
	}

	/**
	 * @return ItemDurability - The ItemDurability mechanics. These determine
	 *         how the durability of the CustomItem, both for Fake and real
	 *         durability, will drop.
	 */
	public ItemDurability getDurabilityMechanics() {
		return itemDurability;
	}

	/**
	 * These determine how the durability of the CustomItem, both for Fake and
	 * real durability, will drop.
	 * 
	 * @param itemDurability
	 *            the itemDurability to set. Every class under the package
	 *            com.chrismin13.moreminecraft.durability is valid.
	 */
	public void setDurabilityMechanics(ItemDurability itemDurability) {
		this.itemDurability = itemDurability;
	}
}