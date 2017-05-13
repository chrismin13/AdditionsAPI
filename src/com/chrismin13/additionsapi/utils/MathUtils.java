package com.chrismin13.additionsapi.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

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

}
