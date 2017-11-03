package com.chrismin13.additionsapi.permissions;

public enum PermissionType {

	ALLOW, DENY;

	public String getPermission() {
		return toString().toLowerCase();
	}

}
