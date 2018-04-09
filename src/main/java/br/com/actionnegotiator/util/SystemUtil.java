package br.com.actionnegotiator.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SystemUtil {

	public static boolean invalidStringLength(String string) {
		return string.length() > 255;
	}
	
	public static boolean invalidBigDecimalLength(BigDecimal bigDecimal) {
		return bigDecimal.setScale(2, RoundingMode.HALF_EVEN).toString().length() > 22;
	}
	
}
