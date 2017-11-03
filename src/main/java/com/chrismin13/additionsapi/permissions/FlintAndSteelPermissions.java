package com.chrismin13.additionsapi.permissions;

/**
 * This specifies the permissions that will be checked when using the Custom
 * Item that is of Material FLINT_AND_STEEL.
 * 
 * @author chrismin13
 */
public class FlintAndSteelPermissions extends ItemPermissions {

	private String fire;
	
	public FlintAndSteelPermissions(String permissionPrefix, PermissionType type) {
		super(permissionPrefix, type);
		fire =  this.getPermissionPrefix() + "."+ this.getType().getPermission() + ".fire";
	}

	/**
	 * @return The permission node for setting a block on fire.
	 */
	public String getFire() {
		return fire;
	}

	/**
	 * Set the permission node for setting a block on fire.
	 * 
	 * @param fire
	 */
	public FlintAndSteelPermissions setFire(String fire) {
		this.fire = fire;
		return this;
	}

}
