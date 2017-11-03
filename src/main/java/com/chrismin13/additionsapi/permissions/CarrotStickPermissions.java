package com.chrismin13.additionsapi.permissions;

/**
 * TODO: STILL NOT DONE. All values are placeholders for when it finally gets
 * implemented. This specifies the permissions that will be checked when using
 * the Custom Item that is of Material CARROT_STICK.
 * 
 * @author chrismin13
 */
public class CarrotStickPermissions extends ItemPermissions {

	private String boost;

	public CarrotStickPermissions(String permissionPrefix, PermissionType type) {
		super(permissionPrefix, type);
		boost =  this.getPermissionPrefix() + "."+ this.getType().getPermission() + ".boost";
	}

	/**
	 * @return The permission node for boosting by right-clicking when on a pig.
	 */
	public String getBoost() {
		return boost;
	}

	/**
	 * Set the permission node for boosting by right-clicking when on a pig.
	 * 
	 * @param boost
	 */
	public CarrotStickPermissions setBoost(String boost) {
		this.boost = boost;
		return this;
	}

}
