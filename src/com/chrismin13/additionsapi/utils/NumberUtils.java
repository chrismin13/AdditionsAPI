package com.chrismin13.additionsapi.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

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

	/**
	 * A rounding method for doubles that also specifies up to how many decimal
	 * places there will be.<br>
	 * Credit goes to Jonik from Stack Overflow.
	 * http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	 * 
	 * @param value
	 *            The double you want to round.
	 * @param places
	 *            How many decimals there will be.
	 * @return The rounded double.
	 */
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	public static int randomInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
}
