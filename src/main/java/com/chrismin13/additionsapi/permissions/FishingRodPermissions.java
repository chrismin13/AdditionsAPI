package com.chrismin13.additionsapi.permissions;

/**
 * This specifies the permissions that will be checked when using the Custom
 * Item that is of Material FISHING_ROD
 * 
 * @author chrismin13
 */

public class FishingRodPermissions extends ItemPermissions {

	private String reel;
	
	public FishingRodPermissions(String permissionPrefix, PermissionType type) {
		super(permissionPrefix, type);
		reel =  this.getPermissionPrefix() + "."+ this.getType().getPermission() + ".reel";
	}

	/**
	 * @return The permission node for reeling on a Fishing Rod
	 */
	public String getReel() {
		return reel;
	}

	/**
	 * @param Set the permission node for reeling on a Fishing Rod
	 */
	public void setReel(String reel) {
		this.reel = reel;
	}

}
