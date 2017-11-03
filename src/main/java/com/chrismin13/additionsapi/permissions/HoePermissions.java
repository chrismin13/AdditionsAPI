package com.chrismin13.additionsapi.permissions;

/**
 * This specifies the permissions that will be checked when using the Custom
 * Tool that is of ToolType HOE.
 * 
 * @author chrismin13
 */
public class HoePermissions extends ItemPermissions {

	private String hoe;

	public HoePermissions(String permissionPrefix, PermissionType type) {
		super(permissionPrefix, type);
		hoe = this.getPermissionPrefix() + "." + this.getType().getPermission() + ".hoe";
	}

	/**
	 * @return The permission node for tiling the ground with a hoe.
	 */
	public String getHoe() {
		return hoe;
	}

	/**
	 * Set the permission node for tiling the ground with a hoe.
	 * 
	 * @param hoe
	 */
	public HoePermissions setHoe(String hoe) {
		this.hoe = hoe;
		return this;
	}

}
