package com.chrismin13.moreminecraft.utils;

import com.chrismin13.moreminecraft.files.LangFile;

public class LangFileUtils {

	public static String get(String entryName) {
		return LangFile.getInstance().getEntry("more_minecraft", entryName);
	}
	
}
