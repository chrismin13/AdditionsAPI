package com.chrismin13.additionsapi.permissions;

/**
 * This specifies the permissions that will be checked when using the Custom
 * Item. There are similar classes available for most other types of Custom
 * Items.
 * 
 * @author chrismin13
 */
public class ItemPermissions {

	private String permissionPrefix;
	private PermissionType type;
	private String blockBreak;
	private String hit;
	private String craft;
	private String drop;
	private String pickup;

	/**
	 * Create a new ItemPermissions Object
	 * 
	 * @param permissionPrefix
	 *            The first part of the permission node - must be simmilar to
	 *            "plugin_name.item_name". By default, this is taken from the
	 *            Custom Item's Id Name.
	 * @param type
	 *            The PermissionType of the permission node. Will go right after
	 *            the permissionPrefix
	 */
	public ItemPermissions(String permissionPrefix, PermissionType type) {
		this.setPermissionPrefix(permissionPrefix);
		this.setType(type);
		blockBreak = permissionPrefix + "." + type.getPermission() + ".break";
		hit = permissionPrefix + "." + type.getPermission() + ".hit";
		craft = permissionPrefix + "." + type.getPermission() + ".craft";
		drop = permissionPrefix + "." + type.getPermission() + ".drop";
		pickup = permissionPrefix + "." + type.getPermission() + ".pickup";
	}

	/**
	 * @return The permission node for breaking a block.
	 */
	public String getBreak() {
		return blockBreak;
	}

	/**
	 * Set the permission node for breaking a block.
	 * 
	 * @param blockBreak
	 */
	public ItemPermissions setBreak(String blockBreak) {
		this.blockBreak = blockBreak;
		return this;
	}

	/**
	 * @return The permission node for hitting another entity.
	 */
	public String getHit() {
		return hit;
	}

	/**
	 * Set the permission node for hitting another entity.
	 * 
	 * @param hit
	 */
	public ItemPermissions setHit(String hit) {
		this.hit = hit;
		return this;
	}

	/**
	 * @return The first part of the Permission Node.
	 */
	public String getPermissionPrefix() {
		return permissionPrefix;
	}

	/**
	 * Set the first part of the Permission Node.
	 * 
	 * @param permissionPrefix
	 */
	public void setPermissionPrefix(String permissionPrefix) {
		this.permissionPrefix = permissionPrefix;
	}

	/**
	 * @return The PermissionType. Follows the Permission Prefix.
	 */
	public PermissionType getType() {
		return type;
	}

	/**
	 * Set the PermissionType. Follows the Permission Prefix.
	 * 
	 * @param type
	 */
	public void setType(PermissionType type) {
		this.type = type;
	}

	/**
	 * @return The permission node for Crafting the Custom Item.
	 */
	public String getCraft() {
		return craft;
	}

	/**
	 * Set the permission node for Crafting the Custom Item.
	 * 
	 * @param craft
	 */
	public void setCraft(String craft) {
		this.craft = craft;
	}

	/**
	 * @return The permission node for Dropping the Custom Item by a Player.
	 */
	public String getDrop() {
		return drop;
	}

	/**
	 * Set the permission node for Dropping the Custom Item by a Player.
	 * 
	 * @param drop
	 */
	public void setDrop(String drop) {
		this.drop = drop;
	}

	/**
	 * @return The permission node for a player to pick up the Custom Item.
	 */
	public String getPickup() {
		return pickup;
	}

	/**
	 * Set the permission node for a player to pick up the Custom Item.
	 * 
	 * @param pickUp
	 */
	public void setPickup(String pickUp) {
		this.pickup = pickUp;
	}
}
