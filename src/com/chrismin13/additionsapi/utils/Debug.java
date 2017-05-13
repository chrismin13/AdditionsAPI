package com.chrismin13.additionsapi.utils;

import java.util.logging.Logger;

import org.bukkit.Bukkit;

import com.chrismin13.additionsapi.files.ConfigFile;
import com.chrismin13.additionsapi.files.ConfigFile.DebugType;

public class Debug {

	private static Logger log = Bukkit.getLogger();
	private static DebugType type = ConfigFile.getInstance().getDebug();
	
	public static void say(String s) {
		log.info(s);
	}
	
	public static void sayTrue(String s) {
		if (type != DebugType.FALSE)
			log.info(s);
	}
	
	public static void saySuper(String s) {
		if (type == DebugType.SUPER)
			log.info(s);
	}
	
	public static void sayError(String s) {
		log.severe(s);
	}
	
	public static void sayTrueError(String s) {
		if (type != DebugType.FALSE)
			log.severe(s);
	}
	
	public static void saySuperError(String s) {
		if (type == DebugType.SUPER)
			log.severe(s);
	}
	
}
