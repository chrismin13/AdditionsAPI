package com.chrismin13.additionsapi.files;

import com.chrismin13.additionsapi.permissions.PermissionType;

/**
 * Holds the values of the config.yml's sub-section specifying the Permission
 * System's different aspects.
 * 
 * @author chris
 *
 */
public class PermissionConfig {

	/**
	 * Create an instance of this Class.
	 *
	 * @param defaultType
	 *           The default Permission Type that will be used. Refer to the
	 *           plugin page for more info on how Permissions work.
	 */
	public PermissionConfig(PermissionType defaultType) {
		this.defaultType = defaultType;
	}

	private PermissionType defaultType;

	/**
	 * @return Which permission type is the default.
	 */
	public PermissionType getDefaultType() {
		return defaultType;
	}
}