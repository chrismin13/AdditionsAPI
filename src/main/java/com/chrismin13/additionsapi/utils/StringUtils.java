package com.chrismin13.additionsapi.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

	public static List<String> startsWith(List<String> subcommands, String start) {
		if (start == null || start.equals("") || subcommands == null || subcommands.isEmpty())
			return subcommands;

		ArrayList<String> startingStrings = new ArrayList<String>();
		
		for (String string : subcommands)
			if (string.startsWith(start))
				startingStrings.add(string);
		
		return startingStrings;
		
	}

}
