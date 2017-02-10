package com.chrismin13.moreminecraft.utils;

public class NumberUtils {

	public static boolean calculateChance(double percentage) {
		double d = Math.random();
		if (d <= percentage)
			return true;
		return false;
	}

	public static int safeLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
		}
		return (int) l;
	}

	public static boolean isValidShot(int integer) {
		return (integer < Short.MAX_VALUE && integer > Short.MIN_VALUE);
	}

}
