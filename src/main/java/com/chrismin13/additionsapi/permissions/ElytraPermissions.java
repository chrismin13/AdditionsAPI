package com.chrismin13.additionsapi.permissions;

/**
 * This specifies the permissions that will be checked when using the Custom
 * Item that is of Material ELYTRA
 * 
 * @author chrismin13
 */
public class ElytraPermissions extends ItemPermissions {

	private String flight;

	public ElytraPermissions(String permissionPrefix, PermissionType type) {
		super(permissionPrefix, type);
		flight =  this.getPermissionPrefix() + "."+ this.getType().getPermission() + ".flight";
	}

	/**
	 * @return The permission node for toggling flight with the Elytra
	 */
	public String getFlight() {
		return flight;
	}

	/**
	 * Set the permission node for toggling flight with the Elytra
	 * 
	 * @param flight
	 */
	public ElytraPermissions setFlight(String flight) {
		this.flight = flight;
		return this;
	}

}
