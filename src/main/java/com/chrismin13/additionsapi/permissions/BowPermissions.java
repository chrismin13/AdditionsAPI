package com.chrismin13.additionsapi.permissions;

/**
 * This specifies the permissions that will be checked when using a Custom Bow.
 * 
 * @author chrismin13
 */
public class BowPermissions extends ItemPermissions {

	private String shoot;

	public BowPermissions(String permissionPrefix, PermissionType type) {
		super(permissionPrefix, type);
		shoot =  this.getPermissionPrefix() + "."+ this.getType().getPermission() + ".shoot";
	}

	/**
	 * @return The permission node for shooting with the bow.
	 */
	public String getShoot() {
		return shoot;
	}

	/**
	 * Set the permission node for shooting with the bow.
	 * 
	 * @param shoot
	 */
	public BowPermissions setShoot(String shoot) {
		this.shoot = shoot;
		return this;
	}

}
