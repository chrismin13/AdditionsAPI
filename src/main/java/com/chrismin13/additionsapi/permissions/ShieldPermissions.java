package com.chrismin13.additionsapi.permissions;

/**
 * This specifies the permissions that will be checked when using the Custom
 * Item that is of Material SHIELD.
 * 
 * @author chrismin13
 */
public class ShieldPermissions extends ItemPermissions {

	private String block;
	
	public ShieldPermissions(String permissionPrefix, PermissionType type) {
		super(permissionPrefix, type);
		block =  this.getPermissionPrefix() + "."+ this.getType().getPermission() + ".block";
	}

	/**
	 * @return The permission node for blocking with the shield.
	 */
	public String getBlock() {
		return block;
	}

	/**
	 * @param Set the permission node for blocking with the shield.
	 */
	public void setBlock(String block) {
		this.block = block;
	}	

}
