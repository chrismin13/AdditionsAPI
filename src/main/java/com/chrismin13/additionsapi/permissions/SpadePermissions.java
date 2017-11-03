package com.chrismin13.additionsapi.permissions;

/**
 * This specifies the permissions that will be checked when using the Custom
 * Tool that is of ToolType SPADE.
 * 
 * @author chrismin13
 */
public class SpadePermissions extends ItemPermissions {

	private String tile;
	
	public SpadePermissions(String permissionPrefix, PermissionType type) {
		super(permissionPrefix, type);
		tile =  this.getPermissionPrefix() + "."+ this.getType().getPermission() + ".tile";
	}
	
	/**
	 * @return The permission node for creating a path block.
	 */
	public String getTile() {
		return tile;
	}

	/**
	 * Set the permission node for creating a path block.
	 * 
	 * @param tile
	 */
	public SpadePermissions setTile(String tile) {
		this.tile = tile;
		return this;
	}
	
}