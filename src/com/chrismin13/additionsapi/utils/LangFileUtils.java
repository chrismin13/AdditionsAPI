package com.chrismin13.additionsapi.utils;

import com.chrismin13.additionsapi.files.LangFile;

public class LangFileUtils {

	public static String get(String entryName) {
		return LangFile.getInstance().getEntry("more_minecraft", entryName);
	}
	
}
