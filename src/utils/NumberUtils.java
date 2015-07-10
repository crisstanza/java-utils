package utils;

/**
 * Classe auxiliar para executar opera&ccedil;&otilde;es ou convers&otilde;es de n&uacute;meros.
 *
 * @author Cris Stanza, 07-Jul-2015
 */
public final class NumberUtils {

	private static final char VIRGULA = ',';
	private static final char PONTO = '.';

	private static final int BASE_10 = 10;

	private NumberUtils() {
	}

	public static int parseInt(String value) {
		return Integer.parseInt(value, BASE_10);
	}

	public static long parseLong(String value) {
		return Long.parseLong(value, BASE_10);
	}

	public static float parseFloat(String value) {
		return Float.parseFloat(value);
	}

	public static double parseDouble(String value) {
		return Double.parseDouble(value);
	}

	public static float fixAndParseFloat(String value) {
		value = NumberUtils.fixDecimal(value);
		return parseFloat(value);
	}

	public static double fixAndParseDouble(String value) {
		value = NumberUtils.fixDecimal(value);
		return parseDouble(value);
	}

	public static Integer fix(Integer value) {
		if (value == null || value < 0) {
			return 0;
		}
		return value;
	}

	public static Long fixLong(Integer n) {
		return n == null ? null : n.longValue();
	}

	public static String fixDecimal(String decimalNumber) {
		return decimalNumber.replace(VIRGULA, PONTO);
	}

	public static long[] parseLongArray(String[] stringArray) {
		if (stringArray == null) {
			return null;
		} else {
			long[] longArray = new long[stringArray.length];
			for (int i = 0; i < stringArray.length; i++) {
				longArray[i] = parseLong(stringArray[i]);
			}
			return longArray;
		}
	}

	public static int[] parseIntArray(String[] stringArray) {
		if (stringArray == null) {
			return null;
		} else {
			int[] intArray = new int[stringArray.length];
			for (int i = 0; i < stringArray.length; i++) {
				intArray[i] = parseInt(stringArray[i]);
			}
			return intArray;
		}
	}

}
