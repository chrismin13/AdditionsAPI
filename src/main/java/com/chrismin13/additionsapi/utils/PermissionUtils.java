package com.chrismin13.additionsapi.utils;

import org.bukkit.entity.Player;

import com.chrismin13.additionsapi.permissions.PermissionType;

public class PermissionUtils {

	public static boolean allowedAction(Player player, PermissionType type, String permission) {
		switch(type) {
		case ALLOW:
			if (player.hasPermission(permission))
				return true;
			break;
		case DENY:
			if (!player.hasPermission(permission) || player.isOp() || player.hasPermission("*"))
				return true;
			break;
		}		
		return false;
	}
	
}
