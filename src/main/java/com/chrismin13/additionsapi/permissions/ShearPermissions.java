package com.chrismin13.additionsapi.permissions;

/**
 * This specifies the permissions that will be checked when using the Custom
 * Item that is of Material SHEARS.
 * 
 * @author chrismin13
 */
public class ShearPermissions extends ItemPermissions {

	private String shear;
	
	public ShearPermissions(String permissionPrefix, PermissionType type) {
		super(permissionPrefix, type);
		shear =  this.getPermissionPrefix() + "."+ this.getType().getPermission() + ".shear";
	}

	/**
	 * @return The permission node for shearing sheep
	 */
	public String getShear() {
		return shear;
	}

	/**
	 * Set the permission node for shearing sheep
	 * 
	 * @param shear
	 */
	public ShearPermissions setShear(String shear) {
		this.shear = shear;
		return this;
	}

}
