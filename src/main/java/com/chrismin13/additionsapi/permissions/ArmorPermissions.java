package com.chrismin13.additionsapi.permissions;

/**
 * This specifies the permissions that will be checked when using the Custom
 * Armor.
 * 
 * @author chrismin13
 */
public class ArmorPermissions extends ItemPermissions {

	private String wear;

	public ArmorPermissions(String permissionPrefix, PermissionType type) {
		super(permissionPrefix, type);
		wear =  this.getPermissionPrefix() + "."+ this.getType().getPermission() + ".wear";
	}

	/**
	 * @return The permission node for wearing the Custom Armor. This is also
	 *         the same for Elytras.
	 */
	public String getWear() {
		return wear;
	}

	/**
	 * @param Set
	 *            the permission node for wearing the Custom Armor. This is also
	 *            the same for Elytras.
	 */
	public ArmorPermissions setWear(String wear) {
		this.wear = wear;
		return this;
	}

}
